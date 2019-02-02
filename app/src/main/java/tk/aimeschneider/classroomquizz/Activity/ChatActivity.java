package tk.aimeschneider.classroomquizz.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import org.json.JSONException;

import java.util.ArrayList;

import de.tavendo.autobahn.WebSocketConnection;
import de.tavendo.autobahn.WebSocketException;
import de.tavendo.autobahn.WebSocketHandler;
import tk.aimeschneider.classroomquizz.ModelOnly.Connection;
import tk.aimeschneider.classroomquizz.ModelOnly.Friend;
import tk.aimeschneider.classroomquizz.ModelOnly.Message;
import tk.aimeschneider.classroomquizz.MyElements.MessageAdapter;
import tk.aimeschneider.classroomquizz.R;

public class ChatActivity extends AppCompatActivity {

    private WebSocketConnection mConnection;

	String serverIpAddress = "192.168.137.101";
    private static final String TAG = "ChatActivity";

    private MessageAdapter mAdapter;
    private ArrayList<Message> messagesRL = new ArrayList<>();

    EditText messageET;
    ImageView sendBtn;
    RecyclerView recyclerView;
    public Friend me;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent i = getIntent();
        me = Connection.me;

        messageET = findViewById(R.id.editMessage);
        sendBtn = findViewById(R.id.sendIV);
        recyclerView = findViewById(R.id.messagesRL);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);

        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new MessageAdapter(messagesRL);

        recyclerView.setAdapter(mAdapter);

        try {
            mConnection = new WebSocketConnection();
            final String wsUrl = "ws://" + serverIpAddress + ":8080";
            mConnection.connect(wsUrl, new WebSocketHandler() {

                @Override
                public void onOpen() {
                    Log.d(TAG, "Connexion réussie à : " + wsUrl);
                }

                @Override
                public void onClose(int code, String reason) {

                    Log.d(TAG, "Connexion perdue");
                }

                @Override
                public void onTextMessage(String msg) {
                    Log.d(TAG, "Message reçu : " + msg);
                    try {
                        if(!Message.getMessageFromJson(msg).isMe()) {
                            messagesRL.add(Message.getMessageFromJson(msg));


                            mAdapter.notifyDataSetChanged();

                            scrollToBottom();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onRawTextMessage(byte[] msg) {
                }

                @Override
                public void onBinaryMessage(byte[] msg) {
                }
            });

        } catch (WebSocketException e) {
            e.printStackTrace();
        }


        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*
                 * On vérifie que notre edittext n'est pas vide
                 */
                if (!TextUtils.isEmpty(getMessage())) {

                    // On met "true" car c'est notre message
                    Message message = new Message(me, getMessage());

                    String json = Message.setMessageToJson(message);


                    // On envoie notre message
                    mConnection.sendTextMessage(json);
//                    mConnection.sendBinaryMessage(new byte[]);
//                    mConnection.sendRawTextMessage(new byte[]);

                    // On ajoute notre message à notre list
                    messagesRL.add(message);

                    // On notifie notre adapter
                    mAdapter.notifyDataSetChanged();

                    scrollToBottom();

                    // On efface !
                    messageET.setText("");
                }

            }
        });

    }

    /**
     * Scroller notre recyclerView en bas
     */
    private void scrollToBottom() {
        recyclerView.scrollToPosition(messagesRL.size() - 1);
    }

    private String getMessage() {
        return messageET.getText().toString().trim();
    }


}
