package tk.aimeschneider.classroomquizz.Fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.pedro.vlc.VlcListener;
import com.pedro.vlc.VlcVideoLibrary;

import tk.aimeschneider.classroomquizz.R;

public class CameraFragment extends Fragment implements VlcListener {
    private static String MRL =   "rtsp://wowzaec2demo.streamlock.net/vod/mp4:BigBuckBunny_115k.mov";
    private SurfaceView svCamera;
    private VlcVideoLibrary vlcPlayer;
        public CameraFragment() {}
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            Toast.makeText(getContext(), "Camera", Toast.LENGTH_SHORT).show();
            View view = inflater.inflate(R.layout.fragment_camera, container, false);
            //svCamera = (SurfaceView) view.findViewById(R.id.svCamera);
            //vlcPlayer = new VlcVideoLibrary(view.getContext(), this, svCamera);
           // vlcPlayer.play(MRL);
            VideoView videoView = (android.widget.VideoView)view.findViewById(R.id.svCamera);

            //add controls to a MediaPlayer like play, pause.
            MediaController mc = new MediaController(getContext());
            videoView.setMediaController(mc);

            //Set the path of Video or URI
            videoView.setVideoURI(Uri.parse(MRL));
            //

            //Set the focus
            videoView.requestFocus();
            return view;
        }

    @Override
    public void onComplete() {
        Toast.makeText(getActivity(), "Playing", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onError() {
        Toast.makeText(getActivity(), "Incorrect MRL", Toast.LENGTH_SHORT).show();
        vlcPlayer.stop();
    }


}
