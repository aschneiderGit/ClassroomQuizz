package tk.aimeschneider.classroomquizz.ModelOnly;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import tk.aimeschneider.classroomquizz.Fragment.TempFragment;
import tk.aimeschneider.classroomquizz.R;

import static tk.aimeschneider.classroomquizz.ModelOnly.Controller.TAG;

public class RetrieveDataTask extends AsyncTask<Void, Void, TemperatureSensorData> {

    public static String MAC_ADRESS = "F0:C7:7F:85:35:0C";
    Activity activity;
    ProgressDialog pDialog;
    BluetoothAdapter btAdapter;
    public
    RetrieveDataTask(Activity activity, BluetoothAdapter btAdapter)
    {
        this.activity = activity;
        this.btAdapter = btAdapter;
    }

    @Override protected void onPreExecute()
    {
        super.onPreExecute ();
        pDialog = new ProgressDialog(activity);
        pDialog.setMessage ( "Getting data from sensor..." );
        pDialog.setIndeterminate ( false );
        pDialog.show ();
    }

    @Override
    protected TemperatureSensorData doInBackground(Void... arg0){
        try {
            String sensorMacAddress = MAC_ADRESS;
            ISensorReader reader =  new GattSensorReader(activity,  btAdapter );
            String []  rawData =  reader.readRawData ( sensorMacAddress );
            TemperatureSensorData data =  TemperatureSensorData .parseData ( rawData ) ;
            return data;
        }
        catch ( RuntimeException e)
        {
            Log.e ( TAG ,  e.getMessage ());
            return null ;
        }
    }

    @Override
    protected void onPostExecute(TemperatureSensorData result) {
        if (result == null)
            return;
        else {
            TempFragment.setTemperature(Double.toString(result.getTemperature()));
            TempFragment.setHumidity(Double.toString(result.getHumidity()));
            TempFragment.setPower(Double.toString(result.getPower()));
            Toast.makeText(activity,Double.toString(result.getTemperature()), Toast.LENGTH_SHORT).show();
        }
        pDialog.dismiss();
    }
}
