package tk.aimeschneider.classroomquizz.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;


import tk.aimeschneider.classroomquizz.ModelOnly.Controller;
import tk.aimeschneider.classroomquizz.ModelOnly.Friend;
import tk.aimeschneider.classroomquizz.R;

import static android.support.constraint.Constraints.TAG;
import static tk.aimeschneider.classroomquizz.ModelOnly.Controller.SERVER_KEY;
import static tk.aimeschneider.classroomquizz.ModelOnly.Controller.WEB_CONNECTION_REQUEST;
import static tk.aimeschneider.classroomquizz.ModelOnly.Controller.isConnection;

public class LoginActivity extends AppCompatActivity {

    private ProgressDialog pDialog;
    private EditText edtLogin;
    private EditText edtMdp;
    private Spinner spServer;
    private Button loginButton;
    private TextView edtServer;

    private View.OnClickListener btnLoginListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v) {
            pDialog = new ProgressDialog(loginButton.getContext());
            pDialog.setMessage("Connection...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
            try {
                Controller.SetLocalhost(edtServer.getText().toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            String url = WEB_CONNECTION_REQUEST + "?key=" + SERVER_KEY + "&username=" + edtLogin.getText().toString()+"&password="+edtMdp.getText().toString();
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
                                    if(new JSONObject(result).has("token")) {
                                        Controller.me = Friend.JsonToUser(new JSONObject(result));
                                        Controller.addLocalhostTxt();
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(intent);
                                    }
                                }
                                catch (JSONException e1)
                                {
                                    e1.printStackTrace();
                                }
                            }
                        }
                    });
        }
    };

    private AdapterView.OnItemSelectedListener svSpinnerListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            edtServer.setText(spServer.getItemAtPosition(position).toString());
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtLogin = (EditText) findViewById(R.id.edtLogin);
        edtMdp = (EditText) findViewById(R.id.edtMdp);
        spServer = (Spinner) findViewById(R.id.spServer);
        edtServer = (EditText) findViewById(R.id.edtServer);
        spServer.setOnItemSelectedListener(svSpinnerListener);
        try {
            Controller.CreateDirectory();
            Controller.ReadLocalhost();
            // set the spinner data programmatically, from a string array or list
            ArrayAdapter<String> adapter = new ArrayAdapter<String>( this, android.R.layout.simple_spinner_item, Controller.ARRAY_LOCALHOST);
            spServer.setAdapter(adapter);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        loginButton = (Button) findViewById(R.id.btnLogin);
        loginButton.setOnClickListener(btnLoginListener);
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
}
