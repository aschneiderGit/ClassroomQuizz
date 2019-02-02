package tk.aimeschneider.classroomquizz.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import tk.aimeschneider.classroomquizz.R;
import tk.aimeschneider.classroomquizz.ModelOnly.Connection;

public class MainActivity extends AppCompatActivity
{
    private TextView txtBienvenue;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button btnQuizz = (Button) findViewById(R.id.btnQuizz);

        btnQuizz.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                LaunchQuizz();
            }
        });
        final Button btnArrondissement = (Button) findViewById(R.id.btnArrondissement);

        btnArrondissement.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                LaunchArrondissement();
            }
        });

        final Button btnFriend = (Button) findViewById(R.id.btnFriend);

        btnFriend.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                LaunchFriend();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        txtBienvenue = (TextView) findViewById(R.id.txtBienvenue);
        txtBienvenue.setText("Bienvenue " + Connection.pseudo +" ! ");

    }

    private void LaunchQuizz()
    {
        Intent intent = new Intent(MainActivity.this, QuizzActivity.class);
        startActivity(intent);
    }
    private void LaunchArrondissement()
    {
        Intent intent = new Intent(MainActivity.this, ArrondissementActivity.class);
        startActivity(intent);
    }


    private void LaunchFriend()
    {
        Intent intent = new Intent(MainActivity.this, FriendActivity.class);
        startActivity(intent);
    }


}
