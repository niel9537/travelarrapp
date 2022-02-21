package com.convergence.travelarrangement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.convergence.travelarrangement.mainfragment.AboutFragment;
import com.convergence.travelarrangement.mainfragment.DoneFragment;
import com.convergence.travelarrangement.mainfragment.HomeFragment;
import com.convergence.travelarrangement.mainfragment.ListFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    Toolbar toolbar;
    NavigationView navigationView;
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_TOKEN = "token";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_ROLE = "role";
    private static final String KEY_DIVISION = "division";
    private static final String KEY_NAME = "name";
    private static final String KEY_NIK = "nik";
    String Nama = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
}