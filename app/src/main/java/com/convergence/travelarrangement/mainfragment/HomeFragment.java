package com.convergence.travelarrangement.mainfragment;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.convergence.travelarrangement.ApiHelper;
import com.convergence.travelarrangement.ApiInterface;
import com.convergence.travelarrangement.FormActivity;
import com.convergence.travelarrangement.ListFormAdapter;
import com.convergence.travelarrangement.ListFormAdminAdapter;
import com.convergence.travelarrangement.ListFormFinanceAdapter;
import com.convergence.travelarrangement.ListFormManajerAdapter;
import com.convergence.travelarrangement.ListFormManajerDivAdapter;
import com.convergence.travelarrangement.ListFormTitleCAdapter;
import com.convergence.travelarrangement.MainActivity;
import com.convergence.travelarrangement.R;
import com.convergence.travelarrangement.model.GetListFormsModel;
import com.convergence.travelarrangement.model.ListForm;
import com.convergence.travelarrangement.model.SubmitFormModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements SearchView.OnQueryTextListener{
    Button btnTambah;
    Adapter adapter;
    TextView txtNama, txtKata;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private ListFormAdapter XAdapter;
    List<ListForm> formArrayList;
    private RecyclerView.LayoutManager mLayoutManager;
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_TOKEN = "token";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_ROLE = "role";
    private static final String KEY_DIVISION = "division";
    private static final String KEY_NAME = "name";
    private static final String KEY_NIK = "nik";
    String Token = "";
    String Role = "";
    String NamaUser = "";
    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        setHasOptionsMenu(true);
        formArrayList = new ArrayList<>();
        txtNama = view.findViewById(R.id.txtNama);
        txtKata = view.findViewById(R.id.txtKata);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_listForm);
        sharedPreferences = this.getActivity().getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        Token = sharedPreferences.getString(KEY_TOKEN,null);
        Role = sharedPreferences.getString(KEY_ROLE,null);
        NamaUser = sharedPreferences.getString(KEY_NAME,null);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        btnTambah = view.findViewById(R.id.btnTambah);
        Log.d("Role ",""+Role);
        if(Role.equals("2")){
            showListFormAdmin();
            btnTambah.setVisibility(view.GONE);
            txtNama.setText("Hi, "+NamaUser);
            txtKata.setVisibility(view.GONE);
        }else if(Role.equals("1")){
            showListForm();
        }else if(Role.equals("3")){
            showListFormManajer();
            btnTambah.setVisibility(view.GONE);
            txtNama.setText("Hi, "+NamaUser);
            txtKata.setVisibility(view.GONE);
        }else if(Role.equals("4")){
            showListFormFinance();
            btnTambah.setVisibility(view.GONE);
            txtNama.setText("Hi, "+NamaUser);
            txtKata.setVisibility(view.GONE);
        }else if(Role.equals("5")){
            showListFormManajerDiv();
            btnTambah.setVisibility(view.GONE);
            txtNama.setText("Hi, "+NamaUser);
            txtKata.setVisibility(view.GONE);
        }else if(Role.equals("6")){
            showListFormTitleC();
            btnTambah.setVisibility(view.GONE);
            txtNama.setText("Hi, "+NamaUser);
            txtKata.setVisibility(view.GONE);
        }else{
            //btnTambah.setVisibility(view.GONE);
            showListForm();
        }


        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), FormActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
    void showListForm(){
        ApiInterface apiInterface = ApiHelper.createService(ApiInterface.class, "Bearer "+Token);
        Call<GetListFormsModel> call = apiInterface.getlistform();
        call.enqueue(new Callback<GetListFormsModel>() {
            @Override
            public void onResponse(Call<GetListFormsModel> call, Response<GetListFormsModel> response) {
                if (response.isSuccessful()){
                    List<ListForm> listForms = response.body().getListForms();
                    mAdapter = new ListFormAdapter(listForms,getActivity());
                    formArrayList = response.body().getListForms();
                    XAdapter = new ListFormAdapter(formArrayList,getActivity());
                    mRecyclerView.setAdapter(mAdapter);
                }else{
                    Toast.makeText(getActivity(),"Gagal Tampil, "+response.message(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetListFormsModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Gagal Tampil, "+t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
    void showListFormAdmin(){
        ApiInterface apiInterface = ApiHelper.createService(ApiInterface.class, "Bearer "+Token);
        Call<GetListFormsModel> call = apiInterface.getlistformadmin();
        call.enqueue(new Callback<GetListFormsModel>() {
            @Override
            public void onResponse(Call<GetListFormsModel> call, Response<GetListFormsModel> response) {
                if (response.isSuccessful()){
                    List<ListForm> listForms = response.body().getListForms();
                    mAdapter = new ListFormAdminAdapter(listForms,getActivity());
                    mRecyclerView.setAdapter(mAdapter);
                }else{
                    Toast.makeText(getActivity(),"Gagal Tampil, "+response.message(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetListFormsModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Gagal Tampil, "+t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
    void showListFormManajer(){
        ApiInterface apiInterface = ApiHelper.createService(ApiInterface.class, "Bearer "+Token);
        Call<GetListFormsModel> call = apiInterface.getlistformmanajer();
        call.enqueue(new Callback<GetListFormsModel>() {
            @Override
            public void onResponse(Call<GetListFormsModel> call, Response<GetListFormsModel> response) {
                if (response.isSuccessful()){
                    List<ListForm> listForms = response.body().getListForms();
                    mAdapter = new ListFormManajerAdapter(listForms,getActivity());
                    mRecyclerView.setAdapter(mAdapter);
                }else{
                    Toast.makeText(getActivity(),"Gagal Tampil, "+response.message(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetListFormsModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Gagal Tampil, "+t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    void showListFormFinance(){
        ApiInterface apiInterface = ApiHelper.createService(ApiInterface.class, "Bearer "+Token);
        Call<GetListFormsModel> call = apiInterface.getlistformfinance();
        call.enqueue(new Callback<GetListFormsModel>() {
            @Override
            public void onResponse(Call<GetListFormsModel> call, Response<GetListFormsModel> response) {
                if (response.isSuccessful()){
                    List<ListForm> listForms = response.body().getListForms();
                    mAdapter = new ListFormFinanceAdapter(listForms,getActivity());
                    mRecyclerView.setAdapter(mAdapter);
                }else{
                    Toast.makeText(getActivity(),"Gagal Tampil, "+response.message(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetListFormsModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Gagal Tampil, "+t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
    void showListFormTitleC(){
        ApiInterface apiInterface = ApiHelper.createService(ApiInterface.class, "Bearer "+Token);
        Call<GetListFormsModel> call = apiInterface.getlistformmanajerdiv();
        call.enqueue(new Callback<GetListFormsModel>() {
            @Override
            public void onResponse(Call<GetListFormsModel> call, Response<GetListFormsModel> response) {
                if (response.isSuccessful()){
                    List<ListForm> listForms = response.body().getListForms();
                    mAdapter = new ListFormTitleCAdapter(listForms,getActivity());
                    mRecyclerView.setAdapter(mAdapter);
                }else{
                    Toast.makeText(getActivity(),"Gagal Tampil, "+response.message(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetListFormsModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Gagal Tampil, "+t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
    void showListFormManajerDiv(){
        ApiInterface apiInterface = ApiHelper.createService(ApiInterface.class, "Bearer "+Token);
        Call<GetListFormsModel> call = apiInterface.getlistformmanajerdiv();
        call.enqueue(new Callback<GetListFormsModel>() {
            @Override
            public void onResponse(Call<GetListFormsModel> call, Response<GetListFormsModel> response) {
                if (response.isSuccessful()){
                    List<ListForm> listForms = response.body().getListForms();
                    mAdapter = new ListFormManajerDivAdapter(listForms,getActivity());
                    mRecyclerView.setAdapter(mAdapter);
                }else{
                    Toast.makeText(getActivity(),"Gagal Tampil, "+response.message(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetListFormsModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Gagal Tampil, "+t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
    void showListFormPendingManajer(){
        ApiInterface apiInterface = ApiHelper.createService(ApiInterface.class, "Bearer "+Token);
        Call<GetListFormsModel> call = apiInterface.getlistformpendingmanajer();
        call.enqueue(new Callback<GetListFormsModel>() {
            @Override
            public void onResponse(Call<GetListFormsModel> call, Response<GetListFormsModel> response) {
                if (response.isSuccessful()){
                    List<ListForm> listForms = response.body().getListForms();
                    mAdapter = new ListFormFinanceAdapter(listForms,getActivity());
                    mRecyclerView.setAdapter(mAdapter);
                }else{
                    Toast.makeText(getActivity(),"Gagal Tampil, "+response.message(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetListFormsModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Gagal Tampil, "+t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);

        final MenuItem item = menu.findItem(R.id.search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);


        item.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                // Do something when collapsed
                XAdapter.setFilter(formArrayList);
                return true; // Return true to collapse action view
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                // Do something when expanded
                return true; // Return true to expand action view
            }
        });

    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final List<ListForm> filteredModelList = filter(formArrayList, newText);
        XAdapter.setFilter(filteredModelList);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    private List<ListForm> filter(List<ListForm> models, String query) {
        query = query.toLowerCase();

        final List<ListForm> filteredModelList = new ArrayList<>();
        for (ListForm model : models) {
            final String text = model.getName().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

}