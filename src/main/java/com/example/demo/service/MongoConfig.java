package com.example.demo.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;


import java.util.List;

@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {

    // rest of the config goes here

    @Value("${spring.data.mongodb.database}")
    private String dbName;

    @Override
    protected String getDatabaseName() {
        return dbName;
    }

    @Override
    protected boolean autoIndexCreation() {
        return true;
    }


}