package tk.aimeschneider.classroomquizz.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import tk.aimeschneider.classroomquizz.R;
import tk.aimeschneider.classroomquizz.ModelOnly.Connection;
import tk.aimeschneider.classroomquizz.ModelOnly.Friend;

import static android.support.constraint.Constraints.TAG;
import static tk.aimeschneider.classroomquizz.ModelOnly.Connection.SERVER_KEY;
import static tk.aimeschneider.classroomquizz.ModelOnly.Connection.WEB_CONNECTION_REQUEST;

public class LoginActivity extends AppCompatActivity {

    private static String JSON_LOGIN_INFO = "info";

    private ProgressDialog pDialog;
    private EditText edtLogin;
    private EditText edtMdp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtLogin = (EditText) findViewById(R.id.edtLogin);
        edtMdp = (EditText) findViewById(R.id.edtMdp);
        final Button loginButton = (Button) findViewById(R.id.btnLogin);
        try {
            Connection.CreateDirectory();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Connection.SetLocalhost();
                pDialog = new ProgressDialog(loginButton.getContext());
                pDialog.setMessage("Connection...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
                pDialog.show();
                String url = WEB_CONNECTION_REQUEST + "?key=" + SERVER_KEY + "&username=" + edtLogin.getText().toString()+ "&password="+ edtMdp.getText().toString();
                Ion.with(loginButton.getContext())
                        .load(url)
                        .asString()
                        .setCallback(new FutureCallback<String>() {
                            @Override
                            public void onCompleted(Exception e, String result) {
                                pDialog.dismiss();
                                if (result == null)
                                    Log.d(TAG, "No response from the server!!!");
                                else {
                                    try
                                    {
                                        JSONObject jsonUser = new JSONObject(result);
                                        Friend f = Friend.JsonToUser(jsonUser);
                                        Connection.pseudo = f.getPrenom();
                                        Connection.me = f;
                                        Connection.TOKEN = f.getToken();
                                        Intent intent = new Intent(LoginActivity.this, Main2Activity.class);
                                        startActivity(intent);
                                    }
                                    catch (JSONException e1) {
                                        e1.printStackTrace();
                                    }
                                }
                            }
                        });
            }
        });
    }
}
