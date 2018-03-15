package com.pixo.birdwatching;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class ObservationListItemAdapter extends ArrayAdapter<Observation> {
	private final int resource;

	public ObservationListItemAdapter(Context context, int resource, List<Observation> objects) {
		super(context, resource, objects);
		this.resource = resource;
	}

	public ObservationListItemAdapter(Context context, int resource, Observation[] objects) {
		super(context, resource, objects);
		this.resource = resource;
	}

	@NonNull
	@Override
	public View getView(int position, View convertView, @NonNull ViewGroup parent) {
		Observation observation = getItem(position);
		String nameEnglish = observation.getNameEnglish();
		float latitude = observation.getLatitude();
		float longitude = observation.getLongitude();
		String placeName = observation.getPlacename();
		LinearLayout observationView;
		if (convertView == null) {
			observationView = new LinearLayout(getContext());
			String inflater = Context.LAYOUT_INFLATER_SERVICE;
			LayoutInflater li = (LayoutInflater) getContext().getSystemService(inflater);
			li.inflate(resource, observationView, true);
		} else {
			observationView = (LinearLayout) convertView;
		}
		TextView nameEnglishView = observationView.findViewById(R.id.observationlist_item_name_english);
		TextView placenameView = observationView.findViewById(R.id.observationlist_item_placename);
		nameEnglishView.setText(nameEnglish);
		placenameView.setText(placeName);

		return observationView;
	}
}
