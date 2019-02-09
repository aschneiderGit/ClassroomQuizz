package tk.aimeschneider.classroomquizz.ModelOnly;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;

public class GattSensorReader implements ISensorReader {

    private BluetoothAdapter bluetoothAdapter;
    private Context context;
    private String[] data = new String[10];
    private final Object synchObj = new Object();

    public GattSensorReader(Context context, BluetoothAdapter bluetoothAdapter) {
        this.bluetoothAdapter = bluetoothAdapter;
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public String[] readRawData(String macAddress) {

        if (macAddress.isEmpty()) {
            throw new RuntimeException("Mac address missing.");
        }
        BluetoothDevice bluetoothDevice = bluetoothAdapter.getRemoteDevice(macAddress);
        bluetoothDevice.connectGatt(context, false, new BeeWiSmartBtCallback(data, synchObj));
        synchronized (synchObj) {
            try {
                // wait max 15 seconds for the callback to retrieve the data from the sensor
                synchObj.wait(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (data[0] == null) {
            throw new RuntimeException("Failed reading data.");
        }

        return data;
    }

}
