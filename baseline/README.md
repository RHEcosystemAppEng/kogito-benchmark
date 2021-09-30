# Table of Contents
* [VM configuration](#vm-configuration)
  * [Common configuration](#common-configuration)
  * [Stop firewall on vm02](#stop-firewall-on-vm02)
  * [Install docker on vm02](#install-docker-on-vm02)
  * [Run docker-compose on vm02](#run-docker-compose-on-vm02)
  * [Run Kogito app on vm02](#run-kogito-app-on-vm02)
* [Test procedure](#test-procedure)

# VM configuration
```
lscpu
Architecture:          x86_64
CPU op-mode(s):        32-bit, 64-bit
Byte Order:            Little Endian
CPU(s):                4
On-line CPU(s) list:   0-3
Thread(s) per core:    1
Core(s) per socket:    1
...
```

## Common configuration
**Prerequisite**: the user belongs to group fsi-temenos (1111)
```shell
cd /opt
sudo mkdir fsi-kogito
sudo chmod 777 fsi-kogito
cd fsi-kogito

sudo yum update
sudo yum install git
git clone https://github.com/RHEcosystemAppEng/kogito-benchmark.git
sudo yum install java-11-openjdk-devel
curl -O https://dlcdn.apache.org/maven/maven-3/3.8.2/binaries/apache-maven-3.8.2-bin.tar.gz
tar xvf apache-maven-3.8.2-bin.tar.gz
cd /usr/local/bin/
sudo ln -s /opt/fsi-kogito/apache-maven-3.8.2/bin/mvn .
```

## Stop firewall on vm02
```shell
sudo systemctl stop firewalld
```
**Note**: this step must be repeated in case the VM reboots

## Install docker on vm02
Install docker, start the daemon and install docker-compose:

```shell
sudo subscription-manager repos --enable=rhel-7-server-rpms
sudo subscription-manager repos --enable=rhel-7-server-extras-rpms
sudo subscription-manager repos --enable=rhel-7-server-optional-rpms
sudo yum install -y docker
sudo systemctl start docker.service

sudo curl -L "https://github.com/docker/compose/releases/download/1.29.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
sudo chmod +x /usr/local/bin/docker-compose
```

Push requested images from the local PC to Quay.io:
[Local PC]
```shell
docker pull mongo:4.4.9
docker tag mongo:4.4.9 quay.io/dmartino/mongo:4.4.9
docker push quay.io/dmartino/mongo:4.4.9

docker pull infinispan/server:11.0.4.Final
docker tag infinispan/server:11.0.4.Final quay.io/dmartino/infinispan-server:11.0.4.Final
docker push quay.io/dmartino/infinispan-server:11.0.4.Final```
```

Update docker-compose.yml with new images:
```yaml
...
image: quay.io/dmartino/mongo:4.4.9
...
image: quay.io/dmartino/infinispan-server:11.0.4.Final
...
```

## Run docker-compose on vm02
```shell
cd /opt/fsi-kogito/kogito-benchmark/test/process-quarkus-example/docker
sudo /usr/local/bin/docker-compose up -d
```
## Run Kogito app on vm02
```
cd /opt/fsi-kogito/kogito-benchmark/test/process-quarkus-example
./runAppInTheBackground.sh
```

**Note**: remember to include the ``-Dquarkus.http.host=`hostname` `` option in the command if missing!

# Test procedure
For each test listed in the [baseline report](https://docs.google.com/spreadsheets/d/1C_1ICjkEhYji3mbdST01bRWGTO5FCx9sblWV43r-TFw/edit?usp=sharing):
* Run kogito app
* Run 5' WARM_UP testCase (`export testCase=WARM_UP` in `test.properties`)
* Run actual test case (once)
* Collect the execution metrics in the baseline report
* Stop Kogito app