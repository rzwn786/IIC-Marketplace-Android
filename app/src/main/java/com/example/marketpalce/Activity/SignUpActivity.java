package com.example.marketpalce.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.marketpalce.API.ApiRequestData;
import com.example.marketpalce.API.RetroServer;
import com.example.marketpalce.Model.ResponseModel;
import com.example.marketpalce.R;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private Button signUpLoginBtn,signupBtn;
    private EditText fname_field,lname_field,username_field,email_field,password_field,confirm_field,phone_field;
    private String fname,lname,username,email,password,confirmPassword,phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        navbar();
        setSignupBtn();


        signUpLoginBtn = findViewById(R.id.signupPageLogin_btn);

        signUpLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    public void navbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");
        toolbar.setSubtitle("");


        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.YELLOW));
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    public void setSignupBtn(){
        signupBtn = findViewById(R.id.signup_btn);
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fname_field = findViewById(R.id.fname_field);
                lname_field = findViewById(R.id.lname_field);
                username_field = findViewById(R.id.uname_field);
                email_field = findViewById(R.id.email_field);
                password_field = findViewById(R.id.password_field);
                confirm_field = findViewById(R.id.confirm_password_field);
                phone_field = findViewById(R.id.phone_field);


                fname = fname_field.getText().toString();
                lname = lname_field.getText().toString();
                username = username_field.getText().toString();
                email = email_field.getText().toString();
                password = password_field.getText().toString();
                confirmPassword = confirm_field.getText().toString();
                phone = phone_field.getText().toString();

                if(fname.trim().equals("")){
                    fname_field.setError("Required");
                }
                else if (lname.trim().equals("")){
                    lname_field.setError("Required");
                }
                else if (username.trim().equals("")){
                    username_field.setError("Required");
                }
                else if (email.trim().equals("")){
                    email_field.setError("Required");
                }
                else if (phone.trim().equals("")){
                    phone_field.setError("Required");
                }
                else if (password.trim().equals("")){
                    password_field.setError("Required");
                }
                else if (confirmPassword.trim().equals("")){
                    confirm_field.setError("Required");
                }
                else{
                    if(password.equals(confirmPassword)){
                        createUser();
                    }
                    else {
                        Toast.makeText(SignUpActivity.this, "Password Not Match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void createUser(){

        int PHONE = Integer.parseInt(phone);


        RequestBody fnameM = RequestBody.create(MediaType.parse("multipart/form-data"),fname);
        RequestBody lnameM = RequestBody.create(MediaType.parse("multipart/form-data"),lname);
        RequestBody userM = RequestBody.create(MediaType.parse("multipart/form-data"),username);
        RequestBody emailM = RequestBody.create(MediaType.parse("multipart/form-data"),email);
        RequestBody phoneM = RequestBody.create(MediaType.parse("multipart/form-data"),String.valueOf(PHONE));
        RequestBody passwordM = RequestBody.create(MediaType.parse("multipart/form-data"),password);

        ApiRequestData ardData = RetroServer.connectRetrofit().create(ApiRequestData.class);
        Call<ResponseModel> signUp = ardData.ardSignUpUser(fnameM,lnameM,userM,emailM,phoneM,passwordM);
        signUp.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                //int code = response.body().getCode();
                //String message = response.body().getMessage();
                Toast.makeText(SignUpActivity.this, "ok done", Toast.LENGTH_SHORT).show();


                //Toast.makeText(SignUpActivity.this, " Code : "+code+" message :"+message, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(SignUpActivity.this, "Failed to connect to server "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_home:
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_signUp:
                Intent s = new Intent(this,SignUpActivity.class);
                startActivity(s);
                break;

            case R.id.nav_logIn:
                Intent l = new Intent(this,LoginActivity.class);
                startActivity(l);
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
        super.onBackPressed();
    }
}