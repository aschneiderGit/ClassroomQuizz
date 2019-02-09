package tk.aimeschneider.classroomquizz.ModelOnly;


import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static tk.aimeschneider.classroomquizz.ModelOnly.Controller.TAG;

public class Message {

    static private int INDEX_JSON_SOURCE = 0;
    static private int INDEX_JSON_MSG = 1;

    private Friend source;
    private String message;

    public Message(Friend source, String message) {
        this.source = source;
        this.message = message;
    }




    public Friend getSource() {
        return source;
    }

    public String getMessage() {
        return message;
    }

    public static Message getMessageFromJson(String msg) throws JSONException {
        JSONObject msgJSON = new JSONObject(msg);
        Friend src = Friend.JsonToFriend( msgJSON.getJSONObject("source"));
        return new Message(src,msgJSON.getString("message"));
    }

    public static String setMessageToJson(Message message)
    {
        Friend f = message.getSource();
        String json = "{\"source\":\"{" +
                "\\\"token\\\": \\\"" + f.getToken() + "\\\"" + "}\", "+
                "\"message\" : \"" + message.getMessage() +"\"}";
        Log.i(TAG, "setMessageToJson: " + json);
        return json;
    }


    public boolean isMe()
    {
        return (source.getId() == Controller.me.getId());
    }

}


