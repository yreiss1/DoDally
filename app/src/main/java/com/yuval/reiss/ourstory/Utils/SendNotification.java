package com.yuval.reiss.ourstory.Utils;

import android.content.Context;

import com.onesignal.OneSignal;

import org.json.JSONException;
import org.json.JSONObject;

public class SendNotification {

    public SendNotification(String message, String heading, String notificationKey, final Context context) {


        try {
            JSONObject notificationContent = new JSONObject(
                    "{'contents':{'en':'" + message + "'}," +
                            "'include_player_ids':['" + notificationKey + "']," +
                            "'headings':{'en': '" + heading + "'}," +
                            "'buttons':[{'id': 'id1', 'text': 'Send Back'}, {'id':'id2', 'text': 'Clear'}]}");

            OneSignal.postNotification(notificationContent, null);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
