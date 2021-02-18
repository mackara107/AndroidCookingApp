package com.example.beginnercookingapp;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.MongoException;
import com.mongodb.stitch.android.core.Stitch;

import java.util.Iterator;
import java.util.Scanner;

import org.bson.BsonInt32;
import org.bson.Document;

public class MongoConnect {

    @SuppressWarnings("resource")
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        try {
            MongoClient mongoClient = MongoClients.create("mongodb+srv://TeamA:<password>@cookingapp-ebxfb.azure.mongodb.net/test?retryWrites=true&w=majority");
            // Connect to teamA database
            MongoDatabase teamA = mongoClient.getDatabase("CookingApp");
            // get the Recipe collection
            MongoCollection<Document> recipe = teamA.getCollection("CookingApp");
            System.out.println("Number of recipes " + recipe.countDocuments());
            Document list = new Document();
            FindIterable<Document> menu = recipe.find(list).projection(new BasicDBObject("menu", new BsonInt32(1)));
            Iterator<Document> it = menu.iterator();
            while (it.hasNext()) {
                System.out.println(it.next().toJson());
            }
            System.out.println();

            Document whereQ = new Document();

            System.out.print("Search: ");
            String command = input.nextLine();
            // here to identify the regex and ignore case
            whereQ.put("menu", new Document("$regex", command).append("$options", "i"));
            // apply the filter
            FindIterable<Document> find = recipe.find(whereQ);

            Iterator<Document> itr = find.iterator();
            int count = 0;
            while (itr.hasNext()) {
                System.out.println(itr.next().toJson());
                count++;
            }
            if (count == 0)
                System.out.println("No recipe found!");

        } catch (MongoException e) {
            e.printStackTrace();
        }
        input.close();
    }

}

