METRICS_FILENAME=$1
echo $METRICS_FILENAME

if [[ -f save_pid.txt ]]
then
  PID=$(cat save_pid.txt)
  echo "Extracting system metrics for process(id) : "$PID
  rm $METRICS_FILENAME

  JAVA_VERSION=$(java -version 2>&1 | head -1 )

  TOTAL_CPU=$(lscpu | grep "CPU(s)" | head -1 | awk '{print $2}')

  TOTAL_MEMORY=$(grep MemTotal /proc/meminfo | awk '{print $2}' | xargs -I {} echo "scale=2; {}/1024^2" | bc)

  APP_CPU_USAGE=$(top -b -p $PID -n 1 | tail -n 1 | awk '{print $9}')

  SYSTEM_CPU_USAGE_PERCENTAGE=$(top -b -n2 -p 1 | fgrep "Cpu(s)" | tail -1 | awk -F'id,' -v prefix="$prefix" '{ split($1, vs, ","); v=vs[length(vs)]; sub("%", "", v); printf "%s%.2f%%\n", prefix, 100 - v }')

  APP_MEMORY_USAGE=$(top -b -p $PID -n 1 | tail -n 1 | awk '{print $6}' | xargs -I {} echo "scale=2; {}/1024" | bc)

  TOTAL_MEMORY_IN_MB=$(grep MemTotal /proc/meminfo | awk '{print $2}' | xargs -I {} echo "scale=2; {}/1024" | bc)

  APP_MEMORY_USAGE_PERCENTAGE=$(echo "$APP_MEMORY_USAGE * 100" | xargs -I {} echo "scale=2; {}/$TOTAL_MEMORY_IN_MB" | bc)

  SYSTEM_MEMORY_USAGE=$(top -b -p $PID -n 1 | fgrep "Mem" | head -1 | awk '{print $8}' | xargs -I {} echo "scale=2; {}/1024" | bc)

  SYSTEM_MEMORY_USAGE_PERCENTAGE=$(echo "$SYSTEM_MEMORY_USAGE * 100" | xargs -I {} echo "scale=2; {}/$TOTAL_MEMORY_IN_MB" | bc)

  echo "java-version, system-CPU-total, system-Memory-total, app-CPU-usage, system-CPU-usage, app-Memory-usage, app-Memory-usage-percentage, system-Memory-usage, system-Memory-usage-percentage" >> $METRICS_FILENAME
  echo "$JAVA_VERSION, $TOTAL_CPU cores, $TOTAL_MEMORY GB, $APP_CPU_USAGE%, $SYSTEM_CPU_USAGE_PERCENTAGE%, $APP_MEMORY_USAGE MB, $APP_MEMORY_USAGE_PERCENTAGE%, $SYSTEM_MEMORY_USAGE MB, $SYSTEM_MEMORY_USAGE_PERCENTAGE%" >> $METRICS_FILENAME
else
  echo "save_pid.txt file not found. Application has not properly started. Please run the application to collect metrics..."
fi