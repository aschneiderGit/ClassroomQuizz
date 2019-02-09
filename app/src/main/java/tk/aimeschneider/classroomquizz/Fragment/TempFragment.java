package tk.aimeschneider.classroomquizz.Fragment;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import tk.aimeschneider.classroomquizz.ModelOnly.Controller;
import tk.aimeschneider.classroomquizz.ModelOnly.RetrieveDataTask;
import tk.aimeschneider.classroomquizz.ModelOnly.TemperatureSensorData;
import tk.aimeschneider.classroomquizz.R;

public class TempFragment extends Fragment {

    private static TextView tvTemp;
    private static TextView tvHumi;
    private static TextView tvPower;
    public TempFragment() {}
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_temperature, container, false);
            Toast.makeText(getContext(), "Temp", Toast.LENGTH_SHORT).show();
            tvTemp = (TextView) view.findViewById(R.id.tvSensorTemp);
            tvHumi = (TextView) view.findViewById(R.id.tvSensorHumi);
            tvPower = (TextView) view.findViewById(R.id.tvSensorPower);
            BluetoothManager bm =  ( BluetoothManager ) getActivity().getSystemService( Context. BLUETOOTH_SERVICE );
            BluetoothAdapter btAdapter =  bm.getAdapter();
            if  (btAdapter ==  null ) {
                Log.e (Controller.TAG,  "Bluetooth Adapter not  available !!!" );
            }
            if (!btAdapter.isEnabled ())
            {
                Intent i =  new Intent ( BluetoothAdapter . ACTION_REQUEST_ENABLE );
                startActivityForResult (i,  99 );
                new RetrieveDataTask(this.getActivity(), btAdapter).execute();
            }
            else
            {
                if (btAdapter.isEnabled())
                {// Bluetooth déjà activé
                    new RetrieveDataTask(this.getActivity(), btAdapter).execute();
                }
            }

            return view;
        }

        public static void setTemperature(String temp)
        {
            tvTemp.setText(temp);
        }
    public static void setHumidity(String humi)
    {
        tvHumi.setText(humi);
    }
    public static void setPower(String power)
    {
        tvPower.setText(power);
    }

}
