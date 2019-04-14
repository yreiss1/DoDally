package com.yuval.reiss.ourstory;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private EditText mUsernameEditText;
    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private Button mRegisterButton;
    private TextView mLoginLink;


    private FirebaseAuth mFirebaseAuth;
    private FirebaseDatabase mFirebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        mUsernameEditText = findViewById(R.id.register_username_edittext);
        mEmailEditText = findViewById(R.id.register_email_edittext);
        mPasswordEditText = findViewById(R.id.register_password_edittext);
        mRegisterButton = findViewById(R.id.register_button);
        mLoginLink = findViewById(R.id.register_textview);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();


        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String mUsername = mUsernameEditText.getText().toString();
                final String mEmail = mEmailEditText.getText().toString();
                final String mPassword = mPasswordEditText.getText().toString();

                if (mUsername.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please add a username!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!isEmailValid(mEmail)) {
                    Toast.makeText(RegisterActivity.this, "Please add a valid email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (mPassword.length() < 6) {
                    Toast.makeText(RegisterActivity.this, "Password must be longer than 6 characters", Toast.LENGTH_SHORT).show();
                    return;
                }


                mFirebaseAuth.createUserWithEmailAndPassword(mEmail, mPassword).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()) {

                            Map user = new HashMap<String, String>();
                            user.put("email", mEmail);
                            user.put("username", mUsername);
                            user.put("story", null);

                            String userID = mFirebaseAuth.getCurrentUser().getUid();
                            DatabaseReference mUserDB = FirebaseDatabase.getInstance().getReference().child("users").child(userID);
                            mUserDB.updateChildren(user);

                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);



                        } else {

                            Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });



        mLoginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                return;
            }
        });


    }





    private boolean isEmailValid(CharSequence email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

}
