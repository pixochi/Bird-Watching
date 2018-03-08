package com.pixo.birdwatching;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.pixo.birdwatching.AccountActivity.LoginActivity;
import com.pixo.birdwatching.AccountActivity.SignupActivity;

public class MainActivity extends AppCompatActivity {

	private Button signOutButton;
	private FirebaseAuth auth;
	private TextView email;
	private  FirebaseAuth.AuthStateListener authListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		email = findViewById(R.id.useremail);

		//firebase auth instance
		auth = FirebaseAuth.getInstance();

		//get current user
		final FirebaseUser user = auth.getCurrentUser();
		setDataToView(user);

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

		signOutButton = (Button) findViewById(R.id.sign_out);
		signOutButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				signOut();
			}
		});

	}

	private void setDataToView(FirebaseUser user) {
		email.setText("User Email: " + user.getEmail());
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
}
