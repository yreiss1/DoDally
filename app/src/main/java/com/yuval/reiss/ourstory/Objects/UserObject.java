package com.yuval.reiss.ourstory.Objects;

import java.util.ArrayList;

public class UserObject {

    private String email;
    private String username;
    private String uid;
    private String notify_id;
    private ArrayList<TaskObject> tasks;

    public UserObject() {

    }

    public UserObject(String email, String username,  String uid, String notify_id) {
        this.email = email;
        this.username = username;
        this.uid = uid;
        this.notify_id = notify_id;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }


    public String getNotify_id() {
        return notify_id;
    }

    public void setNotify_id(String notify_id) {
        this.notify_id = notify_id;
    }


    public void setTasks(ArrayList<TaskObject> tasks) {
        this.tasks = tasks;
    }

    /*
    public ArrayList<TaskObject> getTasks() {
        return tasks;
    }
    */
}

