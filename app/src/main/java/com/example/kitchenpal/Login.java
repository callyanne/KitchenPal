package com.example.kitchenpal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;

public class Login extends AppCompatActivity {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private TextView signup;
    public Button loginBtn;
    public EditText etEmail, etPassword;

    public void setmAuth (FirebaseAuth auth) {
        this.mAuth = auth;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signup = findViewById(R.id.signUpLink);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToSignUp();
            }
        });

        loginBtn = findViewById(R.id.loginButton);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etEmail = findViewById(R.id.loginEmail);
                etPassword = findViewById(R.id.loginPassword);

                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if(email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(Login.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    checkExistingUserAndLogin(email, password);
                }
            }
        });
    }

    private void switchToSignUp() {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
        finish();
    }
    private void checkExistingUserAndLogin(String email, String password) {
        mAuth.fetchSignInMethodsForEmail(email)
                .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {

                        boolean isNewUser = task.getResult().getSignInMethods().isEmpty();

                        if (isNewUser) {
                            Toast.makeText(Login.this, "Couldn't find your account, please sign up!", Toast.LENGTH_SHORT).show();
                        } else {
                            authenticateUser(email, password);
                        }
                    }
                });
    }

    public void authenticateUser(String loginEmail, String loginPw) {
        if (loginEmail.isEmpty() || loginPw.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        }

        mAuth.signInWithEmailAndPassword(loginEmail, loginPw)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            switchToHome();
                        } else {
                            Toast.makeText(Login.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void switchToHome() {
        Intent intent = new Intent(this, HomePage.class);
        startActivity(intent);
        finish();
    }
}