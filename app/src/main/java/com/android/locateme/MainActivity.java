package com.android.locateme;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


    public class MainActivity extends AppCompatActivity implements SensorEventListener {

        private SensorManager mSensorManager;
        private Sensor accelerometer;
        TextView tv1, tv2, tv3, tv4;
        private Vector prev_acc = new Vector(0,0,0);
        private Vector acceleration = new Vector(0,0,0);
        private Vector distance = new Vector(0,0,0);
        public int moop=0;//if moop =1 then the person has travelled 100 metres.


        public class Vector{
            public double a;
            public double b;
            public double c;
            public Vector(double _a,double _b,double _c){
                a=_a;b=_b;c=_c;
            }
            public double magnitude(){
                return java.lang.Math.sqrt(a*a+b*b+c*c);
            }
            public Vector direction(){
                double mag=magnitude();
                Vector v = new Vector(a/mag,b/mag,c/mag);
                return v;
            }
            public Vector add(Vector v){
                return new Vector(java.lang.Math.abs(v.a)+java.lang.Math.abs(a),java.lang.Math.abs(v.b)+java.lang.Math.abs(b),java.lang.Math.abs(v.c)+java.lang.Math.abs(c));
            }
            public Vector multiply(double num){
                return new Vector(num*a, num*b, num*c);
            }
        }

        private float[] mAccelerometer = null;

        public void onAccuracyChanged(Sensor sensor, int accuracy){
        }

        @Override

        public void onSensorChanged(SensorEvent event) {
            // onSensorChanged gets called for each sensor so we have to remember the values
            if (event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {
                mAccelerometer = event.values;
            }
            acceleration = new Vector(mAccelerometer[0]-prev_acc.a,mAccelerometer[1]-prev_acc.b,mAccelerometer[2]-prev_acc.c);
            if(5*(distance.magnitude())<=10){
                distance = distance.add((acceleration.multiply(0.5)).multiply(0.06*0.06));
                moop=0;
            }
            else{
                distance = new Vector(0,0,0);
                moop=1;
                Intent intent = new Intent(this,FirstActivity.class);
                startActivity(intent);
            }

            prev_acc = new Vector(mAccelerometer[0],mAccelerometer[1],mAccelerometer[2]);
            tv4.setText("dist=" + distance.magnitude());
            tv1.setText("x_acc =   " + acceleration.a);
            tv2.setText("y_acc =   " + acceleration.b);
            tv3.setText("z_acc =   " + acceleration.c);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            Intent intent = new Intent(this,FirstActivity.class);
            startActivity(intent);
            mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
            accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
            tv1 = (TextView) findViewById(R.id.textView1);
            tv2 = (TextView) findViewById(R.id.textView2);
            tv3 = (TextView) findViewById(R.id.textView3);
            tv4 = (TextView) findViewById(R.id.textView4);
        }

        @Override
        protected void onResume() {
            super.onResume();
            mSensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
        }

        @Override
        protected void onPause() {
            super.onPause();
            mSensorManager.unregisterListener(this, accelerometer);

        }



        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();
            if (id == R.id.action_settings) {
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
    }



