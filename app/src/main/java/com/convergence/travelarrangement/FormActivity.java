package com.convergence.travelarrangement;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.convergence.travelarrangement.model.ProfileModel;
import com.convergence.travelarrangement.model.SubmitFormModel;
import com.nbsp.materialfilepicker.MaterialFilePicker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormActivity extends AppCompatActivity {
    private static final String RESULT_FILE_PATH = "4";
    RadioGroup radioGroup;
    RadioButton radioButton;
    TextView txtSelectticket;
    EditText edtName, edtNik, edtDivision, edtPhonenumber, edtEmail, edtTravelreason, edtFrom, edtTo, edtDate, edtDuration;
    ImageView ivUploadtransport, ivUploadhotel;
    Button btnSubmit,btnUploadTicket,btnUploadHotel;
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_TOKEN = "token";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_ROLE = "role";
    private static final String KEY_DIVISION = "division";
    private static final String KEY_NAME = "name";
    private static final String KEY_NIK = "nik";

    int files;
    private String mediaPath1;
    private String mediaPath2;
    private String postPath1;
    private String postPath2;
    private static final int REQUEST_PICK_PHOTO = Config.REQUEST_PICK_PHOTO;
    private static final int REQUEST_WRITE_PERMISSION = Config.REQUEST_WRITE_PERMISSION;


    String Token = "";
    String Urgent = "";
    DatePickerDialog.OnDateSetListener onDateSetListener;


    // Akses Izin Ambil Gambar dari Storage
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_WRITE_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            submitForm();
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBarform);
        ivUploadtransport = findViewById(R.id.ivUploadtransport);
        ivUploadhotel = findViewById(R.id.ivUploadhotel);

        edtName = findViewById(R.id.edtName);
        edtNik = findViewById(R.id.edtNik);
        edtDivision = findViewById(R.id.edtDivision);
        edtPhonenumber = findViewById(R.id.edtPhonenumber);
        edtEmail = findViewById(R.id.edtEmail);
        edtTravelreason = findViewById(R.id.edtTravelreason);
        edtFrom = findViewById(R.id.edtFrom);
        edtTo = findViewById(R.id.edtTo);
        edtDate = (EditText) findViewById(R.id.edtDate);
        edtDuration = findViewById(R.id.edtDuration);
        btnSubmit = findViewById(R.id.btnSubmit);
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        Token = sharedPreferences.getString(KEY_TOKEN,null);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back));
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        radioGroup = findViewById(R.id.rdoGroup);
        btnUploadTicket = findViewById(R.id.btnUploadTicket);
        btnUploadHotel = findViewById(R.id.btnUploadhotel);
        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        FormActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        onDateSetListener, year, month, day
                );
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.GRAY));
                datePickerDialog.show();
            }
        });

        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                month = month+1;
                String date = year+"-"+month+"-"+dayOfMonth;
                edtDate.setText(date);
            }
        };
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestPermission();
            }
        });
        btnUploadTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent albumIntent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                files=1;
                startActivityForResult(albumIntent, REQUEST_PICK_PHOTO);

            }
        });
        btnUploadHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent albumIntent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                files=2;
                startActivityForResult(albumIntent, REQUEST_PICK_PHOTO);
            }
        });
    }

    //Akses izin ambil gambar dari penyimpanan

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == REQUEST_PICK_PHOTO){
                if(data!=null){
                    if(files == 1){
                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};

                        Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                        assert cursor != null;
                        cursor.moveToFirst();

                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        mediaPath1 = cursor.getString(columnIndex);
                        ivUploadtransport.setImageURI(data.getData());
                        cursor.close();

                        postPath1 = mediaPath1;
                    }else{
                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};

                        Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                        assert cursor != null;
                        cursor.moveToFirst();

                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        mediaPath2 = cursor.getString(columnIndex);
                        ivUploadhotel.setImageURI(data.getData());
                        cursor.close();

                        postPath2 = mediaPath2;
                    }

                }
            }
        }
    }

    public void checkButton(View v){
        //Cara ambil data dari radiobutton
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
        Urgent = radioButton.getText().toString();
        Toast.makeText(this,"Level "+radioButton.getText().toString(), Toast.LENGTH_SHORT).show();

    }

    private void submitForm(){
        if (mediaPath1== null && mediaPath2== null)
        {
            Toast.makeText(getApplicationContext(), "Upload file ticket dan hotel terlebih  dahulu", Toast.LENGTH_LONG).show();
        }
        else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
            String currentDateandTime = sdf.format(new Date());

            File imagefile = new File(mediaPath1);
            //String namafile= imagefile.getName().replace("-","").replace("_","").replace(" ","");
            //resave file with new name
            File newFile = new File(edtName.getText().toString()+currentDateandTime+"transport"+".jpg");
            Log.d("TRANSPORT AFTER",""+newFile.getName());
            imagefile.renameTo(newFile);
            Log.d("TRANSPORT BEFORE",""+imagefile.getName());
            RequestBody reqBody = RequestBody.create(MediaType.parse("multipart/form-file"), imagefile);
            MultipartBody.Part partImage = MultipartBody.Part.createFormData("transport", newFile.getName(), reqBody);


            File imagefile2 = new File(mediaPath2);
            //String namafile2= imagefile2.getName().replace("-","").replace("_","").replace(" ","");
            File newFile2 = new File(edtName.getText().toString()+currentDateandTime+"hotel"+".jpg");
            Log.d("HOTEL AFTER",""+newFile2.getName());
            imagefile2.renameTo(newFile2);
            Log.d("HOTEL BEFORE",""+imagefile2.getName());
            RequestBody reqBody2 = RequestBody.create(MediaType.parse("multipart/form-file"), imagefile2);
            MultipartBody.Part partImage2 = MultipartBody.Part.createFormData("hotel", newFile2.getName(), reqBody2);


            ApiInterface apiInterface = ApiHelper.createService(ApiInterface.class, "Bearer " + Token);
            Call<SubmitFormModel> call = apiInterface.submitform(
                    partImage,
                    partImage2,
                    RequestBody.create(MediaType.parse("text/plain"),edtName.getText().toString()),
                    RequestBody.create(MediaType.parse("text/plain"),edtNik.getText().toString()),
                    RequestBody.create(MediaType.parse("text/plain"),edtDivision.getText().toString()),
                    RequestBody.create(MediaType.parse("text/plain"),edtPhonenumber.getText().toString()),
                    RequestBody.create(MediaType.parse("text/plain"),edtEmail.getText().toString()),
                    RequestBody.create(MediaType.parse("text/plain"),edtTravelreason.getText().toString()),
                    RequestBody.create(MediaType.parse("text/plain"),edtFrom.getText().toString()),
                    RequestBody.create(MediaType.parse("text/plain"),edtTo.getText().toString()),
                    RequestBody.create(MediaType.parse("text/plain"),edtDate.getText().toString()),
                    RequestBody.create(MediaType.parse("text/plain"),edtDuration.getText().toString()),
                    RequestBody.create(MediaType.parse("text/plain"),Urgent)
            );
            call.enqueue(new Callback<SubmitFormModel>() {
                @Override
                public void onResponse(Call<SubmitFormModel> call, Response<SubmitFormModel> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(FormActivity.this, "Sukses Submit", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(FormActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(FormActivity.this, "Gagal Submit, " + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<SubmitFormModel> call, Throwable t) {
                    Toast.makeText(FormActivity.this, "Gagal Submit, " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    // Cek Versi Android Tuk Minta Izin
    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
        } else {
            submitForm();
        }
    }
}
