if [[ -f save_pid.txt ]]
then
  echo "found pid file - trying to stop process: "`cat save_pid.txt`
  kill -9 `cat save_pid.txt`
  rm save_pid.txt
else
  echo "no current pid - nothing to stop"
fi


