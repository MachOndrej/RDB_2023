package database_communication;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import configs.MongoConfig;
import models.HodnotyMereni;
import org.bson.Document;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.Logger;
import org.bson.conversions.Bson;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mongodb.client.AggregateIterable;

public class MongoDBManager {

    private MongoClient mongoClient;
    private MongoCollection<Document> collection;

    public MongoDBManager() {
        try {
            // Step 1: Create a MongoClient instance
            mongoClient = MongoClients.create(MongoConfig.CONNECTION_STRING);

            // Step 2: Access the MongoDB database
            MongoDatabase database = mongoClient.getDatabase(MongoConfig.DATABASE_NAME);

            // Step 3: Access the collection
            collection = database.getCollection(MongoConfig.COLLECTION_NAME);

            LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
            Logger rootLogger = loggerContext.getLogger("org.mongodb.driver");
            rootLogger.setLevel(Level.OFF);
        } catch (MongoException e) {
            System.err.println("Error connecting to MongoDB: " + e.getMessage());
        }
    }

    public void closeConnection() {
        mongoClient.close();
    }

    // INSERT
    public void insertDocument(Document document) {
        try {
            collection.insertOne(document);
        } catch (MongoException e) {
            System.err.println("Error inserting document: " + e.getMessage());
        }
    }

    // QUERY
    public void queryDocuments(String fieldName, String fieldValue) {
        FindIterable<Document> documents = collection.find(Filters.eq(fieldName, fieldValue));
        for (Document document : documents) {
            System.out.println(document.toJson());
        }
    }

    // UPDATE
    public void updateDocuments(String fieldName, String fieldValue, String updateFieldName, Object updateValue) {
        try {
            collection.updateOne(Filters.eq(fieldName, fieldValue), Updates.set(updateFieldName, updateValue));
            System.out.println("Documents updated successfully!");
        } catch (MongoException e) {
            System.err.println("Error updating documents: " + e.getMessage());
        }
    }

    public ArrayList<Double> getSameTempsTwoPlaces(String place1, String place2){
//        MongoClientURI uri = new MongoClientURI(CONNECTION_STRING);
//        try (MongoClient mongoClient = new MongoClient(uri)) {
//            MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
        //MongoCollection<HodnotyMereni> collection = database.getCollection(COLLECTION_NAME);

//        List<HodnotyMereni> pipeline = Arrays.asList(
//                new HodnotyMereni("$match", new HodnotyMereni("place", new HodnotyMereni("$in", Arrays.asList(place1, place2)))),
//                new HodnotyMereni("$group", new HodnotyMereni("_id", "$datetime")
//                        .append("temperatures", new HodnotyMereni("$addToSet", "$temperature"))),
//                new HodnotyMereni("$match", new HodnotyMereni("temperatures", new HodnotyMereni("$size", new HodnotyMereni("$gt", 1))))
//        );
//
//        AggregateIterable<HodnotyMereni> result = collection.aggregate((List<? extends Bson>) pipeline);
//
//        // ulozeni do listu
//        List<HodnotyMereni> queryResults = new ArrayList<>();
//        for (HodnotyMereni hodnotyMereni : result) {
//            queryResults.add(hodnotyMereni);
//        }
        ArrayList<Double> queryResults = new ArrayList<>();

        ArrayList<Double> queryResultsPlace1 = new ArrayList<>();
        ArrayList<Double> queryResultsPlace2 = new ArrayList<>();

        Bson filter1 = Filters.eq("place", place1);
        Bson filter2 = Filters.eq("place", place2);


        collection.find(filter1).forEach(doc -> queryResultsPlace1.add(doc.getDouble("temperature")));
        collection.find(filter2).forEach(doc -> queryResultsPlace2.add(doc.getDouble("temperature")));

        for (Double temp:
             queryResultsPlace1) {
            if (queryResultsPlace2.contains(temp)){
                queryResults.add(temp);
            }
        }

        // Return the query results
        return queryResults;
    }

    public double[] getMinMaxTempsForPlace(String place){
        double[] returnValues = new double[2];
        Bson filter = Filters.eq("place", place);

        collection.aggregate(
                Arrays.asList(
                        Aggregates.match(filter),
                        Aggregates.group(null, Accumulators.min("min", "$temperature"))
                )
        ).forEach(doc -> returnValues[0] = (double) doc.get("min"));

        collection.aggregate(
                Arrays.asList(
                        Aggregates.match(filter),
                        Aggregates.group(null, Accumulators.max("max", "$temperature"))
                )
        ).forEach(doc -> returnValues[1] = (double) doc.get("max"));

        return returnValues;
    }

    public static void main(String[] args) {
        MongoDBManager mongoDBManager = new MongoDBManager();

//        System.out.println(Arrays.toString(mongoDBManager.getMinMaxTempsForPlace("Prh")));
        System.out.println(mongoDBManager.getSameTempsTwoPlaces("Zapalovac", "Prh"));


//todo: POROVNANI HODNOT
//        getSameTempsTwoPlaces(place1, place2);

        mongoDBManager.closeConnection();
    }

}
