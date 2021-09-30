package com.hussam.matricball;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hussam.matricball.Model.Users;
import com.hussam.matricball.Prevalent.Prevalent;
import com.hussam.matricball.signinorup.MainActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import io.paperdb.Paper;

public class MainActivityNew extends AppCompatActivity {

    private Button joinNowButton, loginButton,joinNowServiceButton;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_new);

        joinNowButton = findViewById(R.id.main_join_now_btn);
        joinNowServiceButton = findViewById(R.id.main_join_now_service_btn);
        loginButton = findViewById(R.id.main_login_btn);
        loadingBar = new ProgressDialog(this);


        Paper.init(this);


       /* loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivityNew.this, LoginActivity.class);

                startActivity(intent);
            }
        });
*/
        joinNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivityNew.this, MainActivity.class);

                startActivity(intent);
            }
        });
        joinNowServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivityNew.this, AdminActivity.class);

                startActivity(intent);
            }
        });

        String UserPhoneKey = Paper.book().read(Prevalent.UserPhoneKey);
        String UserPasswordKey = Paper.book().read(Prevalent.UserPasswordKey);


        if (UserPhoneKey != "" && UserPasswordKey != "") {

            if (!TextUtils.isEmpty(UserPhoneKey) && !TextUtils.isEmpty(UserPasswordKey)) {

                AllowAccess(UserPhoneKey, UserPasswordKey);

                loadingBar.setTitle("Already Logged in");
                loadingBar.setMessage("Please wait ...");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();
            }
        }

    }

    private void AllowAccess(final String phone, final String password) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();


        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.child("Users").child(phone).exists()) {

                    Users usersData = dataSnapshot.child("Users").child(phone).getValue(Users.class);

                    if (usersData.getPhone().equals(phone)) {
                        if (usersData.getPassword().equals(password)) {

                            Toast.makeText(MainActivityNew.this, "You are already logged in...", Toast.LENGTH_SHORT).show();

                            loadingBar.dismiss();
/*
                            Intent intent = new Intent(MainActivityNew.this, HomeActivity.class);

                            //for remember the user when close the app
                            Prevalent.currentOnlineUsers = usersData;


                            startActivity(intent);*/
                        } else {
                            loadingBar.dismiss();
                            Toast.makeText(MainActivityNew.this, "Password is incorrect", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {

                    Toast.makeText(MainActivityNew.this, "Account with this " + phone + "number do not exists", Toast.LENGTH_SHORT).show();

                    loadingBar.dismiss();

                    //Toast.makeText(LoginActivity.this, "you need to create a new account", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
