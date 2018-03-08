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

public class LoginActivity extends AppCompatActivity {

	private EditText inputEmail, inputPassword;
	private FirebaseAuth auth;
	private ProgressBar progressBar;
	private Button btnSignup, btnLogin;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		//Get Firebase auth instance
		auth = FirebaseAuth.getInstance();

		if (auth.getCurrentUser() != null) {
			startActivity(new Intent(LoginActivity.this, MainActivity.class));
			finish();
		}

		inputEmail = (EditText) findViewById(R.id.email);
		inputPassword = (EditText) findViewById(R.id.password);
		progressBar = (ProgressBar) findViewById(R.id.progressBar);
		btnLogin = (Button) findViewById(R.id.btn_login);

		btnSignup = (Button) findViewById(R.id.btn_signup);
		btnSignup.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(LoginActivity.this, SignupActivity.class));
			}
		});

		btnLogin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String email = inputEmail.getText().toString();
				final String password = inputPassword.getText().toString();

				if (TextUtils.isEmpty(email)) {
					Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
					return;
				}

				if (TextUtils.isEmpty(password)) {
					Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
					return;
				}

				progressBar.setVisibility(View.VISIBLE);

				// authenticate user
				auth.signInWithEmailAndPassword(email, password)
				.addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
					@Override
					public void onComplete(@NonNull Task<AuthResult> task) {
						progressBar.setVisibility(View.GONE);
						if (!task.isSuccessful()) {
							// failed to log in
							Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
						} else {
							Intent intent = new Intent(LoginActivity.this, MainActivity.class);
							startActivity(intent);
							finish();
						}
					}
				});
			}
		});
	}
}
