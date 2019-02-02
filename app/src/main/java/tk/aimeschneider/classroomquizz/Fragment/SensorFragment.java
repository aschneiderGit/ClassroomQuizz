package tk.aimeschneider.classroomquizz.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tk.aimeschneider.classroomquizz.R;

public class SensorFragment extends Fragment {

    public SensorFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle(R.string.sensor_title);
        View view = inflater.inflate(R.layout.fragment_sensor, container, false);
        return view;
    }
}