#!/bin/sh

WHEN=$1   # before or after
CONTAINER_ENGINE=$2   # docker or podman

# no before check here since in this kogito version no application index is set and so no db is created until first request

if [ "$WHEN" = 'after' ]
then
  $CONTAINER_ENGINE cp mongoAfter.js kogito-mongodb:/tmp
#  podman exec -it kogito-mongodb "mongo -u developer -p mypass < /tmp/mongoBefore.js"
  $CONTAINER_ENGINE exec -it kogito-mongodb /bin/bash -c "mongo < /tmp/mongoAfter.js"
  exit
fi