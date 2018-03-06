package com.example.currentplacedetailsonmap;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.speech.tts.UtteranceProgressListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener{

    public TextToSpeech t1;
    MapsActivityCurrentPlace classnew;
    TextView data;
    String TAG="Team5";
    String[] Name;
    String[] Direct;
    // Gravity rotational data
    private float gravity[];
    // Magnetic rotational data
    private float magnetic[]; //for magnetic rotational data
    private float accels[] = new float[3];
    private float mags[] = new float[3];
    private float[] values = new float[3];

    // azimuth, pitch and roll
    private float azimuth;
    private float pitch;
    private float roll;
    SensorManager sManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sManager = (SensorManager) getApplicationContext().getSystemService(SENSOR_SERVICE);
        sManager.registerListener(mySensorEventListener, sManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL);
        sManager.registerListener(mySensorEventListener, sManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),SensorManager.SENSOR_DELAY_NORMAL);
        data = (TextView)findViewById(R.id.PlaceName);
        Bundle b =this.getIntent().getExtras();
        Name= b.getStringArray("Name");
        Direct= b.getStringArray("Direction");
        t1 = new TextToSpeech(this,this);
    }

    @Override
    public void onInit(int status) {
        if (status != TextToSpeech.ERROR) {
            t1.setLanguage(Locale.US);
            Log.d(TAG, "Successfully initialized speech engine");
            //t1.speak("Wake up Wake up", TextToSpeech.QUEUE_FLUSH, null,null);
            t1.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                @Override
                public void onDone(String utteranceId) {
                    Log.d(TAG, "TTS finished");
                }

                @Override
                public void onError(String utteranceId) {
                }

                @Override
                public void onStart(String utteranceId) {
                }
            });
        } else{
            Log.d(TAG, " Speech engine setup failed " + status);
        }
    }

    private SensorEventListener mySensorEventListener = new SensorEventListener() {
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        public void onSensorChanged(SensorEvent event) {
            switch (event.sensor.getType()) {
                case Sensor.TYPE_MAGNETIC_FIELD:
                    mags = event.values.clone();
                    break;
                case Sensor.TYPE_ACCELEROMETER:
                    accels = event.values.clone();
                    break;
            }

            if (mags != null && accels != null) {
                gravity = new float[9];
                magnetic = new float[9];
                SensorManager.getRotationMatrix(gravity, magnetic, accels, mags);
                float[] outGravity = new float[9];
                SensorManager.remapCoordinateSystem(gravity, SensorManager.AXIS_X,SensorManager.AXIS_Z, outGravity);
                SensorManager.getOrientation(outGravity, values);

                azimuth = values[0] * 57.2957795f;
                pitch =values[1] * 57.2957795f;
                roll = values[2] * 57.2957795f;
                mags = null;
                accels = null;

                if(((40.0<=azimuth)&&(azimuth<=60.0)))
                    {
                        data.setText(Name[1]);

                    }else if(((-110.0<=azimuth)&&(azimuth<=-60.0))  )
                {
                    data.setText(Name[2]);
                   // t1.speak(Name[2] + "is infront of you", TextToSpeech.QUEUE_FLUSH, null,null);
                }else if(((-35<=azimuth)&&(azimuth<=-10.0))  )
                {
                    data.setText(Name[3]);
                   // t1.speak(Name[3] + "is infront of you", TextToSpeech.QUEUE_FLUSH, null,null);
                }else if(((150<=azimuth)&&(azimuth<=170))  )
                {
                    data.setText(Name[4]);
                   // t1.speak(Name[4] + "is infront of you", TextToSpeech.QUEUE_FLUSH, null,null);
                }else {
                    data.setText("Loading");
                }
            }
        }
    };

    @Override
    public void onBackPressed() {
        Intent Intentnew = new Intent(this,MapsActivityCurrentPlace.class);
        startActivity(Intentnew);
        super.onBackPressed();
    }
}
