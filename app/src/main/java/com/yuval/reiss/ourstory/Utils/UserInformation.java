package com.yuval.reiss.ourstory.Utils;

import com.google.firebase.auth.FirebaseAuth;

public class UserInformation {

    private static String mUsername;
    private static String mEmail;
    private static String uid = FirebaseAuth.getInstance().getUid();
    private static String mNotify;
    private static String mStoryID;

    public static String getStoryID() {
        return mStoryID;
    }

    public static void setStoryID(String mStoryID) {
        UserInformation.mStoryID = mStoryID;
    }

    public static String getUid() {
        return uid;
    }

    public static String getEmail() {
        return mEmail;
    }

    public static String getUsername() {
        return mUsername;
    }

    public static void setUid(String uid) {
        UserInformation.uid = uid;
    }

    public static void setUsername(String mUsername) {
        UserInformation.mUsername = mUsername;
    }

    public static void setEmail(String mEmail) {
        UserInformation.mEmail = mEmail;
    }

    public static String getNotify() {
        return mNotify;
    }

    public static void setNotify(String notify) {
       UserInformation.mNotify = notify;
    }
}
