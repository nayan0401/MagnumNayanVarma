package com.magnumnayan.app;

public class DataModel {
    String userImageUrl, userName;

    public DataModel(String userImageUrl, String userName) {
        this.userImageUrl = userImageUrl;
        this.userName = userName;
    }

    public String getUserImageUrl() {
        return userImageUrl;
    }

    public void setUserImageUrl(String userImageUrl) {
        this.userImageUrl = userImageUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
