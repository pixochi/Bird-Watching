package com.pixo.birdwatching.AccountActivity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.pixo.birdwatching.MainActivity;
import com.pixo.birdwatching.R;

public class SignupActivity extends AppCompatActivity {

	private EditText inputEmail, inputPassword;
	private Button btnSignIn, btnSignUp;
	private ProgressBar progressBar;
	private FirebaseAuth auth;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);
		//Get Firebase auth instance
		auth = FirebaseAuth.getInstance();

		btnSignIn = (Button) findViewById(R.id.sign_in_button);
		btnSignUp = (Button) findViewById(R.id.sign_up_button);
		inputEmail = (EditText) findViewById(R.id.email);
		inputPassword = (EditText) findViewById(R.id.password);
		progressBar = (ProgressBar) findViewById(R.id.progressBar);

		btnSignIn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		btnSignUp.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				String email = inputEmail.getText().toString().trim();
				String password = inputPassword.getText().toString().trim();

				if (TextUtils.isEmpty(email)) {
					Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
					return;
				}

				if (TextUtils.isEmpty(password)) {
					Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
					return;
				}

				if (password.length() < 6) {
					Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
					return;
				}

				progressBar.setVisibility(View.VISIBLE);

				//create user
				auth.createUserWithEmailAndPassword(email, password)
				.addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
					@Override
					public void onComplete(@NonNull Task<AuthResult> task) {
						progressBar.setVisibility(View.GONE);
						if (!task.isSuccessful()) {
							Toast.makeText(SignupActivity.this, "Failed to sign up: " + task.getException(),
							Toast.LENGTH_SHORT).show();
						} else {
							startActivity(new Intent(SignupActivity.this, MainActivity.class));
							finish();
						}
					}
				});

			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		progressBar.setVisibility(View.GONE);
	}
}
