package com.example.marketpalce.Activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.marketpalce.API.ApiRequestData;
import com.example.marketpalce.API.RetroServer;
import com.example.marketpalce.Adapter.FileUtils;
import com.example.marketpalce.Model.ResponseModel;
import com.example.marketpalce.R;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Multipart;

//Sell Page
public class Activity3 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private ImageView imageView;
    private EditText title_field,price_field,description_field,location_field;
    private Spinner categoriesField,conditionField;
    private RadioGroup  radio_group_cod,radio_group_pos;
    private RadioButton radioCod,radioPos;
    private Button submitBtn,chooseImage;
    private String title,price,categories_text,condition_text,description,location,cod,pos,selectedImage;
    private CharSequence[] options= {"Camera","Gallery","Cancel"};
    private CardView tap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);
        requiredPermission();
        navBar();
        getCategoriesValue();
        getConditionValue();
        submitBtnOnClick();


        imageView = findViewById(R.id.imgPreview);
        title_field = findViewById(R.id.title_field);
        price_field = findViewById(R.id.price_field);
        description_field = findViewById(R.id.description_field);
        location_field = findViewById(R.id.location_field);
        radio_group_cod = findViewById(R.id.radio_group_cod);
        radio_group_pos = findViewById(R.id.radio_group_pos);

        chooseImage = findViewById(R.id.chooseImg);
        tap = findViewById(R.id.tap_to_add);
        chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Activity3.this);
                builder.setTitle("Select Image");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(options[which].equals("Camera")){
                            Intent takePic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(takePic, 0);
                        }
                        else if(options[which].equals("Gallery")) {
                            Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(gallery, 1);
                        }
                        else {
                            dialog.dismiss();
                        }
                    }
                });

                builder.show();
            }
        });
    }
    public void requiredPermission(){
        ActivityCompat.requestPermissions(Activity3.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
    }

    public void navBar(){
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

    private String getCategoriesValue(){
        //Categories Spinner
        String value;
        final List<String>categories = Arrays.asList("VEHICLES","ELECTRONICS","HOME AND PERSONAL ITEMS","LEISURE/SPORTS/HOBBIES","JOBS AND SERVICES");
         categoriesField = findViewById(R.id.dropdown_menu_categories);

        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoriesField.setAdapter(adapter);
        value = categoriesField.getSelectedItem().toString();

        return value;
    }

    private String getConditionValue(){
        //Condition Spinner
        String value;
        final List<String>condition = Arrays.asList("NEW","USED");
        conditionField = findViewById(R.id.dropdown_menu_condition);

        ArrayAdapter adapter2 = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, condition);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        conditionField.setAdapter(adapter2);
        value = conditionField.getSelectedItem().toString();

        return value;
    }

    private String getCodValue(){
        String value;
        // get selected radio button from radioGroup
        int selectedCodOption = radio_group_cod.getCheckedRadioButtonId();
        // find the radiobutton by returned id
        radioCod = (RadioButton) findViewById(selectedCodOption);
        value = radioCod.getText().toString();

        return value;
    }

    private String getPostageValue(){
        String value;
        // get selected radio button from radioGroup
        int selectedPosOption = radio_group_pos.getCheckedRadioButtonId();
        // find the radiobutton by returned id
        radioPos = (RadioButton) findViewById(selectedPosOption);
        value = radioPos.getText().toString();

        return value;
    }

    private void submitBtnOnClick(){
        submitBtn = findViewById(R.id.submitListingBtn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title = title_field.getText().toString();
                price = price_field.getText().toString();
                categories_text = getCategoriesValue();
                condition_text = getConditionValue();
                description = description_field.getText().toString();
                location = location_field.getText().toString();
                pos = getPostageValue();
                cod = getCodValue();

                if(title.trim().equals("")){
                    title_field.setError("Required");
                }
                else if (price.trim().equals("")){
                    price_field.setError("Required");
                }
                else if (description.trim().equals("")){
                    description_field.setError("Required");
                }
                else if (location.trim().equals("")){
                    location_field.setError("Required");
                }
                else{
                    createdListing();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode !=RESULT_CANCELED){
            switch (requestCode){
                case 0:
                    if(resultCode == RESULT_OK && data !=null){
                        Bitmap image = (Bitmap) data.getExtras().get("data");
                        selectedImage = FileUtils.getPath(Activity3.this, getImageUri(Activity3.this,image));
                        imageView.setImageBitmap(image);
                    }
                    break;
                case 1:
                    if(resultCode == RESULT_OK && data !=null){

                        Uri image = data.getData();
                        selectedImage = FileUtils.getPath(Activity3.this,image);
                        Picasso.get().load(image).into(imageView);
                    }
            }
        }
    }

    public Uri getImageUri(Context context, Bitmap bitmap){
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "myImage","");

        return Uri.parse(path);
    }

    private void createdListing(){

        File file = new File(Uri.parse(selectedImage).getPath());
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),file);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("upload_file",file.getName(),requestBody);

        double PriceIndOUBLE = Double.parseDouble(price);

        RequestBody userid = RequestBody.create(MediaType.parse("multipart/form-data"),"6");
        RequestBody titleM = RequestBody.create(MediaType.parse("multipart/form-data"),title);
        RequestBody priceM = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(PriceIndOUBLE));
        RequestBody categoriesM = RequestBody.create(MediaType.parse("multipart/form-data"),categories_text);
        RequestBody conditionM = RequestBody.create(MediaType.parse("multipart/form-data"),condition_text);
        RequestBody descriptionM = RequestBody.create(MediaType.parse("multipart/form-data"),description);
        RequestBody locationM = RequestBody.create(MediaType.parse("multipart/form-data"),location);
        RequestBody codM = RequestBody.create(MediaType.parse("multipart/form-data"),cod);
        RequestBody posM = RequestBody.create(MediaType.parse("multipart/form-data"),pos);

        ApiRequestData ardData = RetroServer.connectRetrofit().create(ApiRequestData.class);
        Call<ResponseModel> addData = ardData.ardCreateData(userid,titleM,priceM,categoriesM,conditionM,descriptionM,locationM,codM,posM,filePart);
        addData.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                int code = response.body().getCode();
                String message = response.body().getMessage();

                Toast.makeText(Activity3.this, " Code : "+code+" message :"+message, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(Activity3.this, "Failed to connect to server "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}