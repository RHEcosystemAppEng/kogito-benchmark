## Deploying the Kogito Gatling Benchmarking project to Open Shift.

#### Build config to create image of the kogito gatling benchmark project.

Use [build config](open-shift-build-config.yaml) to build the project as a docker image.

#### Create Noobaa storage claim on openshift 

Create `Object Bucket Claim` storage named `obc-kogito-gatling-claim`

#### Deploy the application using Pod yaml

Use the [pod definition](pod-kogito-gatling-benchmark.yaml) to run the Kogito gatling benchmark project and upload the report to Noobaa storage.

If you are new to the Noobaa then refer this [tutorial](https://blog.oddbit.com/post/2021-02-10-object-storage-with-openshift/) and [Noobaa Wiki](https://github.com/noobaa/noobaa-core/wiki).

If you are new to AWS S3 CLIs then refer this [tutorial](https://docs.aws.amazon.com/cli/latest/userguide/install-cliv2-docker.html)

#### Syncing the Noobaa bucket storage to your local folder
Noobaa is compatible with AWS S3 apis so all the AWS S3 CLI tools should work without any issues.

If you want to explore more about Noobaa please follow [Noobaa documentation](https://github.com/noobaa/noobaa-core). 

```shell
# Keep below configurations as part of your path so that your command can access them easily.
export NOOBAA_END_POINT="https://s3-openshift-storage.apps.mw-ocp4.cloud.lab.eng.bos.redhat.com/"
export NOOBAA_ACCESS_KEY="<REPLACE_WITH_AWS_ACCESS_KEY>"
export NOOBAA_SECRET_KEY="<REPLACE_WITH_AWS_SECRET_KEY>"

# If you have installed AWS cli tool on local then use below command to sync noobaa storage.
alias s3='AWS_ACCESS_KEY_ID=$NOOBAA_ACCESS_KEY AWS_SECRET_ACCESS_KEY=$NOOBAA_SECRET_KEY aws --endpoint $NOOBAA_END_POINT --no-verify-ssl s3'
s3 sync . s3://<NOOBAA-BUCKET-ID>

# If you would like to use docker aws cli to sync the noobaa bucket to local folder.
docker run --rm -it -v $(pwd):/aws -e AWS_ACCESS_KEY_ID=$NOOBAA_ACCESS_KEY -e AWS_SECRET_ACCESS_KEY=$NOOBAA_SECRET_KEY amazon/aws-cli s3 sync s3://obc-kogito-gatling-claim-4e8404ff-592b-4df6-8ac4-8bdc9fd4e1e3 . --endpoint $NOOBAA_END_POINT --no-verify-ssl

```