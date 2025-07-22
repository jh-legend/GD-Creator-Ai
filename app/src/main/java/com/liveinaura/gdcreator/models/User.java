package com.liveinaura.gdcreator.models;

import com.google.firebase.Timestamp;

public class User {
    private String name;
    private String email;
    private String subscriptionStatus;
    private Timestamp subscriptionStartDate;
    private Timestamp subscriptionExpiryDate;
    private String deviceId;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String name, String email, String subscriptionStatus, Timestamp subscriptionStartDate, Timestamp subscriptionExpiryDate, String deviceId) {
        this.name = name;
        this.email = email;
        this.subscriptionStatus = subscriptionStatus;
        this.subscriptionStartDate = subscriptionStartDate;
        this.subscriptionExpiryDate = subscriptionExpiryDate;
        this.deviceId = deviceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubscriptionStatus() {
        return subscriptionStatus;
    }

    public void setSubscriptionStatus(String subscriptionStatus) {
        this.subscriptionStatus = subscriptionStatus;
    }

    public Timestamp getSubscriptionStartDate() {
        return subscriptionStartDate;
    }

    public void setSubscriptionStartDate(Timestamp subscriptionStartDate) {
        this.subscriptionStartDate = subscriptionStartDate;
    }

    public Timestamp getSubscriptionExpiryDate() {
        return subscriptionExpiryDate;
    }

    public void setSubscriptionExpiryDate(Timestamp subscriptionExpiryDate) {
        this.subscriptionExpiryDate = subscriptionExpiryDate;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
