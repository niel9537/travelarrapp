package com.convergence.travelarrangement.mainfragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.convergence.travelarrangement.ApiHelper;
import com.convergence.travelarrangement.ApiInterface;
import com.convergence.travelarrangement.FormActivity;
import com.convergence.travelarrangement.ListFormAdapter;
import com.convergence.travelarrangement.ListFormAdminAdapter;
import com.convergence.travelarrangement.R;
import com.convergence.travelarrangement.model.GetListFormsModel;
import com.convergence.travelarrangement.model.ListForm;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    TextView txtList;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
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
    public ListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListFragment newInstance(String param1, String param2) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_list, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_listFormPendingKaryawan);
        sharedPreferences = this.getActivity().getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        Token = sharedPreferences.getString(KEY_TOKEN,null);
        Role = sharedPreferences.getString(KEY_ROLE,null);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        if(Role.equals("1")){
            showListFormPendingKaryawan();
        }else if(Role.equals("2")){
            showListFormPendingAdmin();
        }

        return view;
    }
    void showListFormPendingKaryawan(){
        ApiInterface apiInterface = ApiHelper.createService(ApiInterface.class, "Bearer "+Token);
        Call<GetListFormsModel> call = apiInterface.getlistformpendingkaryawan();
        call.enqueue(new Callback<GetListFormsModel>() {
            @Override
            public void onResponse(Call<GetListFormsModel> call, Response<GetListFormsModel> response) {
                if (response.isSuccessful()){
                    List<ListForm> listForms = response.body().getListForms();
                    mAdapter = new ListFormAdapter(listForms,getActivity());
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
    void showListFormPendingAdmin(){
        ApiInterface apiInterface = ApiHelper.createService(ApiInterface.class, "Bearer "+Token);
        Call<GetListFormsModel> call = apiInterface.getlistformpendingadmin();
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

}