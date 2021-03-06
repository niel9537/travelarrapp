package com.convergence.travelarrangement;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.convergence.travelarrangement.model.LoginModel;
import com.convergence.travelarrangement.model.ProfileModel;
import com.convergence.travelarrangement.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity{
    EditText edtUsername, edtPassword;
    LoadingDialogBar loadingDialogBar;
    Button btnSignin;
    TextView btnSignup;
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_TOKEN = "token";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_ROLE = "role";
    private static final String KEY_DIVISION = "division";
    private static final String KEY_NAME = "name";
    private static final String KEY_NIK = "nik";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_TITLE = "title";
    private static final String KEY_SUCCESS = "success";
    String Success = "";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_login);
        loadingDialogBar = new LoadingDialogBar(this);
        setupUI(findViewById(R.id.loginpage));
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnSignin = findViewById(R.id.btnSignin);
        btnSignup = findViewById(R.id.btnSignup);
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!edtUsername.getText().toString().equals("") && !edtPassword.getText().toString().equals("")){
                    loadingDialogBar.showDialog("Mohon Tunggu");
                    login();
                }else{
                    loadingDialogBar.showAlert("Data Tidak Lengkap");
                }

            }
        });
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    void login(){
        ApiInterface apiInterface = ApiHelper.createService(ApiInterface.class, "");
        Call<LoginModel> call = apiInterface.login(
                edtUsername.getText().toString(),
                edtPassword.getText().toString()
        );
        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if(response.isSuccessful()){

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(KEY_TOKEN,response.body().getToken().toString());
                    editor.apply();
                    Log.d("KEY : ","Token "+response.body().getToken().toString());
                    getProfile(edtUsername.getText().toString(),response.body().getToken());
                }else{
                    loadingDialogBar.hideDialog();
                    loadingDialogBar.showAlert("Gagal Login");
                    Log.d("Gagal 2",""+response.message());
                    Toast.makeText(LoginActivity.this,"Gagal Login, "+response.message(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                Log.d("Gagal ",""+t.getMessage());
                loadingDialogBar.hideDialog();
                loadingDialogBar.showAlert("Gagal Login");
                Toast.makeText(LoginActivity.this,"Gagal Login, "+t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    void getProfile(String Username,String Token){
        ApiInterface apiInterface = ApiHelper.createService(ApiInterface.class, "Bearer "+Token);
        Call<ProfileModel> call = apiInterface.getProfile(
                Username);
        call.enqueue(new Callback<ProfileModel>() {
            @Override
            public void onResponse(Call<ProfileModel> call, Response<ProfileModel> response) {
                if(response.isSuccessful()){
                    List<User> userList = response.body().getUsers();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(KEY_USERNAME,userList.get(0).getUsername());
                    editor.putString(KEY_ROLE,userList.get(0).getRole());
                    editor.putString(KEY_DIVISION,userList.get(0).getDivision());
                    editor.putString(KEY_NAME,userList.get(0).getName());
                    editor.putString(KEY_NIK,userList.get(0).getNik());
                    editor.putString(KEY_EMAIL,userList.get(0).getEmail());
                    editor.putString(KEY_TITLE,userList.get(0).getTitle());
                    editor.putString(KEY_SUCCESS,"2");
                    editor.apply();
                    Log.d("TOKEN : ","Username "+userList.get(0).getUsername());

                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);

                }else{
                    loadingDialogBar.hideDialog();
                    loadingDialogBar.showAlert("Gagal Login");
                    Toast.makeText(LoginActivity.this,"Gagal Login, "+response.message(),Toast.LENGTH_SHORT).show();
                    Log.d("TOKEN : "," "+response.message());
                }
            }

            @Override
            public void onFailure(Call<ProfileModel> call, Throwable t) {
                loadingDialogBar.hideDialog();
                loadingDialogBar.showAlert("Gagal Login");
                Toast.makeText(LoginActivity.this,"Gagal Login, "+t.getMessage(),Toast.LENGTH_SHORT).show();
                Log.d("TOKEN : "," "+t.getMessage());
            }
        });
    }

    public void setupUI(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(LoginActivity.this);
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
