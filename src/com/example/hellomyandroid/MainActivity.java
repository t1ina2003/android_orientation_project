package com.example.hellomyandroid;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity implements SensorEventListener {
	
	private TextView text_x;
	private TextView text_y;
	private TextView text_z;
	private SensorManager aSensorManager;
	private Sensor aSensor;
	private float gravity[] = new float[3];
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
	
		text_x = (TextView)findViewById(R.id.mytextview_x);
		text_y = (TextView)findViewById(R.id.mytextview_y);
		text_z = (TextView)findViewById(R.id.mytextview_z);
		Button mybutton = (Button)findViewById(R.id.mybutton);
		mybutton.setOnClickListener(new mOnClickListener1());
		
		
		aSensorManager=(SensorManager) getSystemService(SENSOR_SERVICE);
		aSensor = aSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		aSensorManager.registerListener(this, aSensor, aSensorManager.SENSOR_DELAY_NORMAL);
		
    }
	
	private class mOnClickListener1 implements OnClickListener{
		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			intent.setClass(MainActivity.this, OrientationActivity.class);
			startActivity(intent); 
			MainActivity.this.finish(); 
		}
	};

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		gravity[0] = (float)Math.round(event.values[0]*100)/100;
		gravity[1] = (float)Math.round(event.values[1]*100)/100;
		gravity[2] = (float)Math.round(event.values[2]*100)/100;
		text_x.setText("X = "+gravity[0]);
		text_y.setText("Y = "+gravity[1]);
		text_z.setText("Z = "+gravity[2]); 
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		/* ¨ú®øµù¥USensorEventListener */
		aSensorManager.unregisterListener(this);
		Toast.makeText(this, "Unregister accelerometerListener", Toast.LENGTH_SHORT).show();
		
		super.onPause();
	}
	
	
}
