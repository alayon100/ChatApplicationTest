package com.itver.alayon.chatapplicationtest.models;

/**
 * Created by Alayon on 03/12/2017.
 */

public class User {

    private String user_name;
    private String status;
    private String profile_image;

    public User() {

    }

    public User(String user_name, String status, String profile_image) {
        this.user_name = user_name;
        this.status = status;
        this.profile_image = profile_image;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_name='" + user_name + '\'' +
                ", status='" + status + '\'' +
                ", profile_image='" + profile_image + '\'' +
                '}';
    }
}
