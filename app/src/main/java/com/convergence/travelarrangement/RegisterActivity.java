package com.convergence.travelarrangement;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.convergence.travelarrangement.model.LoginModel;
import com.convergence.travelarrangement.model.ProfileModel;
import com.convergence.travelarrangement.model.RegisterModel;
import com.convergence.travelarrangement.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity{
    LoadingDialogBar loadingDialogBar;
    EditText edtNik,edtName,edtEmail,edtUsername, edtPassword;
    AutoCompleteTextView edtRole,edtDivision;
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> adapterDiv;
    TextView btnSignin;
    Button btnSignup;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_registrasi);
        loadingDialogBar = new LoadingDialogBar(this);
        setupUI(findViewById(R.id.registerpage));
        edtNik = findViewById(R.id.edtNik);
        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        edtDivision = findViewById(R.id.edtDivision);
        edtRole = findViewById(R.id.edtRole);
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnSignin = findViewById(R.id.btnSignin);
        btnSignup = findViewById(R.id.btnSignup);
        String[] spRole = new String [] {"Staff","CEO","CPO","COO","CTO","MANAGER/BD"};
        adapter=new ArrayAdapter<String>(
                RegisterActivity.this,
                R.layout.spinner_jabatan,
                spRole
        );
        edtRole.setAdapter(adapter);
        String[] spDivision = new String [] {"IT","FINANCE","GA","OPERATION","MARKETING","SUPPORT"};
        adapterDiv=new ArrayAdapter<String>(
                RegisterActivity.this,
                R.layout.spinner_jabatan,
                spDivision
        );
        edtDivision.setAdapter(adapterDiv);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!edtNik.getText().toString().equals("") &&
                   !edtRole.getText().toString().equals("") &&
                   !edtEmail.getText().toString().equals("") &&
                   !edtName.getText().toString().equals("") &&
                   !edtDivision.getText().toString().equals("") &&
                   !edtPassword.getText().toString().equals("") &&
                   !edtUsername.getText().toString().equals("")
                ){
                    loadingDialogBar.showDialog("Mohon Tunggu");
                    checkNik();
                }else{
                    loadingDialogBar.showAlert("Data Tidak Lengkap !");
                    //Toast.makeText(getApplicationContext(),"Data harus lengkap !",Toast.LENGTH_LONG).show();
                }

            }
        });
        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }
    public void checkNik(){
        ApiInterface apiInterface = ApiHelper.createService(ApiInterface.class, "");
        Call<ProfileModel> call = apiInterface.checknik(edtNik.getText().toString());
        call.enqueue(new Callback<ProfileModel>() {
            @Override
            public void onResponse(Call<ProfileModel> call, Response<ProfileModel> response) {
                if(response.isSuccessful()){
                    signup();
                }else{
                    loadingDialogBar.hideDialog();
                    loadingDialogBar.showAlert("NIK sudah ada!");

                }
            }

            @Override
            public void onFailure(Call<ProfileModel> call, Throwable t) {
                Toast.makeText(RegisterActivity.this,"Gagal menyambungkan, "+t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void signup(){
        String jabatan = "";
        if(edtRole.getText().toString().equals("Staff")){
            jabatan = "1";
        }else{
            jabatan = "5";
        }
        Log.d("Title ",""+edtRole.getText().toString());
        ApiInterface apiInterface = ApiHelper.createService(ApiInterface.class, "");
        Call<RegisterModel> call = apiInterface.registrasi(
                edtNik.getText().toString(),
                edtName.getText().toString(),
                edtEmail.getText().toString(),
                edtUsername.getText().toString(),
                edtPassword.getText().toString(),
                edtDivision.getText().toString(),
                edtRole.getText().toString(),
                jabatan
        );
        call.enqueue(new Callback<RegisterModel>() {
            @Override
            public void onResponse(Call<RegisterModel> call, Response<RegisterModel> response) {
                if(response.isSuccessful()){
                    Toast.makeText(RegisterActivity.this,"Berhasil Daftar",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(RegisterActivity.this,"Gagal Daftar, "+response.message(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterModel> call, Throwable t) {
                Toast.makeText(RegisterActivity.this,"Gagal Daftar, "+t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setupUI(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(RegisterActivity.this);
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
