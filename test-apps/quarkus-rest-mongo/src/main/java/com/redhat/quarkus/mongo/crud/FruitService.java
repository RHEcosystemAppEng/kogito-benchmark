package com.redhat.quarkus.mongo.crud;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class FruitService {
    private Logger logger = LoggerFactory.getLogger(FruitService.class);
    @Inject
    MongoClient mongoClient;

    public List<Fruit> list() {
        List<Fruit> list = new ArrayList<>();
        MongoCursor<Document> cursor = getCollection().find().iterator();

        try {
            while (cursor.hasNext()) {
                Document document = cursor.next();
                Fruit fruit = new Fruit();
                fruit.setName(document.getString("name"));
                fruit.setId(String.valueOf(document.getObjectId("_id")));
                fruit.setDescription(document.getString("description"));
                list.add(fruit);
            }
        } finally {
            cursor.close();
        }
        return list;
    }

    public void add(Fruit fruit) {
        Document document = new Document()
                .append("name", fruit.getName())
                .append("description", fruit.getDescription());
        getCollection().insertOne(document);
        logger.info("Fruit {} is added..!!",document.getObjectId("_id"));
    }

    private MongoCollection getCollection() {
        return mongoClient.getDatabase("kogito_dataindex").getCollection("demo.fruit");
    }
}
