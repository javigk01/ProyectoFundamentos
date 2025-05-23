package fundamentos.adcbank.services;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

/**
 * @brief Service class for managing MongoDB database connections in the ADCBank application.
 */
public class DatabaseService {

    /** @brief Singleton instance of the DatabaseService. */
    private static DatabaseService instance;

    /** @brief MongoDB client instance. */
    private MongoClient mongoClient;

    /** @brief MongoDB database instance. */
    private MongoDatabase database;

    /**
     * @brief Private constructor to enforce singleton pattern and initialize MongoDB connection.
     */
    private DatabaseService() {
        ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017");
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
        mongoClient = MongoClients.create(settings);
        database = mongoClient.getDatabase("bankdb");
    }

    /**
     * @brief Gets the singleton instance of the DatabaseService.
     * @return The DatabaseService instance.
     */
    public static DatabaseService getInstance() {
        if (instance == null) {
            instance = new DatabaseService();
        }
        return instance;
    }

    /**
     * @brief Gets the MongoDB database instance.
     * @return The MongoDatabase instance.
     */
    public MongoDatabase getDatabase() {
        return database;
    }
}