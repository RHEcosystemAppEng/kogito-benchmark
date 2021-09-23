mvn -Dquarkus.profile=infinispan -P infinispan clean package
nohup java -Dquarkus.profile=infinispan -jar target/quarkus-app/quarkus-run.jar > process-quarkus-example.log 2>&1 &
echo $! > save_pid.txt:q