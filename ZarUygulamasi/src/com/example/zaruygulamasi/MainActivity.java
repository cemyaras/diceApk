package com.example.zaruygulamasi;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;

public class MainActivity extends Activity implements SensorEventListener {
	private SensorManager senSensorManager;
	private Sensor senAccelerometer;
	private long lastUpdate = 0;
	private float last_x, last_y, last_z;
	private static final int SHAKE_THRESHOLD = 900;
	ImageView img1, img2;
	TextView txt1, txt2;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		img1 = (ImageView) findViewById(R.id.imageView1);
		img2 = (ImageView) findViewById(R.id.imageView2);
		txt1 = (TextView) findViewById(R.id.textView1);
		txt2 = (TextView) findViewById(R.id.TextView01);
		senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		senAccelerometer = senSensorManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		senSensorManager.registerListener(this, senAccelerometer,
				SensorManager.SENSOR_DELAY_UI);

	}

	protected void onPause() {
		super.onPause();
		senSensorManager.unregisterListener(this);
	}

	// Sensor delay'i normal modda çalýþtýrýyoruz!
	protected void onResume() {
		super.onResume();
		senSensorManager.registerListener(this, senAccelerometer,
				SensorManager.SENSOR_DELAY_UI);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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

	private void diceSelecter(int i, ImageView img) {
		switch (i) {
		case 1:
			img.setImageResource(R.drawable.zar1);
			break;
		case 2:
			img.setImageResource(R.drawable.zar2);
			break;
		case 3:
			img.setImageResource(R.drawable.zar3);
			break;
		case 4:
			img.setImageResource(R.drawable.zar4);
			break;
		case 5:
			img.setImageResource(R.drawable.zar5);
			break;
		case 6:
			img.setImageResource(R.drawable.zar6);
			break;

		}
	}

	private void getRandomNumber() {
		Random randNumber = new Random(); // burada random fonksiyonunu biraz
											// daha random yapalým
		int i1 = randNumber.nextInt(6) + 1;
		randNumber = new Random();
		int i2 = randNumber.nextInt(6) + 1;

		Animation a = AnimationUtils.loadAnimation(this,
				R.anim.move_down_ball_first);
		Animation b = AnimationUtils.loadAnimation(this,
				R.anim.move_down_ball_second);
		diceSelecter(i1, img1);
		img1.clearAnimation();
		txt1.setText("");
		img1.startAnimation(a);
		// txt1.setText(Integer.toString(i1));

		diceSelecter(i2, img2);
		img2.clearAnimation();
		txt2.setText("");
		img2.startAnimation(b);
		// txt2.setText(Integer.toString(i2));

	}

	@Override
	public void onSensorChanged(SensorEvent sensorEvent) {
		// TODO Auto-generated method stub
		// Toast.makeText(getApplicationContext(), "salladýn beni aga",
		// Toast.LENGTH_SHORT).show();
		Sensor mySensor = sensorEvent.sensor;

		if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
			float x = sensorEvent.values[0];
			float y = sensorEvent.values[1];
			// float z = sensorEvent.values[2];

			long curTime = System.currentTimeMillis();

			if ((curTime - lastUpdate) > 100) {
				long diffTime = (curTime - lastUpdate);
				lastUpdate = curTime;

				float speed = Math.abs(x + y + -last_x - last_y) / diffTime
						* 10000;

				if (speed > SHAKE_THRESHOLD) {
					getRandomNumber();
				}

				last_x = x;
				last_y = y;
				// last_z = z;
			}
		}

	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

	}

}
