package com.cdc.cdcadmin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText emailText, passText;
    Button loginButton;

    ProgressBar progressBar;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onStart()
    {
        super.onStart();
        if(mAuth.getCurrentUser() != null)
        {
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailText = (EditText) findViewById(R.id.emailEditText);
        passText = (EditText) findViewById(R.id.passEditText);
        loginButton = (Button) findViewById(R.id.loginButton);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                adminLogin();

            }
        });
    }

    private void adminLogin()
    {
        String email, password;

        email = emailText.getText().toString();
        password = passText.getText().toString();

        progressBar.setVisibility(View.VISIBLE);

        if(email.isEmpty())
        {
            progressBar.setVisibility(View.INVISIBLE);
            emailText.setError("Email is required");
            emailText.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            progressBar.setVisibility(View.INVISIBLE);
            emailText.setError("Please enter a valid Email");
            emailText.requestFocus();
            return;
        }

        if(password.isEmpty())
        {
            progressBar.setVisibility(View.INVISIBLE);
            passText.setError("Password is required");
            passText.requestFocus();
            return;
        }

        if(password.length()<6)
        {
            progressBar.setVisibility(View.INVISIBLE);
            passText.setError("Enter the correct password");
            passText.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>()
        {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful())
                {
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });



    }























}
