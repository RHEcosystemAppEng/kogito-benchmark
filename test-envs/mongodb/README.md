The script assumes that you are logged into OCP via oc command line as well as that the application collection exists and contains the required indexes

Excepted example output -  
- COUNT BEFORE CLEANUP (a number), 
- COUNT AFTER CLEANUP (should be 0)
- EXPECTED INDEXES (should be on `_id` and `id`)
```shell
MongoDB shell version v4.4.1
connecting to: mongodb://127.0.0.1:27017/?compressors=disabled&gssapiServiceName=mongodb
Implicit session: session { "id" : UUID("866b624f-fc54-4bb3-b08a-b6a4d5cc8427") }
MongoDB server version: 4.4.1
switched to db kogito_dataindex
408  ***COUNT BEFORE CLEANUP***
{
"nIndexesWas" : 2,
"nIndexes" : 2,
"indexes" : [  ***EXPECTED INDEXES***
{
"v" : 2,
"key" : {
"_id" : 1
},
"name" : "_id_"
},
{
"v" : 2,
"key" : {
"id" : 1
},
"name" : "id_1"
}
],
"ok" : 1,
"$clusterTime" : {
"clusterTime" : Timestamp(1633963203, 408),
"signature" : {
"hash" : BinData(0,"F8Vne+KFSXWswqukqUO8XEISOH4="),
"keyId" : NumberLong("7006309081140953089")
}
},
"operationTime" : Timestamp(1633963203, 408)
}
0  ***COUNT AFTER CLEANUP***
bye
```

### Validating that collection and indexes exist
```shell
...inside mongodb shell
show dbs; -- EXPECTED to contain kogito_dataindex
use kogito_dataindex;
show collections;  -- EXPECTED to contain simpleHT
db.demo.simpleHT.getIndexes();  -- EXPECTED to have index on id
```
### Managing missing collection
If the collection does not yet exist (after fresh installation of application), run a short trial test that will create the collection.
### Managing missing index
Adding missing index:
```js
db.demo.simpleHT.createIndex({"id": 1});
```
Drop the table to have next request creating the collection from scratch:
```js
db.demo.simpleHT.drop()
```
List existing indexes:
```js
db.demo.simpleHT.remove({})
```
```js
db.demo.simpleHT.getIndexes()
```