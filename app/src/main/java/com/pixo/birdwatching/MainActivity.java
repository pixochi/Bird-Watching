package com.pixo.birdwatching;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.pixo.birdwatching.AccountActivity.LoginActivity;

public class MainActivity extends AppCompatActivity {

	private FirebaseAuth auth;
	private TextView email;
	private  FirebaseAuth.AuthStateListener authListener;
	private static FirebaseUser user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// ****************************
		// TAB BAR
		// ****************************
		ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
		ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

		// Add Fragments to adapter
		adapter.addFragment(new MyBirdsFragment(), "My Birds");
		adapter.addFragment(new AllBirdsFragment(), "All Birds");
		viewPager.setAdapter(adapter);

		TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
		tabLayout.setupWithViewPager(viewPager);

		// ****************************
		// AUTHENTICATION
		// ****************************

		//firebase auth instance
		auth = FirebaseAuth.getInstance();

		//get current user
		user = auth.getCurrentUser();

		authListener = new FirebaseAuth.AuthStateListener() {
			@Override
			public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
			FirebaseUser user = firebaseAuth.getCurrentUser();
			if (user == null) {
				// user logged out -> launch login activity
				startActivity(new Intent(MainActivity.this, LoginActivity.class));
				finish();
			}
			}
		};
	}

	public void signOut() {
		auth.signOut();
	}

	@Override
	public void onStart() {
		super.onStart();
		auth.addAuthStateListener(authListener);
	}

	@Override
	public void onStop() {
		super.onStop();
		if (authListener != null) {
			auth.removeAuthStateListener(authListener);
		}
	}

	public static FirebaseUser getCurrentUser(){
		return user;
	}

//	ACTION BAR OPTIONS
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
			case R.id.action_signout: {
				signOut();
				break;
			}
			case R.id.action_addObservation: {
				goToAddObservation();
				break;
			}
		}
		return super.onOptionsItemSelected(item);
	}

	public void goToAddObservation() {
//		Intent intent = new Intent(this, AddObservationActivity.class);
//		startActivity(intent);
		Log.d("navigation","go to addObservation clicked");
	}
}
