package com.pixo.birdwatching;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class AddObservationActivity extends AppCompatActivity {

	TextView messageTextView;
	Bird[] birds;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_observation);
		messageTextView = findViewById(R.id.network_message);

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
