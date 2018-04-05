package com.pixo.birdwatching;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class AddObservationActivity extends AppCompatActivity{

	TextView messageTextView;
	EditText latitudeEditText, longitudeEditText;
	Bird[] birds;
	double longitude;
	double latitude;

	String LOCATION_PROVIDER = LocationManager.GPS_PROVIDER;
	long MIN_TIME = 5000;
	float MIN_DISTANCE = 1000;
	int REQUEST_CODE = 1;
	LocationManager mLocationManager;
	LocationListener mLocationListener;

	@Override
	protected void onResume() {
		super.onResume();
		geCurrentLocation();
	}

	private void geCurrentLocation() {
		mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		mLocationListener = new LocationListener() {
			@Override
			public void onLocationChanged(Location location) {
				longitude = location.getLongitude();
				latitude = location.getLatitude();
				latitudeEditText.setText(latitude+"");
				longitudeEditText.setText(longitude+"");
			}

			@Override
			public void onStatusChanged(String s, int i, Bundle bundle) {

			}

			@Override
			public void onProviderEnabled(String s) {

			}

			@Override
			public void onProviderDisabled(String s) {

			}
		};

		if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
			return;
		}
		mLocationManager.requestLocationUpdates(LOCATION_PROVIDER, MIN_TIME, MIN_DISTANCE, mLocationListener);
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);

		if(requestCode == REQUEST_CODE){
			if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
				geCurrentLocation();
			} else {
				Log.d("LOCATION f", "Permission denied");
			}
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_observation);
		messageTextView = findViewById(R.id.network_message);
		latitudeEditText = findViewById(R.id.add_observation_latitude);
		longitudeEditText = findViewById(R.id.add_observation_longitude);

		AddObservationActivity.ReadTask task = new AddObservationActivity.ReadTask();
		task.execute("http://birdobservationservice.azurewebsites.net/Service1.svc/birds");

	}

	public void addObservation(View view){

	}

	private class ReadTask extends ReadHttpTask {
		@Override
		protected void onPostExecute(CharSequence jsonString) {

			Gson gson = new GsonBuilder().create();
			birds = gson.fromJson(jsonString.toString(), Bird[].class);

			Spinner dropdown = findViewById(R.id.bird_species);

			ArrayAdapter<String> birdSpeciesAdapter = new ArrayAdapter<String>(AddObservationActivity.this, R.layout.bird_spinner_item, getBirdNames(birds));
			dropdown.setAdapter(birdSpeciesAdapter);

		}

		@Override
		protected void onCancelled(CharSequence message) {
			messageTextView.setText(message);
			Log.e("network", message.toString());
		}
	}

	private String[] getBirdNames(Bird[] birds) {
		List<String> birdIds = new ArrayList<String>();

		for (Bird b:
			 birds) {
			birdIds.add(b.getNameEnglish());
		}

		String[] birdIdsArray = new String[ birdIds.size() ];
		birdIds.toArray( birdIdsArray );

		return birdIdsArray;
	}
}
