package com.miniweam.boxeld.Models;

public class Users {

    String email, username, password, profilePic, userId, lastMessage, status, audioFileTitle;

    //general constructor
    public Users () {

    }

    public Users(String email, String userName, String password, String profilePic, String userId, String lastMessage, String status, String audioFileTitle) {
        this.email = email;
        this.username = userName;
        this.password = password;
        this.profilePic = profilePic;
        this.userId = userId;
        this.lastMessage = lastMessage;
        this.status = status;
        this.audioFileTitle = audioFileTitle;
    }

    public String getAudioFileTitle() {
        return audioFileTitle;
    }

    public void setAudioFileTitle(String audioFileTitle) {
        this.audioFileTitle = audioFileTitle;
    }

    //constructor for register
    public Users(String email, String userName, String password) {
        this.email = email;
        this.username = userName;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return username;
    }

    public void setUserName(String userName) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
