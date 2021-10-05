#!/bin/sh

echo " Assuming you are on the RedHat VPN and currently authorized with open shift cluster...!!"


if [ -d "log-metrics" ]; then rm -Rf log-metrics; fi

mkdir log-metrics
cd log-metrics

echo "Pulling the logs from open shift cluster. This may take some time depending on the size of the logs.."
# 1 - capture the pod logs from open shift to local folder
(for pod in $(oc get pods -l app=process-quarkus-example -o custom-columns=POD:.metadata.name --no-headers); do echo $pod; kubectl logs $pod --prefix=true; done;) > pod-logs.log

echo "Successfully downloaded logs from Open Shift cluster. Calculating metrics now."
#2 - Filter the targeted logs.
(grep -w 'Order has been created Order\[12345\] with assigned approver JOHN'  pod-logs.log) > pod-logs-filtered.log

#3 - Apply group by and count logic.
(sort pod-logs-filtered.log | uniq -c) > pod-logs-count-results.log

#4 - Display the results.
more -100 pod-logs-count-results.log