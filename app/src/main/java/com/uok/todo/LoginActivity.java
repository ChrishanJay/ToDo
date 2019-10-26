package com.uok.todo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        final EditText emailTxt = findViewById(R.id.txtEmail);
        final EditText passwordTxt = findViewById(R.id.txtPassword);

        dialog = new ProgressDialog(this);
        dialog.setTitle("Logging In ....");

        findViewById(R.id.btnLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.show();

                String email = emailTxt.getText().toString();
                String password = passwordTxt.getText().toString();

                loginUser(email, password);
            }
        });

    }

    private void loginUser(final String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
                    homeIntent.putExtra("email", email);
                    startActivity(homeIntent);
                } else {
                    // Wrong Password
                }
            }
        });
    }
}
