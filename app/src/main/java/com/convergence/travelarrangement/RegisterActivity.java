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
import com.convergence.travelarrangement.model.RegisterModel;
import com.convergence.travelarrangement.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity{
    EditText edtNik,edtName,edtEmail,edtUsername, edtPassword, edtDivision, edtRole;
    TextView btnSignin;
    Button btnSignup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi);
        edtNik = findViewById(R.id.edtNik);
        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        edtDivision = findViewById(R.id.edtDivision);
        edtRole = findViewById(R.id.edtRole);
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnSignin = findViewById(R.id.btnSignin);
        btnSignup = findViewById(R.id.btnSignup);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup();
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

    void signup(){
        ApiInterface apiInterface = ApiHelper.createService(ApiInterface.class, "");
        Call<RegisterModel> call = apiInterface.registrasi(
                edtNik.getText().toString(),
                edtName.getText().toString(),
                edtEmail.getText().toString(),
                edtUsername.getText().toString(),
                edtPassword.getText().toString(),
                edtDivision.getText().toString(),
                edtRole.getText().toString()
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


}
