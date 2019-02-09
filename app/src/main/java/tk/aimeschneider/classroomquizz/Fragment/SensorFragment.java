package tk.aimeschneider.classroomquizz.Fragment;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

import tk.aimeschneider.classroomquizz.ModelOnly.Controller;
import tk.aimeschneider.classroomquizz.ModelOnly.MyPageAdapter;
import tk.aimeschneider.classroomquizz.R;

public class SensorFragment extends Fragment {


    public SensorFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle(R.string.sensor);
        View view = inflater.inflate(R.layout.fragment_sensor, container, false);
        ViewPager pagesVP = (ViewPager) view.findViewById(R.id.vpSensor);
        TabLayout slidingTL = (TabLayout)  view.findViewById(R.id.tlSlidSensor);
        MyPageAdapter adapter =  new MyPageAdapter (this.getContext() , getActivity().getSupportFragmentManager());
        pagesVP.setAdapter (adapter);
        slidingTL. setupWithViewPager ( pagesVP );
        return view;
    }

    @ Override
    public void onActivityResult(int requestCode, int resultCode, Intent i) {
        if (requestCode == 99 && resultCode == Activity.RESULT_OK) {
            Log.i(Controller.TAG, "Bluetooth  is enabled .");
        } else {
            Log.i(Controller.TAG, "Bluetooth must  be enabled .");
        }
    }


}