## Deploying the Kogito Gatling Benchmarking project to Open Shift.

#### Build config to create image of the kogito gatling benchmark project.

Use [build config](open-shift-build-config.yaml) to build the project as a docker image.

#### Create Noobaa storage claim on openshift 

Create `Object Bucket Claim` storage named `obc-kogito-gatling-claim`

#### Deploy the application using Pod yaml

Use the [pod definition](pod-kogito-gatling-benchmark.yaml) to run the Kogito gatling benchmark project and upload the report to Noobaa storage.

If you are new to the Noobaa then refer this [tutorial](https://blog.oddbit.com/post/2021-02-10-object-storage-with-openshift/) and [Noobaa Wiki](https://github.com/noobaa/noobaa-core/wiki).

If you are new to AWS S3 CLIs then refer this [tutorial](https://docs.aws.amazon.com/cli/latest/userguide/install-cliv2-docker.html)
