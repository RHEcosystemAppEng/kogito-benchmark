echo 'to make application accessible as localhost, set -Dquarkus.http.host=0.0.0.0'
mvn -Dquarkus.profile=mongo -Dquarkus.http.host=`hostname` -P mongo clean package
nohup java -Dquarkus.profile=mongo -jar target/quarkus-app/quarkus-run.jar > process-quarkus-example.log 2>&1 &
echo $! > save_pid.txt