# Table of Contents
* [Mongodb Sharded Cluster Setup on Open Shift](#Mongodb Sharded Cluster Setup on Open Shift)
    * [Mongodb Sharded Cluster Setup on Open Shift](#mongodb-sharded-cluster-setup-on-open-shift)
    * [Create the MongoDB Enterprise Operator](#create-the-mongodb-enterprise-operator)
    * [Install the MongoDB Operations Manager](#install-the-mongodb-operations-manager)
    * [Setting up the Mongo DB Sharded Cluster.](#setting-up-the-mongo-db-sharded-cluster)
    * [Accessing the database in Kogito Application](#accessing-the-database-in-kogito-application)
    * [Sharding the collection on Mongo](#sharding-the-collection-on-mongo)
    * [Kogito Benchmarking Results using Sharded Cluster.](#kogito-benchmarking-results-using-sharded-cluster)

# Mongodb Sharded Cluster Setup on Open Shift

Create a blank project to continue the mongo DB cluster setup on open shift.

```shell
oc login https://<api-endpoint-url>
oc new-project mongodb
```

Going forward we will be forking the mongo database [enterprise operator Github repository](https://github.com/mongodb/mongodb-enterprise-kubernetes) and modifying to our needs.

```shell
oc create -f https://github.com/mongodb/mongodb-enterprise-kubernetes/raw/master/crds.yaml
```

# Create the MongoDB Enterprise Operator

Create the MongoDB Enterprise Operator and a few associated resources (service accounts, roles, role bindings) by executing below command.

```shell
oc create -f https://github.com/mongodb/mongodb-enterprise-kubernetes/raw/master/mongodb-enterprise-openshift.yaml
```

You may see the pods start with the name ```enterprise-operator-xxxx-yyy```. Make sure that pod starts correctly before going to next step. It might take some time depending on your environment configuration.

# Install the MongoDB Operations Manager

MongoDB operations manager is an application will let you manage the sharded cluster. We have to deploy/setup this application before setting up the actual cluster.

Step1: Create the super admin user for operations manager. Store the file name called ```opsman-admin-credentials.yaml``` The choosen password must match the Operations manager's passowrd policy. It has to be 8 chars minimum, one letter minimum, one digit minimum, one special character minimum.

```yaml
apiVersion: v1
kind: Secret
stringData:
  FirstName: Operations
  LastName: Manager
  Password: <opsman-password>
  Username: opsman
type: Opaque
metadata:
  name: opsman-admin-credentials
```

Step 2: another secret with the password Operations Manager uses to access its own database called appdb. Store this file in ```opsman.db-password.yaml```

```yaml
apiVersion: v1
kind: Secret
stringData:
  password: <db-password>
type: Opaque
metadata:
  name: opsman-db-password
```

Step 3: create a custom resource of type ```MongoDBOpsManager``` that will trigger the MongoDB Enterprise Operator and make it install the Operations Manager. Save this file with the name ```opsman-instance.yaml```

```yaml
apiVersion: mongodb.com/v1
kind: MongoDBOpsManager
metadata:
  name: ops-manager
spec:
  replicas: 1
  version: 4.2.4
  adminCredentials: opsman-admin-credentials

  backup:
    enabled: false

  applicationDatabase:
    members: 3
    version: 4.2.0
    passwordSecretKeyRef:
      name: opsman-db-password
```

Step 4: Create all the resources from above steps on Openshift cluster.

```yaml
oc create -f opsman-admin-credentials.yaml
oc create -f opsman-db-password.yaml
oc apply -f opsman-instance.yaml
```

Step 5: Watch the installation unfold, e.g. with ```oc get pods -w``` or ```oc logs ops-manager-0 -f```. Be patient as it might take a couple of minutes until the Operations Manager is operational.

Step 6: Create a route to make the Operations Manager’s GUI accessible from the outside.

```yaml
oc expose svc ops-manager-svc
```

Step 7: Access the route/Ops manager GUI application on the browser for the first time. You can login with the credentials given in the secret file ```opsman-admin-credentials.yaml```
You need to provide all the info as per your organization. With this setup MongoDB operations manager should be in a running state and usable. 


# Setting up the Mongo DB Sharded Cluster.
All these steps have to be performed on the MongoDB Operations manager GUI. We need to link this Ops Manager app with Kubernetes application using the API keys and configmaps.

Step 1: Create an org so that all the clusters are associated in this organization. Click on the very top right Operations and Organizations. Click the green Button NEW ORGANIZATION. Copy the organization ID from the organization details page.

Step 2: Create an API key that allows the Operator to access the Operations Manager. Note down the 8 character code below Public Key. Give a name to the API key and choose a permission - either Organization Owner or Organization Project Creator. Click Next. Here is the only time when you see the private key completely in clear text. Write it down - you will need it shortly

Step 3: whitelist entry to allow the operator to access the Operations Manager API. Use the Operations Manager pod ID address. You can get it with

```shell
oc get pod -l app=enterprise-operator -o jsonpath='{.items[0].status.podIP}'
```
You may have realized that this white list doesn’t work any more as soon as the Operator gets restarted, i.e. it’s run in a pod with a different IP address. You may specify the IP address range used for pods (e.g. 10.0.0.0/8). The problem is that it’s impossible for mere mortals to determine this IP address range.

Step 4: create a secret and a config map in preparation of the deployment of a real MongoDB instance. The secret holds the credentials to access the Operations Manager API. Save this file as ```test-orga-api-key.yaml``` 

```yaml
apiVersion: v1
kind: Secret
stringData:
  user: <public-key>
  publicApiKey: <private-key>
type: Opaque
metadata:
  name: test-orga-api-key
```

Step 5: The config map has information about the project to create (provided it doesn’t already exist), the organization where the project resides in and the URL to access the Operations Manager. Store this in a file named ```test-project-config.yaml```.

```yaml
apiVersion: v1
kind: ConfigMap
metadata:
  name: test-project-config
data:
  projectName: "Test Project"
  orgId: <organization-id>
  baseUrl: http://<opsman-service>.<project>.svc.cluster.local:8080
```

Step 6: The last resource you have to create is sharded Mongo DB cluster. The definition for this is provided in - [sharded-cluster.yaml](setup/sharded-cluster.yaml). Please tweak this definition as per your requirement.

Step 7: Apply all the resources created in above steps on to the Openshift platform

```shell
oc create -f test-orga-api-key.yaml
oc create -f test-project-config.yaml
oc create -f sharded-cluster.yaml
```
Step 8: It will take signficantly long time to create all the required pods. Monitor the progress.

```shell
oc get pods -w
```

# Accessing the database in Kogito Application

Step 1: Enable the authentication to mongo cluster and create user name and passwords to access the mongo cluster using Ops Manager GUI

Step 2: Publish all the changes on Ops Manager GUI. Your changes won't reflect until you publish the changes. You can find publish button on top of the Ops Manager GUI. This may take some time.

Step 3: Update the yaml file - [kogito-mongo-secret.yml](setup/kogito-mongo-secret.yml) with the user name and password you created in above steps.

Step 4: Create resource on the Openshift. 
```shell
oc apply -f kogito-mongo-secret.yml
```

Step 5: Start the Quarkus application so that application will create the kogito database on the cluster.

# Sharding the collection on Mongo

By default collection or database is not sharded. We have to explicitly enable them to be sharded.

Step 1: port forward the mongos pod. You can use any mongos pod.

```shell
oc port-forward kogito-sharded-mongo-mongos-1 34000:27017
```

Step 2: Connect to mongosh using the mongo DB credentials. 

```shell
mongosh mongodb://developer:Passw0rd.@localhost:34000
```

Step 3: You will be connected cluster. Some sample commands. 
```shell
Enterprise [direct: mongos] kogito_quarkus> show collections
demo.orderItems
demo.orders
demo.simpleHT
emptyProcess
notPersistedProcess
persons
```
Step 4: Enable sharding on the database and collection. We have to create hashed index on the shard key we are planning to use.
```shell
> sh.enableSharding("kogito_quarkus")
//we need to have hashed index on the shard key. Hashed index is a different index than regular index.
> db.demo.simpleHT.ensureIndex({id: "hashed"})
//shard the collection now
> sh.shardCollection("kogito_quarkus.demo.simpleHT",{"id":"hashed"})
```

# Kogito Benchmarking Results using Sharded Cluster.

* [3 shards - Default pods memory limits](./benchmarking-results/benchmarkReport-3shards-default.html)
* [3 Shards - Memory Limits - 2CPU, 5G memory](./benchmarking-results/benchmarkReport-3shards-mongo2CPU5G.html)
* [3 Shards - Memory Limits - 4CPU, 8G memory](./benchmarking-results/benchmarkReport-3shards-mongo4CPU8G.html)