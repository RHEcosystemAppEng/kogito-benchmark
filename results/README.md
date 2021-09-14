# Issues / Troubleshooting

1) Break down of the request times using curl (request made from outside OCP env through OCP Route):

**Example request:**

```shell
curl -L --output /dev/null --silent --show-error --write-out 'lookup: %{time_namelookup}\nconnect: %{time_connect}\nappconnect: %{time_appconnect}\npretransfer: %{time_pretransfer}\nredirect: %{time_redirect}\nstarttransfer: %{time_starttransfer}\ntotal: %{time_total}\n' -d '{"approver" : "john", "order" : {"orderNumber" : "12345", "shipped" : false}}' -H "Content-Type: application/json" -X POST http://process-quarkus-example-fsi-kogito-benchmarking.apps.mw-ocp4.cloud.lab.eng.bos.redhat.com/orders

lookup: 0.000402
<br>connect: 0.268728
<br>appconnect: 0.000000
<br>pretransfer: 0.268754
<br>redirect: 0.000000
<br>starttransfer: 0.488364
<br>total: 0.488426
```
**Response fields explanation:**

**lookup:** The time, in seconds, it took from the start until the name resolving was completed.
<br>**connect:** The time, in seconds, it took from the start until the TCP connect to the remote host (or proxy) was completed.
<br>**appconnect:** The time, in seconds, it took from the start until the SSL/SSH/etc connect/handshake to the remote host was completed. (Added in 7.19.0)
<br>**pretransfer:** The time, in seconds, it took from the start until the file transfer was just about to begin. This includes all pre-transfer commands and negotiations that are specific to the particular protocol(s) involved.
<br>**redirect:** The time, in seconds, it took for all redirection steps include name lookup, connect, pretransfer and transfer before the final transaction was started. time_redirect shows the complete execution time for multiple redirections. (Added in 7.12.3)
<br>**starttransfer:** The time, in seconds, it took from the start until the first byte was just about to be transferred. This includes time_pretransfer and also the time the server needed to calculate the result.
<br>**total:** The total time, in seconds, that the full operation lasted. The time will be displayed with millisecond resolution.