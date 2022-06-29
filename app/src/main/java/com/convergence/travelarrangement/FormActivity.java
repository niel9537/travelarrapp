package com.convergence.travelarrangement;

import android.app.Activity;
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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
import androidx.appcompat.app.AppCompatDelegate;
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

    LoadingDialogBar loadingDialogBar;
    RadioGroup radioGroup;
    RadioButton radioButton;
    TextView txtSelectticket, txtMakskeretaapi, txtMakspesawat, txtMakshotel;
    EditText edtName, edtNik, edtPhonenumber, edtEmail, edtTravelreason, edtFrom, edtTo, edtDate, edtDuration;
    AutoCompleteTextView edtDivision, edtTitle, edtUrgent;
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
    private static final String KEY_TITLE = "title";
    private static final String KEY_SUCCESS = "success";
    int files;
    private String mediaPath1;
    private String mediaPath2;
    private String postPath1;
    private String postPath2;
    private static final int REQUEST_PICK_PHOTO = Config.REQUEST_PICK_PHOTO;
    private static final int REQUEST_WRITE_PERMISSION = Config.REQUEST_WRITE_PERMISSION;
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> adapterDiv;
    ArrayAdapter<String> adapterUrgent;
    String currentDateandTime = "";
    String Token = "";
    String Urgent = "";
    String Role = "";
    String Name = "";
    String Nik = "";
    String Division = "";
    String Titles = "";
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
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_form);
        loadingDialogBar = new LoadingDialogBar(this);
        setupUI(findViewById(R.id.formpage));
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBarform);
        ivUploadtransport = findViewById(R.id.ivUploadtransport);
        ivUploadhotel = findViewById(R.id.ivUploadhotel);
        txtMakskeretaapi = findViewById(R.id.txtMakskeretaapi);
        txtMakshotel = findViewById(R.id.txtMakshotel);
        txtMakspesawat = findViewById(R.id.txtMakspesawat);
        edtName = findViewById(R.id.edtName);
        edtNik = findViewById(R.id.edtNik);
        edtDivision = findViewById(R.id.edtDivision);
        edtTitle = findViewById(R.id.edtTitle);
        edtPhonenumber = findViewById(R.id.edtPhonenumber);
        edtEmail = findViewById(R.id.edtEmail);
        edtTravelreason = findViewById(R.id.edtTravelreason);
        edtFrom = findViewById(R.id.edtFrom);
        edtTo = findViewById(R.id.edtTo);
        edtDate = (EditText) findViewById(R.id.edtDate);
        edtDuration = findViewById(R.id.edtDuration);
        edtUrgent = findViewById(R.id.edtUrgent);
        btnSubmit = findViewById(R.id.btnSubmit);
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        Token = sharedPreferences.getString(KEY_TOKEN,null);
        Role = sharedPreferences.getString(KEY_ROLE,null);
        Division = sharedPreferences.getString(KEY_DIVISION,null);
        Name = sharedPreferences.getString(KEY_NAME,null);
        Nik = sharedPreferences.getString(KEY_NIK,null);
        Titles = sharedPreferences.getString(KEY_TITLE,null);
        Log.d("Role ",""+Role);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back));
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        btnUploadTicket = findViewById(R.id.btnUploadTicket);
        btnUploadHotel = findViewById(R.id.btnUploadhotel);
        if(Titles.equals("Staff")){
            txtMakspesawat.setText("- Maks biaya pesawat sebesar Rp. 500.000 (LION AIR)");
            txtMakskeretaapi.setText("- Maks biaya kereta api sebesar Rp. 500.000 (BISNIS)");
            txtMakshotel.setText("- Maks biaya hotel sebesar Rp. 400.000 (BINTANG 3)");
        }else if(Titles.equals("MANAGER/BD")){
            txtMakspesawat.setText("- Maks biaya pesawat sebesar Rp. 750.000 (CITILINK)");
            txtMakskeretaapi.setText("- Maks biaya kereta api sebesar Rp. 750.000 (EKSEKUTIF)");
            txtMakshotel.setText("- Maks biaya hotel sebesar Rp. 500.000 (BINTANG 3)");
        }else if(Titles.equals("CTO")){
            txtMakspesawat.setText("- Maks biaya pesawat sebesar Rp. 1.500.000 (GARUDA)");
            txtMakskeretaapi.setText("- Maks biaya kereta api sebesar Rp. 1.000.000 (LUXURY)");
            txtMakshotel.setText("- Maks biaya hotel sebesar Rp. 1.000.000 (BINTANG 4)");
        }else if(Titles.equals("COO")){
            txtMakspesawat.setText("- Maks biaya pesawat sebesar Rp. 1.500.000 (GARUDA)");
            txtMakskeretaapi.setText("- Maks biaya kereta api sebesar Rp. 1.000.000 (LUXURY)");
            txtMakshotel.setText("- Maks biaya hotel sebesar Rp. 1.000.000 (BINTANG 4)");
        }else if(Titles.equals("CPO")){
            txtMakspesawat.setText("- Maks biaya pesawat sebesar Rp. 1.500.000 (GARUDA)");
            txtMakskeretaapi.setText("- Maks biaya kereta api sebesar Rp. 1.000.000 (LUXURY)");
            txtMakshotel.setText("- Maks biaya hotel sebesar Rp. 1.000.000 (BINTANG 4)");
        }else if(Titles.equals("CEO")){
            txtMakspesawat.setText("- Maks biaya pesawat sebesar Rp. 1.500.000 (GARUDA)");
            txtMakskeretaapi.setText("- Maks biaya kereta api sebesar Rp. 1.000.000 (LUXURY)");
            txtMakshotel.setText("- Maks biaya hotel sebesar Rp. 1.500.000 (BINTANG 5)");
        }else{
            txtMakspesawat.setText("-");
            txtMakskeretaapi.setText("-");
            txtMakshotel.setText("-");
        }
        edtName.setText(Name);
        edtNik.setText(Nik);
        String[] spRole = new String [] {"Staff","CEO","CPO","COO","CTO","MANAGER/BD"};
        adapter=new ArrayAdapter<String>(FormActivity.this, R.layout.spinner_jabatan, spRole);
        edtTitle.setAdapter(adapter);
        edtTitle.postDelayed(new Runnable() {
            @Override
            public void run() {
                edtTitle.setText(Titles);
                //edtTitle.showDropDown();

            }
        }, 10);

        String[] spDivision = new String [] {"IT","FINANCE","GA","OPERATION","MARKETING","SUPPORT"};
        adapterDiv=new ArrayAdapter<String>(FormActivity.this, R.layout.spinner_jabatan, spDivision);
        edtDivision.setAdapter(adapterDiv);
        edtDivision.postDelayed(new Runnable() {
            @Override
            public void run() {
                edtDivision.setText(Division);
                //edtDivision.showDropDown();

            }
        }, 10);
        String[] spUrgent = new String [] {"Sedang","Penting","Sangat Penting"};
        adapterUrgent=new ArrayAdapter<String>(FormActivity.this, R.layout.spinner_jabatan, spUrgent);
        edtUrgent.setAdapter(adapterUrgent);
        Log.d("Currend Datetime",""+currentDateandTime);

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
                        if(mediaPath1!=null){
                            btnUploadTicket.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_done,0,0,0);
                        }

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
                        if(mediaPath2!=null){
                            btnUploadHotel.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_done,0,0,0);
                        }
                    }

                }
            }
        }
    }




    private void submitForm(){


        if (mediaPath1== null && mediaPath2== null)
        {
            loadingDialogBar.showAlert("Ticket/Hotel Kosong");
            //Toast.makeText(getApplicationContext(), "Upload file ticket dan hotel terlebih  dahulu", Toast.LENGTH_LONG).show();
        }
        else {
            if(!edtName.getText().toString().equals("")){
                if(!edtNik.getText().toString().equals("")){
                    if(!edtPhonenumber.getText().toString().equals("")){
                        if(!edtEmail.getText().toString().equals("")){
                            if(!edtTravelreason.getText().toString().equals("")){
                                if(!edtFrom.getText().toString().equals("")){
                                    if(!edtTo.getText().toString().equals("")){
                                        if(!edtDate.getText().toString().equals("")){
                                            if(!edtDuration.getText().toString().equals("")){
                                                if(!edtUrgent.getText().toString().equals("")){
                                                    loadingDialogBar.showDialog("Mohon Tunggu");
                                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                                                    currentDateandTime = sdf.format(new Date());
                                                    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
                                                    String currentDateandTime2 = sdf2.format(new Date());
                                                    Log.d("CurrDate",""+currentDateandTime);
                                                    //String namafile= imagefile.getName().replace("-","").replace("_","").replace(" ","");
                                                    //resave file with new name
                                                    File imagefile = new File(mediaPath1);
                                                    MultipartBody.Part partImage = null;
                                                    if(imagefile.getName().toString().contains("png")){
                                                        File newFile = new File(Nik+currentDateandTime2+"transport"+".png");
                                                        Log.d("TRANSPORT AFTER",""+newFile.getName());
                                                        imagefile.renameTo(newFile);
                                                        Log.d("TRANSPORT BEFORE",""+imagefile.getName());
                                                        RequestBody reqBody = RequestBody.create(MediaType.parse("multipart/form-file"), imagefile);
                                                        partImage = MultipartBody.Part.createFormData("transport", newFile.getName(), reqBody);
                                                    }else if(imagefile.getName().toString().contains("jpg") || imagefile.getName().toString().contains("jpeg") ){
                                                        File newFile = new File(Nik+currentDateandTime2+"transport"+".jpg");
                                                        Log.d("TRANSPORT AFTER",""+newFile.getName());
                                                        imagefile.renameTo(newFile);
                                                        Log.d("TRANSPORT BEFORE",""+imagefile.getName());
                                                        RequestBody reqBody = RequestBody.create(MediaType.parse("multipart/form-file"), imagefile);
                                                        partImage = MultipartBody.Part.createFormData("transport", newFile.getName(), reqBody);
                                                    }


                                                    File imagefile2 = new File(mediaPath2);
                                                    MultipartBody.Part partImage2 = null;
                                                    if(imagefile2.getName().toString().contains("png")){
                                                        File newFile2 = new File(Nik+currentDateandTime2+"hotel"+".png");
                                                        Log.d("HOTEL AFTER",""+newFile2.getName());
                                                        imagefile2.renameTo(newFile2);
                                                        Log.d("HOTEL BEFORE",""+imagefile2.getName());
                                                        RequestBody reqBody2 = RequestBody.create(MediaType.parse("multipart/form-file"), imagefile2);
                                                        partImage2 = MultipartBody.Part.createFormData("hotel", newFile2.getName(), reqBody2);
                                                    }else if(imagefile2.getName().toString().contains("jpg") || imagefile.getName().toString().contains("jpeg") ){
                                                        File newFile2 = new File(Nik+currentDateandTime2+"hotel"+".jpg");
                                                        Log.d("HOTEL AFTER",""+newFile2.getName());
                                                        imagefile2.renameTo(newFile2);
                                                        Log.d("HOTEL BEFORE",""+imagefile2.getName());
                                                        RequestBody reqBody2 = RequestBody.create(MediaType.parse("multipart/form-file"), imagefile2);
                                                        partImage2 = MultipartBody.Part.createFormData("hotel", newFile2.getName(), reqBody2);
                                                    }

                                                    ApiInterface apiInterface = ApiHelper.createService(ApiInterface.class, "Bearer " + Token);
                                                    Call<SubmitFormModel> call = apiInterface.submitform(
                                                            partImage,
                                                            partImage2,
                                                            RequestBody.create(MediaType.parse("text/plain"),edtName.getText().toString()),
                                                            RequestBody.create(MediaType.parse("text/plain"),edtNik.getText().toString()),
                                                            RequestBody.create(MediaType.parse("text/plain"),edtDivision.getText().toString()),
                                                            RequestBody.create(MediaType.parse("text/plain"),edtTitle.getText().toString()),
                                                            RequestBody.create(MediaType.parse("text/plain"),edtPhonenumber.getText().toString()),
                                                            RequestBody.create(MediaType.parse("text/plain"),edtEmail.getText().toString()),
                                                            RequestBody.create(MediaType.parse("text/plain"),edtTravelreason.getText().toString()),
                                                            RequestBody.create(MediaType.parse("text/plain"),edtFrom.getText().toString()),
                                                            RequestBody.create(MediaType.parse("text/plain"),edtTo.getText().toString()),
                                                            RequestBody.create(MediaType.parse("text/plain"),edtDate.getText().toString()),
                                                            RequestBody.create(MediaType.parse("text/plain"),edtDuration.getText().toString()),
                                                            RequestBody.create(MediaType.parse("text/plain"),edtUrgent.getText().toString()),
                                                            RequestBody.create(MediaType.parse("text/plain"),Role),
                                                            RequestBody.create(MediaType.parse("text/plain"),"Berhasil"),
                                                            RequestBody.create(MediaType.parse("text/plain"),currentDateandTime)
                                                    );
                                                    call.enqueue(new Callback<SubmitFormModel>() {
                                                        @Override
                                                        public void onResponse(Call<SubmitFormModel> call, Response<SubmitFormModel> response) {
                                                            if (response.isSuccessful()) {
                                                                Toast.makeText(FormActivity.this, "Sukses Submit", Toast.LENGTH_SHORT).show();
                                                                Log.d("Status 2",""+response.message());
                                                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                                                editor.putString(KEY_SUCCESS,"1");
                                                                editor.apply();
                                                                Intent intent = new Intent(FormActivity.this, MainActivity.class);
                                                                startActivity(intent);
                                                            } else {
                                                                loadingDialogBar.hideDialog();
                                                                loadingDialogBar.showAlert("Gagal Submit");
                                                                Log.d("Status 2",""+response.message());
                                                                Toast.makeText(FormActivity.this, "Gagal Submit, " + response.message(), Toast.LENGTH_SHORT).show();
                                                            }
                                                        }

                                                        @Override
                                                        public void onFailure(Call<SubmitFormModel> call, Throwable t) {
                                                            Log.d("Status 2",""+t.getMessage());
                                                            loadingDialogBar.hideDialog();
                                                            loadingDialogBar.showAlert("Gagal Submit");
                                                            Toast.makeText(FormActivity.this, "Gagal Submit, " + t.getMessage(), Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                                }else{
                                                    loadingDialogBar.showAlert("Tingkat Urgensi Kosong");
                                                }
                                            }else{
                                                loadingDialogBar.showAlert("Lama Perjalanan Kosong");
                                            }
                                        }else{
                                            loadingDialogBar.showAlert("Tanggal Pergi Kosong");
                                        }
                                    }else{
                                        loadingDialogBar.showAlert("Kota Tujuan Kosong");
                                    }
                                }else{
                                    loadingDialogBar.showAlert("Kota Keberangkatan Kosong");
                                }
                            }else{
                                loadingDialogBar.showAlert("Alasan Perjalanan Kosong");
                            }
                        }else{
                            loadingDialogBar.showAlert("Email Kosong");
                        }
                    }else{
                        loadingDialogBar.showAlert("Phonenumber Kosong");
                    }
                }else{
                    loadingDialogBar.showAlert("Nik Kosong");
                }
            }else{
                loadingDialogBar.showAlert("Nama Kosong");
            }

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

    public void setupUI(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(FormActivity.this);
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        if(inputMethodManager.isAcceptingText()){
            inputMethodManager.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(),
                    0
            );
        }
    }
}
