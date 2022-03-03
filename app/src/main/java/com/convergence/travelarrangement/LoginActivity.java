package com.convergence.travelarrangement;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.convergence.travelarrangement.model.LoginModel;
import com.convergence.travelarrangement.model.ProfileModel;
import com.convergence.travelarrangement.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity{
    EditText edtUsername, edtPassword;
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
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnSignin = findViewById(R.id.btnSignin);
        btnSignup = findViewById(R.id.btnSignup);
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
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
                    Toast.makeText(LoginActivity.this,"Gagal Login, "+response.message(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
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
                    editor.apply();
                    Log.d("TOKEN : ","Username "+userList.get(0).getUsername());
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(LoginActivity.this,"Gagal Login, "+response.message(),Toast.LENGTH_SHORT).show();
                    Log.d("TOKEN : "," "+response.message());
                }
            }

            @Override
            public void onFailure(Call<ProfileModel> call, Throwable t) {
                Toast.makeText(LoginActivity.this,"Gagal Login, "+t.getMessage(),Toast.LENGTH_SHORT).show();
                Log.d("TOKEN : "," "+t.getMessage());
            }
        });
    }


}
