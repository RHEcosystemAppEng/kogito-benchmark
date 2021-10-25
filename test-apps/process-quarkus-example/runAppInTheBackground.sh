# 'to make application accessible as localhost, set -Dquarkus.http.host=0.0.0.0'

PROFILE=$1  # mongo or postgres

echo $PROFILE
if [ $PROFILE != 'mongo' ] && [ $PROFILE != 'postgres' ]
then
  echo "invalid profile: "$PROFILE
  exit
fi

mvn -Dquarkus.profile=$PROFILE -Dquarkus.http.host=`hostname` -P $PROFILE clean package
nohup java -Dquarkus.profile=$PROFILE -jar target/quarkus-app/quarkus-run.jar > process-quarkus-example.log 2>&1 &
echo $! > save_pid.txt