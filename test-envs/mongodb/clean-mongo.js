use kogito_dataindex;
db.demo.simpleHT.count();
db.demo.fruit.count();

db.demo.simpleHT.remove({});db.demo.simpleHT.reIndex();
db.demo.fruit.remove({});db.demo.fruit.reIndex();

db.demo.simpleHT.count();
db.demo.fruit.count();

