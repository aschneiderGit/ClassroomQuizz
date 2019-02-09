package tk.aimeschneider.classroomquizz.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;
import com.pedro.vlc.VlcListener;
import com.pedro.vlc.VlcVideoLibrary;

import java.util.ArrayList;
import java.util.List;

import me.dm7.barcodescanner.zbar.BarcodeFormat;
import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;
import tk.aimeschneider.classroomquizz.Activity.QuizzActivity;
import tk.aimeschneider.classroomquizz.ModelOnly.Controller;
import tk.aimeschneider.classroomquizz.R;

import static tk.aimeschneider.classroomquizz.ModelOnly.Controller.TAG;

public class ScannerFragment extends Fragment implements ZBarScannerView.ResultHandler  {
    ZBarScannerView scannerView;

    public ScannerFragment() {}
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_scanner, container, false);
            Toast.makeText(getContext(), "Scanner", Toast.LENGTH_SHORT).show();
            scannerView = new ZBarScannerView(view.getContext());
            List<BarcodeFormat> formats = new ArrayList<>();
            formats.add(BarcodeFormat.QRCODE);
            scannerView.setFormats(formats);
            FrameLayout contentFL = view.findViewById(R.id.flScanner);
            contentFL.addView(scannerView);

            return view;
        }

    public void remoteSign(String token, String qrcode) {
        String urlString = "http://"+Controller.LOCALHOST+"/iot-server/api/qrcode-sign.php?key=iot1235&token="+token+"&qrcode="+qrcode;
        Ion.with(this)
                .load(urlString)
                .asString().withResponse()
                .setCallback(new FutureCallback<Response<String>>() {
                    @Override
                    public void onCompleted(Exception e, Response<String> result) {
                        if (result.getHeaders().code() == 200) {
                            Toast.makeText(getActivity(), "SCanner", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    @Override
    public void onResume() {
        super.onResume();
        // Register ourselves as a handler for scan results.
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }
    @Override
    public void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    @Override
    public void handleResult(Result rawResult) {
        String qrcode = rawResult.getContents();
        Log.d(TAG, "QRcode" + qrcode);
        remoteSign(Controller.me.getToken(), qrcode);
    }
}

