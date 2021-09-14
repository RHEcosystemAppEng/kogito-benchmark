# Issues / Troubleshooting

1) Break down of the request times using curl (request made from outside OCP env through OCP Route):

**Example request:**

curl -L --output /dev/null --silent --show-error --write-out 'lookup: %{time_namelookup}\nconnect: %{time_connect}\nappconnect: %{time_appconnect}\npretransfer: %{time_pretransfer}\nredirect: %{time_redirect}\nstarttransfer: %{time_starttransfer}\ntotal: %{time_total}\n' -d '{"approver" : "john", "order" : {"orderNumber" : "12345", "shipped" : false}}' -H "Content-Type: application/json" -X POST http://process-quarkus-example-fsi-kogito-benchmarking.apps.mw-ocp4.cloud.lab.eng.bos.redhat.com/orders

lookup: 0.000402
<br>connect: 0.268728
<br>appconnect: 0.000000
<br>pretransfer: 0.268754
<br>redirect: 0.000000
<br>starttransfer: 0.488364
<br>total: 0.488426