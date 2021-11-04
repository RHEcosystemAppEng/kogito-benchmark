TEST_IDX=$1
SYSTEM_DATA=system-data.csv
USAGE_DATA=usage$TEST_IDX.csv
echo $USAGE_DATA $SYSTEM_DATA

if [[ -f save_pid.txt ]]
then
  PID=$(cat save_pid.txt)
  echo "Extracting system metrics for process(id) : "$PID
  rm -r metrics
  mkdir metrics

  JAVA_VERSION=$(java -version 2>&1 | head -1 )

  TOTAL_CPU=$(lscpu | grep "CPU(s)" | head -1 | awk '{print $2}')

  TOTAL_MEMORY=$(grep MemTotal /proc/meminfo | awk '{print $2}' | xargs -I {} echo "scale=2; {}/1024^2" | bc)

  APP_CPU_USAGE=$(top -b -p $PID -n 1 | tail -n 1 | awk '{print $9}')
  APP_CPU_USAGE=${APP_CPU_USAGE%.*}
  APP_CPU_USAGE=$((APP_CPU_USAGE/4))

  SYSTEM_CPU_USAGE_PERCENTAGE=$(top -b -n2 -p 1 | fgrep "Cpu(s)" | tail -1 | awk -F'id,' -v prefix="$prefix" '{ split($1, vs, ","); v=vs[length(vs)]; sub("%", "", v); printf "%s%.2f\n", prefix, 100 - v }')

  APP_MEMORY_USAGE=$(top -b -p $PID -n 1 | tail -n 1 | awk '{print $6}' | xargs -I {} echo "scale=2; {}/1024" | bc)

  TOTAL_MEMORY_IN_MB=$(grep MemTotal /proc/meminfo | awk '{print $2}' | xargs -I {} echo "scale=2; {}/1024" | bc)

  APP_MEMORY_USAGE_PERCENTAGE=$(echo "$APP_MEMORY_USAGE * 100" | xargs -I {} echo "scale=2; {}/$TOTAL_MEMORY_IN_MB" | bc)

  SYSTEM_MEMORY_USAGE=$(top -b -p $PID -n 1 | fgrep "Mem" | head -1 | awk '{print $8}' | xargs -I {} echo "scale=2; {}/1024" | bc)

  SYSTEM_MEMORY_USAGE_PERCENTAGE=$(echo "$SYSTEM_MEMORY_USAGE * 100" | xargs -I {} echo "scale=2; {}/$TOTAL_MEMORY_IN_MB" | bc)

  echo "java-version, total-CPUs, total-Memory-GB" > metrics/$SYSTEM_DATA
  echo "$JAVA_VERSION, $TOTAL_CPU, ${TOTAL_MEMORY}" >> metrics/$SYSTEM_DATA

  echo "app-CPU-usage, system-CPU-usage, app-Memory-usage, app-Memory-usage-percentage, system-Memory-usage, system-Memory-usage-percentage" > metrics/$USAGE_DATA
  echo "$APP_CPU_USAGE%, $SYSTEM_CPU_USAGE_PERCENTAGE%, ${APP_MEMORY_USAGE}MB, $APP_MEMORY_USAGE_PERCENTAGE%, ${SYSTEM_MEMORY_USAGE}MB, $SYSTEM_MEMORY_USAGE_PERCENTAGE%" >> metrics/$USAGE_DATA

else
  echo "save_pid.txt file not found. Application has not properly started. Please run the application to collect metrics..."
fi