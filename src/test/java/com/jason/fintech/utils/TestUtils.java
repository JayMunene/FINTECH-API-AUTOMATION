package com.jason.fintech.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Utility class for test data generation and common test operations
 */
public class TestUtils {
    
    private static final Random random = new Random();
    
    /**
     * Generate random loan amount within specified range
     */
    public static double generateLoanAmount(double min, double max) {
        return min + (max - min) * random.nextDouble();
    }
    
    /**
     * Generate random tenure in months
     */
    public static int generateTenure(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }
    
    /**
     * Calculate simple interest
     */
    public static double calculateSimpleInterest(double principal, double rate, int months) {
        return (principal * rate * months) / (12 * 100);
    }
    
    /**
     * Calculate EMI (Equated Monthly Installment)
     */
    public static double calculateEMI(double principal, double rate, int months) {
        double monthlyRate = rate / (12 * 100);
        return (principal * monthlyRate * Math.pow(1 + monthlyRate, months)) 
               / (Math.pow(1 + monthlyRate, months) - 1);
    }
    
    /**
     * Generate loan test data
     */
    public static Map<String, Object> generateLoanData(int userId, double amount, int tenure) {
        Map<String, Object> loanData = new HashMap<>();
        loanData.put("userId", userId);
        loanData.put("amount", amount);
        loanData.put("interestRate", 12.5);
        loanData.put("tenure", tenure);
        loanData.put("purpose", "Business Loan");
        loanData.put("applicationDate", getCurrentDate());
        return loanData;
    }
    
    /**
     * Get current date in ISO format
     */
    public static String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date());
    }
    
    /**
     * Generate unique transaction ID
     */
    public static String generateTransactionId() {
        return "TXN" + System.currentTimeMillis() + random.nextInt(1000);
    }
    
    /**
     * Validate loan amount is within acceptable range
     */
    public static boolean isValidLoanAmount(double amount, double min, double max) {
        return amount >= min && amount <= max;
    }
    
    /**
     * Format currency for display
     */
    public static String formatCurrency(double amount) {
        return String.format("₹%.2f", amount);
    }
    
    /**
     * Log test info to console with formatting
     */
    public static void logInfo(String message) {
        System.out.println("[INFO] " + getCurrentTimestamp() + " - " + message);
    }
    
    /**
     * Get current timestamp
     */
    private static String getCurrentTimestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");
        return sdf.format(new Date());
    }
}
