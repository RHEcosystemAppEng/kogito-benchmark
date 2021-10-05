use kogito_dataindex;
db.demo.orders.count();
db.demo.orderItems.count();
db.demo.orders.remove({});db.demo.orders.reIndex();
db.demo.orderItems.remove({});db.demo.orderItems.reIndex();

db.demo.orders.count();
db.demo.orderItems.count();
