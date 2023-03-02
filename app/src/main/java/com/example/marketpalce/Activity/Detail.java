package com.example.marketpalce.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.marketpalce.R;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class Detail extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

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




        int listingId = getIntent().getIntExtra("LISTINGID",0);
        String date = getIntent().getStringExtra("DATE");
        String title = getIntent().getStringExtra("TITLE");
        double price = getIntent().getDoubleExtra("PRICE",0.00);
        String location = getIntent().getStringExtra("LOCATION");
        String condition = getIntent().getStringExtra("CONDITION");
        String postage = getIntent().getStringExtra("POSTAGE");
        String cod = getIntent().getStringExtra("COD");
        String image = getIntent().getStringExtra("IMAGE");
        String description = getIntent().getStringExtra("DESCRIPTION");

        TextView listIdTextView = findViewById(R.id.listing_id_text);
        TextView titleTextView = findViewById(R.id.title_text);
        TextView priceTextView = findViewById(R.id.price_text);
        TextView locationTextView = findViewById(R.id.location_text);
        TextView descriptionTextView = findViewById(R.id.description_text);
        TextView conditionTextView = findViewById(R.id.condition_text);
        TextView postageTextView = findViewById(R.id.postage_text);
        TextView dateTextView = findViewById(R.id.created_date_text);
        TextView codTextView = findViewById(R.id.cash_on_delivery_text);
        ImageView imageItem = findViewById(R.id.image_item);

        descriptionTextView.setMovementMethod(new ScrollingMovementMethod());




        Glide.with(this).load("http://192.168.0.108/iicmarketplace/uploads/"+image).into(imageItem);
        listIdTextView.setText(String.valueOf(listingId));
        titleTextView.setText(title);
        priceTextView.setText(String.valueOf(price));
        locationTextView.setText(location);
        descriptionTextView.setText(description);
        conditionTextView.setText(condition);
        postageTextView.setText(postage);
        dateTextView.setText(date);
        codTextView.setText(cod);
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