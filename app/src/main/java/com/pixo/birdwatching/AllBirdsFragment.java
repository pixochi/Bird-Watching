package com.pixo.birdwatching;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class AllBirdsFragment extends Fragment {

	ListView listView;
	TextView messageTextView;

	public AllBirdsFragment() {}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ReadTask task = new ReadTask();
		task.execute("http://birdobservationservice.azurewebsites.net/Service1.svc/observations");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.all_birds_fragment, container, false);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		listView = getView().findViewById(R.id.observations_listview);
		messageTextView = getView().findViewById(R.id.network_message);
	}

	private class ReadTask extends ReadHttpTask {
		@Override
		protected void onPostExecute(CharSequence jsonString) {

			Gson gson = new GsonBuilder().create();
			final Observation[] observations = gson.fromJson(jsonString.toString(), Observation[].class);

			ObservationListItemAdapter adapter = new ObservationListItemAdapter(getActivity().getBaseContext(), R.layout.observation_list_item, observations);
			listView.setAdapter(adapter);
		}

		@Override
		protected void onCancelled(CharSequence message) {
			messageTextView.setText(message);
			Log.e("network", message.toString());
		}
	}
}


