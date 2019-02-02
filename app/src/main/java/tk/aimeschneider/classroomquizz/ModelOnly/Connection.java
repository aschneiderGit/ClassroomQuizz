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


//classe qui va servir à interroger la bd
abstract public class Connection {

    private static String JSON_PASSWORD = "password";

    static public String SERVER_KEY = "iot1235";
    static public String LOCALHOST = "http://192.168.137.101" ;
    static public String LOCALHOST_TXT = "/Localhost.txt";
    static public String LOCALHOST_PATH = "/android/data/tk.aimeschneider/ClassroomQuizz";
    final static String TAG = Connection.class.getName();
    static public String WEB_CONNECTION_REQUEST ;
    static public String WEB_QUESTION_REQUEST ;
    static public String WEB_QUESTION_ARRONDISSEMENT_REQUEST  ;
    static public String WEB_FRIENDS_REQUEST ;
    static public String WEB_FRIENDS_IMG ;
    public static String TOKEN;

    public static String pseudo ="";
    public static Friend me;

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


    public static void SetLocalhost() {

        try {
            File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + LOCALHOST_PATH + LOCALHOST_TXT);
            FileInputStream fileInputStream = new FileInputStream(f);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            LOCALHOST = bufferedReader.readLine();
            WEB_CONNECTION_REQUEST = LOCALHOST + "/iot-server/api/login.php";
            WEB_QUESTION_REQUEST = LOCALHOST + "/classroom_server/getQuestions.php";
            WEB_QUESTION_ARRONDISSEMENT_REQUEST = LOCALHOST + "/classroom_server/getQuestionsArrondissement.php";
            WEB_FRIENDS_REQUEST = LOCALHOST + "/iot-server/api/getFriends.php";
            WEB_FRIENDS_IMG = LOCALHOST + "/iot-server/profiles/";

            bufferedReader.close();
        } catch (FileNotFoundException ex) {
            Log.d(TAG, ex.getMessage());
        } catch (IOException ex) {
            Log.d(TAG, ex.getMessage());
        }
    }

    public static void CreateDirectory() throws FileNotFoundException, UnsupportedEncodingException {
        File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + LOCALHOST_PATH + LOCALHOST_TXT);
        if (!f.exists())
        {
            File d = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + LOCALHOST_PATH );
            d.mkdirs();
            PrintWriter writer = new PrintWriter(Environment.getExternalStorageDirectory().getAbsolutePath() + LOCALHOST_PATH + LOCALHOST_TXT, "UTF-8");
            writer.println(LOCALHOST);
            writer.close();
        }
    }

}
