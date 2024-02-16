package com.example.sensor_samsung;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager; //менеджер сенсоров

    private float[] rotationMatrix; //матрица поворота

    private float[] accelerometer;  //данные с акселерометра
    private float[] geomagnetism;   //данные геомагнитного датчика
    private float[] orientation;    //матрица положения в пространстве

    private TextView xyAngle;
    private TextView xzAngle;
    private TextView zyAngle;
    Draw draw;
    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        draw = new Draw(this);
        setContentView(draw);
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);

        rotationMatrix = new float[16];
        accelerometer = new float[3];
        geomagnetism = new float[3];
        orientation = new float[3];

        // поля для вывода показаний
//        xyAngle = findViewById(R.id.xyValue);
//        xzAngle= findViewById(R.id.xzValue);
//        zyAngle = findViewById(R.id.zyValue);

//        setContentView(R.layout.activity_main);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        loadSensorData(event); // получаем данные с датчика
        SensorManager.getRotationMatrix(rotationMatrix, null, accelerometer, geomagnetism); //получаем матрицу поворота
        SensorManager.getOrientation(rotationMatrix, orientation); //получаем данные ориентации устройства в пространстве

//        if((xyAngle ==null) || (xzAngle==null) || (zyAngle ==null)){
//            xyAngle = findViewById(R.id.xyValue);
//            xzAngle = findViewById(R.id.xzValue);
//            zyAngle = findViewById(R.id.zyValue);
//        }
        try {
            Thread.sleep(0);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        draw.update((float) Math.toDegrees(orientation[1]), (float) Math.toDegrees(orientation[2]));

//        xyAngle.setText(String.valueOf(Math.round(Math.toDegrees(orientation[0]))));
//        xzAngle.setText(String.valueOf(Math.round(Math.toDegrees(orientation[1]))));
//        zyAngle.setText(String.valueOf(Math.round(Math.toDegrees(orientation[2]))));

    }



    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //используется для получения уведомлений от SensorManager при изменении значений датчика
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_UI );
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_UI );
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    private void loadSensorData(SensorEvent event) {
        final int type = event.sensor.getType(); //определяем тип датчика
        if (type == Sensor.TYPE_ACCELEROMETER) { //если акселерометр
            accelerometer = event.values.clone();
        }
        if (type == Sensor.TYPE_MAGNETIC_FIELD) { //если геомагнитный датчик
            geomagnetism = event.values.clone();
        }
    }
}