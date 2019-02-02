package tk.aimeschneider.classroomquizz.ModelOnly;

import org.json.JSONException;
import org.json.JSONObject;

public class Friend {

    static private String JSON_FRIEND_ID = "id";
    static private String JSON_FRIEND_TOKEN = "token";
    static private String JSON_FRIEND_PRENOM = "first_name";
    static private String JSON_FRIEND_NOM = "last_name";
    static private String JSON_FRIEND_LAST_SCORE = "last_score";
    static private String JSON_FRIEND_PHOTO_PATH = "profile_url";
    static private String JSON_FRIEND_COLOR = "random_color";
    static private String JSON_FRIEND_PRESENT = "is_present";



    private int id;
    private String prenom;
    private String name;
    private String token;
    private int lastScore;
    private String photoPath;
    private String randomColor;
    private int present;

    public Friend(int id, String token, String prenom, String name, int lastScore ,String photoPath, String randomColor,int present ) {

        this.id = id;
        this.token = token;
        this.prenom = prenom;
        this.name = name;
        this.lastScore = lastScore;
        this.photoPath = photoPath;
        this.randomColor = randomColor;
        this.present = present;
    }

    public int getId() { return id; }

    public String getToken() { return token; }

    public String getPrenom() {
        return prenom;
    }

    public String getName() {
        return name;
    }

    public int getLastScore() { return lastScore; }

    public String getPhotoPath() { return photoPath; }

    public String getRandomColor() { return randomColor; }

    public boolean isPresent() { return (present==1); }


    public static Friend JsonToFriend(JSONObject json) throws JSONException {

        int id = json.getInt(JSON_FRIEND_ID);
        String prenom = json.getString(JSON_FRIEND_PRENOM);
        String nom = json.getString(JSON_FRIEND_NOM);
        int lastScore;
        if (!json.isNull( JSON_FRIEND_LAST_SCORE ))
            lastScore = json.getInt(JSON_FRIEND_LAST_SCORE);
        else
            lastScore = 0;
        String photo = json.getString(JSON_FRIEND_PHOTO_PATH);
        String randomColor = json.getString(JSON_FRIEND_COLOR);
        int present = 0;
        if(json.has(JSON_FRIEND_PRESENT))
            present = json.getInt(JSON_FRIEND_PRESENT);

        return new Friend(id,"",prenom,nom,lastScore,photo,randomColor,present);
    }

    public static Friend JsonToUser(JSONObject json) throws JSONException {

        int id = json.getInt(JSON_FRIEND_ID);
        String prenom = json.getString(JSON_FRIEND_PRENOM);
        String nom = json.getString(JSON_FRIEND_NOM);
        String token = json.getString(JSON_FRIEND_TOKEN);
        int lastScore;
        if (!json.isNull( JSON_FRIEND_LAST_SCORE ))
            lastScore = json.getInt(JSON_FRIEND_LAST_SCORE);
        else
            lastScore = 0;
        String photo = json.getString(JSON_FRIEND_PHOTO_PATH);
        String randomColor = json.getString(JSON_FRIEND_COLOR);
        int present = json.getInt(JSON_FRIEND_PRESENT);

        return new Friend(id,token,prenom,nom,lastScore,photo,randomColor,present);
    }
}
