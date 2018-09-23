//@author: Alan Jerry Pan, CPA, CSc
//@affiliation: Shanghai Jiaotong University
//
//Program to retrieve all available sensors and all its measurement values from Android devices.
//
//Suggested citation as computer software for reference:
//Pan, Alan J. (2018). Android Device Sensor and Values Retrieval [Computer software]. Github repository <https://github.com/alanjpan/Android-Device-Sensor-and-Values-Retrieval>
//
//Note this software's license is GNU GPLv3.
    
package alanpan.myapplication;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;


public class MainActivity extends Activity {
    private TextView text;
    private Button button;

    public SensorManager sensormanager;
    public Sensor sensor;
    public Sensor sensorM;
    public List<Sensor> sensorList;

    public Integer index = 0;
    public Integer maxindex = 0;

    public StringBuilder output = new StringBuilder();



    public SensorEventListener listener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent e) {
            float[] value = e.values;

            output.append("\n\n" + sensorList.get(index).getStringType() + "\n" + sensorList.get(index).getName() + ":  ");
            for (float v : value) {
                output.append(v + ", ");
            }
            sensormanager.unregisterListener(listener);

            text.setText(output);

            if (index <= maxindex) {
                index = index + 1;
                sensor = sensorList.get(index);
                sensorM = sensormanager.getDefaultSensor(sensor.getType());
                sensormanager.registerListener(listener, sensorM, SensorManager.SENSOR_DELAY_NORMAL);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = (TextView) findViewById(R.id.textView);
        text.setMovementMethod(new ScrollingMovementMethod());

        initializeSensors();
    }

    public void initializeSensors() {
        output.append("LIST OF SENSORS AND VALUES");

        sensormanager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorList = sensormanager.getSensorList(Sensor.TYPE_ALL);
        maxindex = sensorList.size();

        sensor = sensorList.get(index);
        sensorM = sensormanager.getDefaultSensor(sensor.getType());
        sensormanager.registerListener(listener, sensorM, SensorManager.SENSOR_DELAY_NORMAL);
    }

}
