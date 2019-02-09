package tk.aimeschneider.classroomquizz.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import tk.aimeschneider.classroomquizz.ModelOnly.Controller;
import tk.aimeschneider.classroomquizz.R;

public class HomeFragment extends Fragment  {

    private TextView txtBienvenue;
    FragmentManager fm;
    public HomeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle(R.string.home);
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        final Button btnQuizz = (Button) view.findViewById(R.id.btnQuizz);
        btnQuizz.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                LaunchChallenge();
            }
        });
        final Button btnArrondissement = (Button) view.findViewById(R.id.btnArrondissement);
        btnArrondissement.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                LaunchChat();
            }
        });
        final Button btnFriend = (Button) view.findViewById(R.id.btnFriend);
        btnFriend.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                LaunchSensor();
            }
        });
        txtBienvenue = (TextView) view.findViewById(R.id.txtBienvenue);
        txtBienvenue.setText("Bienvenue " + Controller.me.getPrenom() +" ! ");
        fm = getFragmentManager();
        return view;
    }

    private void LaunchChallenge()
    {
        fm.beginTransaction().replace(R.id.contentFL, new ChallengeFragment()).commit();
    }
    private void LaunchChat()
    {
        fm.beginTransaction().replace(R.id.contentFL, new ChatFragment()).commit();
    }
    private void LaunchSensor()
    {
        fm.beginTransaction().replace(R.id.contentFL, new SensorFragment()).commit();
    }



}
