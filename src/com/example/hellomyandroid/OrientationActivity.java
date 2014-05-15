package com.example.hellomyandroid;


import java.util.List;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
 
public class OrientationActivity extends Activity {

	SensorManager sensorManager;
	boolean accelerometerPresent;
	Sensor accelerometerSensor;
	  
	TextView textInfo, textX, textY, textZ;
	  
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		  super.onCreate(savedInstanceState);
	      setContentView(R.layout.activity_orientation);
	       
	      textInfo = (TextView)findViewById(R.id.info);
	      textX = (TextView)findViewById(R.id.textx);
	      textY = (TextView)findViewById(R.id.texty);
	      textZ = (TextView)findViewById(R.id.textz);
	       
	      sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE); 
	      List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ORIENTATION);
	       
	      if(sensorList.size() > 0){
	    	  accelerometerPresent = true;
	    	  accelerometerSensor = sensorList.get(0);
	         
	    	  String strSensor  = "Name: " + accelerometerSensor.getName()
	    			  + "\nVersion: " + String.valueOf(accelerometerSensor.getVersion()) 
	    			  + "\nVendor: " + accelerometerSensor.getVendor() 
	    			  + "\nType: " + String.valueOf(accelerometerSensor.getType()) 
	    			  + "\nMax: " + String.valueOf(accelerometerSensor.getMaximumRange()) 
	    			  + "\nResolution: " + String.valueOf(accelerometerSensor.getResolution()) 
	    			  + "\nPower: " + String.valueOf(accelerometerSensor.getPower()) 
	    			  + "\nClass: " + accelerometerSensor.getClass().toString();
	    	  textInfo.setText(strSensor);
	      }
	      else{
	        accelerometerPresent = false;
	      }
		Button mybutton = (Button)findViewById(R.id.mybutton2);
		mybutton.setOnClickListener(new mOnClickListener1());
	       
	}
	
	private class mOnClickListener1 implements OnClickListener{
		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			intent.setClass(OrientationActivity.this, MainActivity.class);
			startActivity(intent); 
			OrientationActivity.this.finish(); 
		}
	};
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(accelerometerPresent){
			sensorManager.registerListener(accelerometerListener, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
			Toast.makeText(this, "Register accelerometerListener", Toast.LENGTH_SHORT).show();
		}
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		if(accelerometerPresent){
			sensorManager.unregisterListener(accelerometerListener);
			Toast.makeText(this, "Unregister accelerometerListener", Toast.LENGTH_SHORT).show();
		}
	}
	
	private SensorEventListener accelerometerListener = new SensorEventListener(){
		@Override
		public void onAccuracyChanged(Sensor arg0, int arg1) {
			
		};
		@Override
		public void onSensorChanged(SensorEvent event) {
			textX.setText("X: " + String.valueOf(event.values[0]));
			textY.setText("Y: " + String.valueOf(event.values[1]));
			textZ.setText("Z: " + String.valueOf(event.values[2]));
		};
	};
	  
	
}
