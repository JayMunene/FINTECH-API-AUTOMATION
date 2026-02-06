package com.jason.fintech.config;

/**
 * API Configuration Management for Different Environments
 * Supports DEV, QA, STAGING, and PRODUCTION environments
 */
public class ApiConfig {
    
    private static final String ENV = System.getProperty("env", "QA");
    
    public enum Environment {
        DEV("https://dev-api.fintech.com"),
        QA("https://jsonplaceholder.typicode.com"),
        STAGING("https://staging-api.fintech.com"),
        PROD("https://api.fintech.com");
        
        private final String baseUrl;
        
        Environment(String baseUrl) {
            this.baseUrl = baseUrl;
        }
        
        public String getBaseUrl() {
            return baseUrl;
        }
    }
    
    public static String getBaseUrl() {
        return Environment.valueOf(ENV.toUpperCase()).getBaseUrl();
    }
    
    public static String getEnvironment() {
        return ENV;
    }
    
    // API Endpoints
    public static class Endpoints {
        public static final String LOANS = "/posts";
        public static final String LOAN_BY_ID = "/posts/{id}";
        public static final String USERS = "/users";
        public static final String TRANSACTIONS = "/comments";
    }
    
    // Timeouts in milliseconds
    public static class Timeouts {
        public static final long CONNECTION_TIMEOUT = 10000;
        public static final long READ_TIMEOUT = 30000;
        public static final long MAX_RESPONSE_TIME = 2000;
    }
    
    // Test Data
    public static class TestData {
        public static final int DEFAULT_USER_ID = 1;
        public static final double MIN_LOAN_AMOUNT = 1000.00;
        public static final double MAX_LOAN_AMOUNT = 1000000.00;
        public static final double DEFAULT_INTEREST_RATE = 12.5;
        public static final int MIN_TENURE = 6; // months
        public static final int MAX_TENURE = 60; // months
    }
}
