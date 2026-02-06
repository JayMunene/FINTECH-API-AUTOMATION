package com.jason.fintech.models;

/**
 * POJO for Loan Request
 * Represents the structure of a loan application request
 */
public class LoanRequest {
    
    private int userId;
    private double amount;
    private double interestRate;
    private int tenure;
    private String purpose;
    private String applicationDate;
    
    // Constructors
    public LoanRequest() {}
    
    public LoanRequest(int userId, double amount, double interestRate, int tenure, String purpose) {
        this.userId = userId;
        this.amount = amount;
        this.interestRate = interestRate;
        this.tenure = tenure;
        this.purpose = purpose;
    }
    
    // Getters and Setters
    public int getUserId() {
        return userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public double getAmount() {
        return amount;
    }
    
    public void setAmount(double amount) {
        this.amount = amount;
    }
    
    public double getInterestRate() {
        return interestRate;
    }
    
    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }
    
    public int getTenure() {
        return tenure;
    }
    
    public void setTenure(int tenure) {
        this.tenure = tenure;
    }
    
    public String getPurpose() {
        return purpose;
    }
    
    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }
    
    public String getApplicationDate() {
        return applicationDate;
    }
    
    public void setApplicationDate(String applicationDate) {
        this.applicationDate = applicationDate;
    }
    
    @Override
    public String toString() {
        return "LoanRequest{" +
                "userId=" + userId +
                ", amount=" + amount +
                ", interestRate=" + interestRate +
                ", tenure=" + tenure +
                ", purpose='" + purpose + '\'' +
                ", applicationDate='" + applicationDate + '\'' +
                '}';
    }
}
