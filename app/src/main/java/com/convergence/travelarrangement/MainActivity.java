package com.convergence.travelarrangement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.convergence.travelarrangement.mainfragment.AboutFragment;
import com.convergence.travelarrangement.mainfragment.DoneFragment;
import com.convergence.travelarrangement.mainfragment.HomeFragment;
import com.convergence.travelarrangement.mainfragment.ListFragment;
import com.convergence.travelarrangement.model.NotifModel;
import com.convergence.travelarrangement.model.ProfileModel;
import com.convergence.travelarrangement.model.User;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    Toolbar toolbar;
    NavigationView navigationView;
    SharedPreferences sharedPreferences;
    TextView txtSearch, txtNotif, txtBadge;

    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_TOKEN = "token";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_ROLE = "role";
    private static final String KEY_DIVISION = "division";
    private static final String KEY_NAME = "name";
    private static final String KEY_NIK = "nik";
    private static final String KEY_SUCCESS = "success";
    String success = "2";
    String Nama = "";
    String Token = "";
    String Nik = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer);
        toolbar = findViewById(R.id.toolBar);
        navigationView = findViewById(R.id.nav_view);
        setSupportActionBar(toolbar);
        toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView=findViewById(R.id.nav_view);

        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.frame, new HomeFragment());
        tx.commit();
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        Nama = sharedPreferences.getString(KEY_NAME,null);
        Token = sharedPreferences.getString(KEY_TOKEN,null);
        Nik = sharedPreferences.getString(KEY_NIK,null);
        View headerView = navigationView.getHeaderView(0);
        TextView txtNames = (TextView) headerView.findViewById(R.id.names);
        txtNames.setText(Nama);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                Fragment fragment = null;
                switch (id){
                    case R.id.list:
                        fragment =new ListFragment();
                        loadFragment(fragment);
                        toolbar.setTitle("Menunggu/Revisi");
                        break;
                    case R.id.done:
                        fragment =new DoneFragment();
                        loadFragment(fragment);
                        toolbar.setTitle("Selesai");
                        break;
                    case R.id.about:
                        fragment =new AboutFragment();
                        loadFragment(fragment);
                        toolbar.setTitle("Tentang Kami");
                        break;
                    case R.id.home:
                        fragment =new HomeFragment();
                        loadFragment(fragment);
                        toolbar.setTitle("Travel Arrangement App");
                        break;
                    case R.id.logout:
                        SharedPreferences preferences =getSharedPreferences("mypref", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.clear();
                        editor.apply();
                        Toast.makeText(getApplicationContext(),"Berhasil Log Out",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    public void loadFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame,fragment).commit();
        drawerLayout.closeDrawer(GravityCompat.START);
        fragmentTransaction.addToBackStack(null);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
     //create an action bar button
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // R.menu.mymenu is a reference to an xml file named mymenu.xml which should be inside your res/menu directory.
//        // If you don't have res/menu, just create a directory named "menu" inside res
//        getMenuInflater().inflate(R.menu.menu_search, menu);
//        return super.onCreateOptionsMenu(menu);
//    }
    private void setupBadge(View actionView) {
        ApiInterface apiInterface = ApiHelper.createService(ApiInterface.class, "Bearer "+Token);
        Call<ProfileModel> call = apiInterface.getnotif(Nik);
        call.enqueue(new Callback<ProfileModel>() {
            @Override
            public void onResponse(Call<ProfileModel> call, Response<ProfileModel> response) {
                if(response.isSuccessful()){
                    List<User> userList = response.body().getUsers();
                    if(userList.get(0).getReadable().equals("1")){
                        Log.d("NOTIF : "," "+userList.get(0).getReadable());
                    }else{
                    }
                }else{
                    Toast.makeText(MainActivity.this,"Gagal dapat notif, "+response.message(),Toast.LENGTH_SHORT).show();
                    Log.d("TOKEN : "," "+response.message());
                }
            }

            @Override
            public void onFailure(Call<ProfileModel> call, Throwable t) {
                Toast.makeText(MainActivity.this,"Gagal menyambungkan, "+t.getMessage(),Toast.LENGTH_SHORT).show();
                Log.d("TOKEN : "," "+t.getMessage());
            }
        });
    }

    // handle button activities
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
////        Toast.makeText(MainActivity.this,""+"Alert",Toast.LENGTH_SHORT).show();
////        int id = item.getItemId();
////
////        if (id == R.id.search) {
////            Toast.makeText(MainActivity.this,""+"Alert",Toast.LENGTH_SHORT).show();
//           Intent intent = new Intent(MainActivity.this,SearchActivity.class);
//           startActivity(intent);
////        }
//        return super.onOptionsItemSelected(item);
//    }

}