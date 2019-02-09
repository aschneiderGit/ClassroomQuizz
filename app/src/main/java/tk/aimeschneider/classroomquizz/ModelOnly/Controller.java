package tk.aimeschneider.classroomquizz.ModelOnly;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;


//classe qui va servir à interroger la bd
abstract public class Controller {

    private static String JSON_PASSWORD = "password";
    public static ArrayList<String> ARRAY_LOCALHOST;
    public static String HTTP = "http://";
    static public String SERVER_KEY = "iot1235";
    static public String LOCALHOST = "" ;
    static public String LOCALHOST_TXT = "/Localhost.txt";
    static public String LOCALHOST_PATH = "/android/data/tk.aimeschneider/ClassroomQuizz";
    public static String TAG = Controller.class.getName();
    static public String WEB_CONNECTION_REQUEST ;
    static public String WEB_QUESTION_REQUEST ;
    static public String WEB_QUESTION_ARRONDISSEMENT_REQUEST  ;
    static public String WEB_FRIENDS_REQUEST ;
    static public String WEB_FRIENDS_IMG ;
    public static String TOKEN;

    public static String pseudo ="";
    public static Friend me;
    public static ArrayList<Friend> myFriends;

    public static boolean checkConnection(Context ctx) {
        ConnectivityManager connectivityManager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected() ||
                (networkInfo.getType() != ConnectivityManager.TYPE_WIFI &&
                        networkInfo.getType() != ConnectivityManager.TYPE_MOBILE)) {
            // No internet connection
            return false;
        } else
            return true;
    }

    public static boolean isConnection( String password, JSONObject json)
    {
        try {
            return (password.equals(json.getString(JSON_PASSWORD)));
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }

    }

    public static void SetLocalhost(String s) throws IOException {

        //vérifie que le server exist deja dans la fichier sinon il l'ajoute
        LOCALHOST =   s;
        WEB_CONNECTION_REQUEST = HTTP + LOCALHOST + "/iot-server/api/login.php";
        WEB_QUESTION_REQUEST = HTTP + LOCALHOST + "/iot-server/api/getQuestions.php";
        WEB_QUESTION_ARRONDISSEMENT_REQUEST = HTTP + LOCALHOST + "/iot-server/api/getQuestionsArrondissement.php";
        WEB_FRIENDS_REQUEST = HTTP + LOCALHOST + "/iot-server/api/getFriends.php";
        WEB_FRIENDS_IMG = HTTP + LOCALHOST + "/iot-server/api/profiles/";
    }

    public static void addLocalhostTxt()
    {

        try {
            File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + LOCALHOST_PATH + LOCALHOST_TXT);
            FileInputStream fileInputStream = new FileInputStream(f);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            String file ="";
            boolean exist = false;
            while (line != null) {
                exist = line.equals(LOCALHOST);
                if(exist)
                    break;
                file = file + line + "\n";
                line = reader.readLine();
            }
            reader.close();
            if (!exist) {
                PrintWriter writer = new PrintWriter(Environment.getExternalStorageDirectory().getAbsolutePath() + LOCALHOST_PATH + LOCALHOST_TXT, "UTF-8");
                writer.print(file  + LOCALHOST);
                writer.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void CreateDirectory() throws FileNotFoundException, UnsupportedEncodingException {
        File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + LOCALHOST_PATH + LOCALHOST_TXT);
        if (!f.exists()) {
            File d = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + LOCALHOST_PATH);
            d.mkdirs();
            PrintWriter writer = new PrintWriter(Environment.getExternalStorageDirectory().getAbsolutePath() + LOCALHOST_PATH + LOCALHOST_TXT, "UTF-8");
            writer.print(LOCALHOST);
            writer.close();

        }
    }

    public static void ReadLocalhost() throws IOException {
        ARRAY_LOCALHOST = new ArrayList<>();
        File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + LOCALHOST_PATH + LOCALHOST_TXT);
        FileInputStream fileInputStream = new FileInputStream(f);
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
        BufferedReader reader = new BufferedReader(inputStreamReader);
        String line = reader.readLine();
        while (line != null) {
            if (!line.isEmpty())
                ARRAY_LOCALHOST.add(line);
            line = reader.readLine();
        }
        reader.close();
    }
}
