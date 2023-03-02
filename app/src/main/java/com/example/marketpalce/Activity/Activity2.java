package com.example.marketpalce.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.marketpalce.API.ApiRequestData;
import com.example.marketpalce.API.RetroServer;
import com.example.marketpalce.Adapter.AdapterData;
import com.example.marketpalce.Adapter.RecyclerViewInterface;
import com.example.marketpalce.Model.DataModel;
import com.example.marketpalce.Model.ResponseModel;
import com.example.marketpalce.R;
import com.google.android.material.navigation.NavigationView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//Find Page
public class Activity2 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, RecyclerViewInterface {
    private DrawerLayout drawer;

    private RecyclerView recyclerViewData;
    private RecyclerView.Adapter adapterViewData;
    private RecyclerView.LayoutManager layoutData;
    private List<DataModel> itemListing = new ArrayList<>();
    private SwipeRefreshLayout srlData;
    private ProgressBar pbData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

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

        recyclerViewData = findViewById(R.id.rv_listing);
        srlData = findViewById(R.id.srl_data);
        pbData = findViewById(R.id.pb_data);

        layoutData = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerViewData.setLayoutManager(layoutData);
        //retrieveData();

        srlData.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srlData.setRefreshing(true);
                retrieveData();
                srlData.setRefreshing(false);

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        retrieveData();
    }

    public void retrieveData(){
        pbData.setVisibility(View.VISIBLE);

        ApiRequestData ardData = RetroServer.connectRetrofit().create(ApiRequestData.class);
        Call<ResponseModel> showData = ardData.ardRetrieveData();

        showData.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                int code = response.body().getCode();
                String message = response.body().getMessage();
                itemListing = response.body().getData();
                adapterViewData = new AdapterData(Activity2.this,itemListing,Activity2.this);
                recyclerViewData.setAdapter(adapterViewData);
                adapterViewData.notifyDataSetChanged();
                pbData.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(Activity2.this, "Failed to connect to Server"+t.getMessage(), Toast.LENGTH_SHORT).show();
                pbData.setVisibility(View.INVISIBLE);
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

    @Override
    public void OnItemClick(int position) {
        Intent intent = new Intent(Activity2.this,Detail.class);

        intent.putExtra("LISTINGID",itemListing.get(position).getListingid());
        intent.putExtra("DATE",itemListing.get(position).getCreated_date());
        intent.putExtra("TITLE",itemListing.get(position).getItem_title());
        intent.putExtra("PRICE",itemListing.get(position).getItem_price());
        intent.putExtra("LOCATION",itemListing.get(position).getItem_location());
        intent.putExtra("CONDITION",itemListing.get(position).getItem_condition());
        intent.putExtra("POSTAGE",itemListing.get(position).getItem_postage());
        intent.putExtra("COD",itemListing.get(position).getItem_cod());
        intent.putExtra("DESCRIPTION",itemListing.get(position).getItem_description());
        intent.putExtra("IMAGE",itemListing.get(position).getItem_image());

        startActivity(intent);

    }
}