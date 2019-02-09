package tk.aimeschneider.classroomquizz.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tk.aimeschneider.classroomquizz.R;

public class LightFragment extends Fragment {

        public LightFragment() {}

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_light, container, false);
            return view;
        }

}
