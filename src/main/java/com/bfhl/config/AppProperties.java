package com.bfhl.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    // e.g., "john_doe"
    private String fullName;
    // e.g., "17091999"
    private String dobDdmmyyyy;
    // e.g., "john@xyz.com"
    private String email;
    // e.g., "ABCD123"
    private String rollNumber;

    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public String getDobDdmmyyyy() {
        return dobDdmmyyyy;
    }
    public void setDobDdmmyyyy(String dobDdmmyyyy) {
        this.dobDdmmyyyy = dobDdmmyyyy;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getRollNumber() {
        return rollNumber;
    }
    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }

    public String buildUserId() {
        String name = (fullName == null) ? "" : fullName.toLowerCase().trim();
        String dob = (dobDdmmyyyy == null) ? "" : dobDdmmyyyy.trim();
        if (!dob.matches("^\\d{8}$")) {
            // Fallback: keep whatever provided
        }
        return name + "_" + dob;
    }
}