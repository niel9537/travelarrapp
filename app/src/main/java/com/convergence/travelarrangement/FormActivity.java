package com.convergence.travelarrangement;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.convergence.travelarrangement.model.ProfileModel;
import com.convergence.travelarrangement.model.SubmitFormModel;

import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormActivity extends AppCompatActivity {
    RadioGroup radioGroup;
    RadioButton radioButton;
    EditText edtName, edtNik, edtDivision, edtPhonenumber, edtEmail, edtTravelreason, edtFrom, edtTo, edtDate, edtDuration;
    Button btnSubmit;
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_TOKEN = "token";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_ROLE = "role";
    private static final String KEY_DIVISION = "division";
    private static final String KEY_NAME = "name";
    private static final String KEY_NIK = "nik";
    String Token = "";
    String Urgent = "";
    DatePickerDialog.OnDateSetListener onDateSetListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBarform);
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
                submitForm();
            }
        });
    }

    public void checkButton(View v){
        //Cara ambil data dari radiobutton
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
        Urgent = radioButton.getText().toString();
        Toast.makeText(this,"Level "+radioButton.getText().toString(), Toast.LENGTH_SHORT).show();

    }

    private void submitForm(){
        ApiInterface apiInterface = ApiHelper.createService(ApiInterface.class, "Bearer "+Token);
        Call<SubmitFormModel> call = apiInterface.submitform(
                edtName.getText().toString(),
                edtNik.getText().toString(),
                edtDivision.getText().toString(),
                edtPhonenumber.getText().toString(),
                edtEmail.getText().toString(),
                edtTravelreason.getText().toString(),
                edtFrom.getText().toString(),
                edtTo.getText().toString(),
                edtDate.getText().toString(),
                edtDuration.getText().toString(),
                Urgent
        );
        call.enqueue(new Callback<SubmitFormModel>() {
            @Override
            public void onResponse(Call<SubmitFormModel> call, Response<SubmitFormModel> response) {
                if (response.isSuccessful()){
                    Toast.makeText(FormActivity.this,"Sukses Submit",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(FormActivity.this,MainActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(FormActivity.this,"Gagal Submit, "+response.message(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SubmitFormModel> call, Throwable t) {
                Toast.makeText(FormActivity.this,"Gagal Submit, "+t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
