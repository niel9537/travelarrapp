package com.convergence.travelarrangement;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.convergence.travelarrangement.model.ListForm;
import com.convergence.travelarrangement.model.NotifModel;
import com.convergence.travelarrangement.model.SetListFormsModel;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListFormFinanceAdapter extends RecyclerView.Adapter<ListFormFinanceAdapter.MyViewHolder> {
    LoadingDialogBar loadingDialogBar;
    List<ListForm> listForm;
    Context context;

    public ListFormFinanceAdapter(List<ListForm> listForm, Context context) {
        this.listForm = listForm;
        this.context = context;
    }

    @Override
    public ListFormFinanceAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listform, parent, false);
        ListFormFinanceAdapter.MyViewHolder mViewHolder = new ListFormFinanceAdapter.MyViewHolder(mView);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(ListFormFinanceAdapter.MyViewHolder holder, final int position) {
        String SHARED_PREF_NAME = "mypref";
        String KEY_ROLE = "role";
        String KEY_TOKEN = "token";
        String KEY_EMAIL = "email";
        String email = "";
        String tokens = "";
        String role = "";


        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        email = preferences.getString(KEY_EMAIL, null);
        role = preferences.getString(KEY_ROLE, null);
        tokens = preferences.getString(KEY_TOKEN, null);
        Log.d("Role", ""+role);

        holder.txtName.setText(listForm.get(position).getName());
        holder.txtDates.setText(listForm.get(position).getDates());
        holder.txtUrgent.setText(listForm.get(position).getUrgent());
        if(listForm.get(position).getStatus().equals("1")){
            holder.txtStatus.setText("Status : Menunggu konfirmasi dari Admin");
            holder.linearLayout.setBackgroundResource(R.color.yellowanedot);
        }else if(listForm.get(position).getStatus().equals("2")){
            holder.txtStatus.setText("Status : Admin telah konfirmasi, menunggu konfirmasi dari Manajer");
            holder.linearLayout.setBackgroundResource(R.color.yellowanedot);
        }else if(listForm.get(position).getStatus().equals("3")){
            holder.txtStatus.setText("Status : Permintaan ditolak oleh Admin, silahkan melakukan revisi");
            holder.linearLayout.setBackgroundResource(R.color.redanedot);
        }else if(listForm.get(position).getStatus().equals("4")){
            holder.txtStatus.setText("Status : Permintaan dikonfirmasi oleh manajer, sedang di proses oleh tim finance");
            holder.linearLayout.setBackgroundResource(R.color.yellowanedot);
        }else if(listForm.get(position).getStatus().equals("5")){
            holder.txtStatus.setText("Status : Permintaan ditolak oleh Manajer, form dibatalkan");
            holder.linearLayout.setBackgroundResource(R.color.redanedot);
        }else if(listForm.get(position).getStatus().equals("6")){
            holder.txtStatus.setText("Status : Permintaan budget dikonfirmasi oleh tim finance.");
        }else if(listForm.get(position).getStatus().equals("99")){
            holder.txtStatus.setText("Status : Permintaan budget ditolak oleh tim finance, form dibatalkan");
            holder.linearLayout.setBackgroundResource(R.color.redanedot);
        }else{
            holder.txtStatus.setText("Status : Tidak terdefinisikan");
        }

        if(listForm.get(position).getStatus().equals("4"))
        {
            String finalTokens = tokens;
            String finalEmail = email;
            String finalRole = role;
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadingDialogBar = new LoadingDialogBar(context);
                    AlertDialog.Builder builder=new AlertDialog.Builder(v.getContext());
                    View view=LayoutInflater.from(context).inflate(R.layout.modal_detailformfinance,null);
                    TextView txtDesc = (TextView) view.findViewById(R.id.txtDesc);
                    TextView txtDescription = (TextView) view.findViewById(R.id.txtDescription);
                    EditText edtDescription = (EditText) view.findViewById(R.id.edtDescription);
                    TextView txtCreateat = (TextView) view.findViewById(R.id.txtCreateat);
                    txtCreateat.setText(listForm.get(position).getCreateat());
                    TextView txtFrom = (TextView) view.findViewById(R.id.txtFrom);
                    txtFrom.setText(listForm.get(position).getFromcity());
                    TextView txtTo = (TextView) view.findViewById(R.id.txtTo);
                    txtTo.setText(listForm.get(position).getTocity());
                    TextView txtName = (TextView) view.findViewById(R.id.txtName);
                    txtName.setText(listForm.get(position).getName());
                    TextView txtNik = (TextView) view.findViewById(R.id.txtNik);
                    txtNik.setText(listForm.get(position).getNik());
                    TextView txtPhonenumber = (TextView) view.findViewById(R.id.txtPhonenumber);
                    txtPhonenumber.setText(listForm.get(position).getPhonenumber());
                    TextView txtEmail = (TextView) view.findViewById(R.id.txtEmail);
                    txtEmail.setText(listForm.get(position).getEmail());
                    TextView txtDates = (TextView) view.findViewById(R.id.txtDates);
                    txtDates.setText(listForm.get(position).getDates());
                    TextView txtDuration = (TextView) view.findViewById(R.id.txtDuration);
                    txtDuration.setText(listForm.get(position).getDuration());
                    TextView txtTravelreason = (TextView) view.findViewById(R.id.txtTravelreason);
                    txtTravelreason.setText(listForm.get(position).getTravelreason());
                    TextView txtUrgent = (TextView) view.findViewById(R.id.txtUrgent);
                    txtUrgent.setText(listForm.get(position).getUrgent());
                    TextView txtBudget = (TextView) view.findViewById(R.id.txtBudget);
                    int number = Integer.parseInt(listForm.get(position).getBudget());
                    String str = String.format(Locale.US, "%,d", number).replace(',', '.');
                    txtBudget.setText("Rp "+str);
                    TextView txtStatus = (TextView) view.findViewById(R.id.txtStatus);
                    if(listForm.get(position).getStatus().equals("1")){
                        txtStatus.setText("Status : Menunggu konfirmasi dari Admin");
                    }else if(listForm.get(position).getStatus().equals("2")){
                        txtStatus.setText("Status : Admin telah konfirmasi, menunggu konfirmasi dari Manajer");
                    }else if(listForm.get(position).getStatus().equals("3")){
                        txtStatus.setText("Status : Permintaan ditolak oleh Admin, silahkan melakukan revisi");
                    }else if(listForm.get(position).getStatus().equals("4")){
                        txtStatus.setText("Status : Permintaan dikonfirmasi oleh manajer, sedang di proses oleh tim finance");
                    }else if(listForm.get(position).getStatus().equals("5")){
                        txtStatus.setText("Status : Permintaan ditolak oleh Manajer, form dibatalkan");
                        txtDesc.setText("Alasan ");
                        txtDescription.setText(listForm.get(position).getDescription());
                    }else if(listForm.get(position).getStatus().equals("6")){
                        txtStatus.setText("Status : Permintaan budget dikonfirmasi oleh tim finance.");
                        txtDescription.setText("Approve");
                        txtDescription.setTextColor(Color.parseColor("#ff99cc00"));
                    }else if(listForm.get(position).getStatus().equals("7")){
                        txtStatus.setText("Status : Menunggu konfirmasi dari Title C");
                    }else if(listForm.get(position).getStatus().equals("8")){
                        txtStatus.setText("Status : Permintaan ditolak oleh Title C, form dibatalkan");
                        txtDesc.setText("Alasan ");
                        txtDescription.setText(listForm.get(position).getDescription());
                    }else if(listForm.get(position).getStatus().equals("9")){
                        txtStatus.setText("Status : Permintaan dikonfirmasi oleh Title C");
                        txtDescription.setText("Approve");
                        txtDescription.setTextColor(Color.parseColor("#ff99cc00"));
                    }else if(listForm.get(position).getStatus().equals("99")){
                        txtStatus.setText("Status : Permintaan budget ditolak oleh tim finance, form dibatalkan");
                        txtDesc.setText("Alasan ");
                        txtDescription.setText(listForm.get(position).getDescription());
                    }else{
                        txtStatus.setText("Status : Tidak terdefinisikan");
                    }
                    LinearLayout linearLayout= (LinearLayout) view.findViewById(R.id.alertdialogpage);
                    LinearLayout linearLayout2= (LinearLayout) view.findViewById(R.id.alertdialogpage2);
                    linearLayout.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context
                                    .INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(edtDescription.getWindowToken(), 0);
                            return true;
                        }
                    });
                    linearLayout2.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context
                                    .INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(edtDescription.getWindowToken(), 0);
                            return true;
                        }
                    });
                    builder.setView(view);
                    Button btnKembali = view.findViewById(R.id.btnKembali);
                    Button btnTerima = view.findViewById(R.id.btnTerima);
                    Button btnTolak = view.findViewById(R.id.btnTolak);
                    Button btnTransport = view.findViewById(R.id.btnTransport);
                    Button btnHotel = view.findViewById(R.id.btnHotel);

                    final AlertDialog alertDialog=builder.create();

                    btnKembali.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                        }
                    });
                    btnTransport.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(context,DetailImageActivity.class);
                            intent.putExtra("path", listForm.get(position).getTransport().toString());
                            context.startActivity(intent);
                        }
                    });
                    btnHotel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(context,DetailImageActivity.class);
                            intent.putExtra("path", listForm.get(position).getHotel().toString());
                            context.startActivity(intent);
                        }
                    });
                    btnTerima.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            loadingDialogBar.showDialog("Mohon Tunggu");
                            terimaAdmin(view,listForm.get(position).getIdTicketarr(), finalTokens, "6",txtName.getText().toString(), finalEmail, finalRole, txtNik.getText().toString());
                            alertDialog.dismiss();
                        }
                    });
                    //btnTolak.setVisibility(view.GONE);
                    btnTolak.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(!edtDescription.getText().toString().equals("")) {
                                loadingDialogBar.showDialog("Mohon Tunggu");
                                tolakAdmin(view, listForm.get(position).getIdTicketarr(), finalTokens, "99", txtName.getText().toString(), finalEmail, finalRole, txtNik.getText().toString(), edtDescription.getText().toString());
                                alertDialog.dismiss();
                            }else{
                                loadingDialogBar.showAlert("Tentukan Reason");
                            }
                        }


                    });
                    alertDialog.show();
                }

            });
        }else if(listForm.get(position).getStatus().equals("6")){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder builder=new AlertDialog.Builder(v.getContext());
                    View view=LayoutInflater.from(context).inflate(R.layout.modal_detailform,null);
                    TextView txtDesc = (TextView) view.findViewById(R.id.txtDesc);
                    TextView txtDescription = (TextView) view.findViewById(R.id.txtDescription);
                    TextView txtCreateat = (TextView) view.findViewById(R.id.txtCreateat);
                    txtCreateat.setText(listForm.get(position).getCreateat());
                    TextView txtFrom = (TextView) view.findViewById(R.id.txtFrom);
                    txtFrom.setText(listForm.get(position).getFromcity());
                    TextView txtTo = (TextView) view.findViewById(R.id.txtTo);
                    txtTo.setText(listForm.get(position).getTocity());
                    TextView txtName = (TextView) view.findViewById(R.id.txtName);
                    txtName.setText(listForm.get(position).getName());
                    TextView txtNik = (TextView) view.findViewById(R.id.txtNik);
                    txtNik.setText(listForm.get(position).getNik());
                    TextView txtPhonenumber = (TextView) view.findViewById(R.id.txtPhonenumber);
                    txtPhonenumber.setText(listForm.get(position).getPhonenumber());
                    TextView txtEmail = (TextView) view.findViewById(R.id.txtEmail);
                    txtEmail.setText(listForm.get(position).getEmail());
                    TextView txtDates = (TextView) view.findViewById(R.id.txtDates);
                    txtDates.setText(listForm.get(position).getDates());
                    TextView txtDuration = (TextView) view.findViewById(R.id.txtDuration);
                    txtDuration.setText(listForm.get(position).getDuration());
                    TextView txtTravelreason = (TextView) view.findViewById(R.id.txtTravelreason);
                    txtTravelreason.setText(listForm.get(position).getTravelreason());
                    TextView txtUrgent = (TextView) view.findViewById(R.id.txtUrgent);
                    txtUrgent.setText(listForm.get(position).getUrgent());
                    TextView txtBudget = (TextView) view.findViewById(R.id.txtBudget);
                    int number = Integer.parseInt(listForm.get(position).getBudget());
                    String str = String.format(Locale.US, "%,d", number).replace(',', '.');
                    txtBudget.setText("Rp "+str);
                    TextView txtStatus = (TextView) view.findViewById(R.id.txtStatus);
                    if(listForm.get(position).getStatus().equals("1")){
                        txtStatus.setText("Status : Menunggu konfirmasi dari Admin");
                    }else if(listForm.get(position).getStatus().equals("2")){
                        txtStatus.setText("Status : Admin telah konfirmasi, menunggu konfirmasi dari Manajer");
                    }else if(listForm.get(position).getStatus().equals("3")){
                        txtStatus.setText("Status : Permintaan ditolak oleh Admin, silahkan melakukan revisi");
                    }else if(listForm.get(position).getStatus().equals("4")){
                        txtStatus.setText("Status : Permintaan dikonfirmasi oleh manajer.");
                    }else if(listForm.get(position).getStatus().equals("5")){
                        txtStatus.setText("Status : Permintaan ditolak oleh Manajer, form dibatalkan");
                        txtDesc.setText("Alasan ");
                        txtDescription.setText(listForm.get(position).getDescription());
                    }else if(listForm.get(position).getStatus().equals("6")){
                        txtStatus.setText("Status : Permintaan budget dikonfirmasi oleh tim finance, sedang mencetak laporan");
                        txtDescription.setText("Approve");
                        txtDescription.setTextColor(Color.parseColor("#ff99cc00"));
                    }else if(listForm.get(position).getStatus().equals("7")){
                        txtStatus.setText("Status : Menunggu konfirmasi dari Title C");
                    }else if(listForm.get(position).getStatus().equals("8")){
                        txtStatus.setText("Status : Permintaan ditolak oleh Title C, form dibatalkan");
                        txtDesc.setText("Alasan ");
                        txtDescription.setText(listForm.get(position).getDescription());
                    }else if(listForm.get(position).getStatus().equals("9")){
                        txtStatus.setText("Status : Permintaan dikonfirmasi oleh Title C");
                        txtDescription.setText("Approve");
                        txtDescription.setTextColor(Color.parseColor("#ff99cc00"));
                    }else if(listForm.get(position).getStatus().equals("99")){
                        txtStatus.setText("Status : Permintaan budget ditolak oleh tim finance, form dibatalkan");
                        txtDesc.setText("Alasan ");
                        txtDescription.setText(listForm.get(position).getDescription());
                    }else{
                        txtStatus.setText("Status : Tidak terdefinisikan");
                    }

                    builder.setView(view);
                    Button btnKembali = view.findViewById(R.id.btnKembali);
                    Button btnTransport = view.findViewById(R.id.btnTransport);
                    Button btnHotel = view.findViewById(R.id.btnHotel);

                    final AlertDialog alertDialog=builder.create();

                    btnKembali.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                        }
                    });
                    btnTransport.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(context,DetailImageActivity.class);
                            intent.putExtra("path", listForm.get(position).getTransport().toString());
                            context.startActivity(intent);
                        }
                    });
                    btnHotel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(context,DetailImageActivity.class);
                            intent.putExtra("path", listForm.get(position).getHotel().toString());
                            context.startActivity(intent);
                        }
                    });
                    alertDialog.show();
                }

            });
        }else{
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder builder=new AlertDialog.Builder(v.getContext());
                    View view=LayoutInflater.from(context).inflate(R.layout.modal_detailform,null);
                    TextView txtDesc = (TextView) view.findViewById(R.id.txtDesc);
                    TextView txtDescription = (TextView) view.findViewById(R.id.txtDescription);
                    TextView txtCreateat = (TextView) view.findViewById(R.id.txtCreateat);
                    txtCreateat.setText(listForm.get(position).getCreateat());
                    TextView txtFrom = (TextView) view.findViewById(R.id.txtFrom);
                    txtFrom.setText(listForm.get(position).getFromcity());
                    TextView txtTo = (TextView) view.findViewById(R.id.txtTo);
                    txtTo.setText(listForm.get(position).getTocity());
                    TextView txtName = (TextView) view.findViewById(R.id.txtName);
                    txtName.setText(listForm.get(position).getName());
                    TextView txtNik = (TextView) view.findViewById(R.id.txtNik);
                    txtNik.setText(listForm.get(position).getNik());
                    TextView txtPhonenumber = (TextView) view.findViewById(R.id.txtPhonenumber);
                    txtPhonenumber.setText(listForm.get(position).getPhonenumber());
                    TextView txtEmail = (TextView) view.findViewById(R.id.txtEmail);
                    txtEmail.setText(listForm.get(position).getEmail());
                    TextView txtDates = (TextView) view.findViewById(R.id.txtDates);
                    txtDates.setText(listForm.get(position).getDates());
                    TextView txtDuration = (TextView) view.findViewById(R.id.txtDuration);
                    txtDuration.setText(listForm.get(position).getDuration());
                    TextView txtTravelreason = (TextView) view.findViewById(R.id.txtTravelreason);
                    txtTravelreason.setText(listForm.get(position).getTravelreason());
                    TextView txtUrgent = (TextView) view.findViewById(R.id.txtUrgent);
                    txtUrgent.setText(listForm.get(position).getUrgent());
                    TextView txtBudget = (TextView) view.findViewById(R.id.txtBudget);
                    int number = Integer.parseInt(listForm.get(position).getBudget());
                    String str = String.format(Locale.US, "%,d", number).replace(',', '.');
                    txtBudget.setText("Rp "+str);
                    TextView txtStatus = (TextView) view.findViewById(R.id.txtStatus);
                    if(listForm.get(position).getStatus().equals("1")){
                        txtStatus.setText("Status : Menunggu konfirmasi dari Admin");
                    }else if(listForm.get(position).getStatus().equals("2")){
                        txtStatus.setText("Status : Admin telah konfirmasi, menunggu konfirmasi dari Manajer");
                    }else if(listForm.get(position).getStatus().equals("3")){
                        txtStatus.setText("Status : Permintaan ditolak oleh Admin, silahkan melakukan revisi");
                    }else if(listForm.get(position).getStatus().equals("4")){
                        txtStatus.setText("Status : Permintaan dikonfirmasi oleh manajer, sedang di proses oleh tim finance");
                    }else if(listForm.get(position).getStatus().equals("5")){
                        txtStatus.setText("Status : Permintaan ditolak oleh Manajer, form dibatalkan");
                        txtDesc.setText("Alasan ");
                        txtDescription.setText(listForm.get(position).getDescription());
                    }else if(listForm.get(position).getStatus().equals("6")){
                        txtStatus.setText("Status : Permintaan budget dikonfirmasi oleh tim finance.");
                        txtDescription.setText("Approve");
                        txtDescription.setTextColor(Color.parseColor("#ff99cc00"));
                    }else if(listForm.get(position).getStatus().equals("7")){
                        txtStatus.setText("Status : Menunggu konfirmasi dari Title C");
                    }else if(listForm.get(position).getStatus().equals("8")){
                        txtStatus.setText("Status : Permintaan ditolak oleh Title C, form dibatalkan");
                        txtDesc.setText("Alasan ");
                        txtDescription.setText(listForm.get(position).getDescription());
                    }else if(listForm.get(position).getStatus().equals("9")){
                        txtStatus.setText("Status : Permintaan dikonfirmasi oleh Title C");
                        txtDescription.setText("Approve");
                        txtDescription.setTextColor(Color.parseColor("#ff99cc00"));
                    }else if(listForm.get(position).getStatus().equals("99")){
                        txtStatus.setText("Status : Permintaan budget ditolak oleh tim finance, form dibatalkan");
                        txtDesc.setText("Alasan ");
                        txtDescription.setText(listForm.get(position).getDescription());
                    }else{
                        txtStatus.setText("Status : Tidak terdefinisikan");
                    }

                    builder.setView(view);
                    Button btnKembali = view.findViewById(R.id.btnKembali);
                    Button btnTransport = view.findViewById(R.id.btnTransport);
                    Button btnHotel = view.findViewById(R.id.btnHotel);

                    final AlertDialog alertDialog=builder.create();

                    btnKembali.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                        }
                    });
                    btnTransport.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(context,DetailImageActivity.class);
                            intent.putExtra("path", listForm.get(position).getTransport().toString());
                            context.startActivity(intent);
                        }
                    });
                    btnHotel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(context,DetailImageActivity.class);
                            intent.putExtra("path", listForm.get(position).getHotel().toString());
                            context.startActivity(intent);
                        }
                    });
                    alertDialog.show();
                }

            });
        }
    }

    void terimaAdmin(View view,String id,String Token, String status, String name, String email, String role, String nik) {
        ApiInterface apiInterface = ApiHelper.createService(ApiInterface.class, "Bearer " + Token);
        Call<SetListFormsModel> call = apiInterface.setFormAdmin(
                id,
                status,
                name,
                email,
                role
        );
        call.enqueue(new Callback<SetListFormsModel>() {
            @Override
            public void onResponse(Call<SetListFormsModel> call, Response<SetListFormsModel> response) {
                if (response.isSuccessful()) {
                    setNotifKaryawan(view,nik,Token);
//                    Toast.makeText(view.getContext(), "Berhasil Konfirmasi", Toast.LENGTH_SHORT).show();
//                    context.startActivity(new Intent(context, MainActivity.class));
                } else {
                    Toast.makeText(view.getContext(), "Gagal Konfirmasi", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<SetListFormsModel> call, Throwable t) {
                Toast.makeText(view.getContext(), "Gagal Konfirmasi", Toast.LENGTH_SHORT).show();

            }
        });
    }
    void tolakAdmin(View view,String id,String Token, String status,String name, String email, String role, String nik, String description) {
        ApiInterface apiInterface = ApiHelper.createService(ApiInterface.class, "Bearer " + Token);
        Call<SetListFormsModel> call = apiInterface.setFormAdminReason(
                id,
                status,
                name,
                email,
                role,
                description
        );
        call.enqueue(new Callback<SetListFormsModel>() {
            @Override
            public void onResponse(Call<SetListFormsModel> call, Response<SetListFormsModel> response) {
                if (response.isSuccessful()) {
                    setNotifKaryawan(view,nik,Token);
//                    Toast.makeText(view.getContext(), "Berhasil Konfirmasi", Toast.LENGTH_SHORT).show();
//                    context.startActivity(new Intent(context, MainActivity.class));
                } else {
                    Toast.makeText(view.getContext(), "Gagal Konfirmasi", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<SetListFormsModel> call, Throwable t) {
                Toast.makeText(view.getContext(), "Gagal Konfirmasi", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void setNotifKaryawan(View view,String nik,String Token) {
        ApiInterface apiInterface = ApiHelper.createService(ApiInterface.class, "Bearer " + Token);
        Call<NotifModel> call = apiInterface.setnotifkaryawan(nik);
        call.enqueue(new Callback<NotifModel>() {
            @Override
            public void onResponse(Call<NotifModel> call, Response<NotifModel> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(view.getContext(), "Berhasil Konfirmasi", Toast.LENGTH_SHORT).show();
                    context.startActivity(new Intent(context, MainActivity.class));
                } else {
                    Toast.makeText(view.getContext(), "Gagal Konfirmasi", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<NotifModel> call, Throwable t) {
                Toast.makeText(view.getContext(), "Gagal Konfirmasi", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return listForm.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtName, txtDates, txtUrgent, txtStatus;
        LinearLayout linearLayout;
        public MyViewHolder(View itemView) {
            super(itemView);
            txtStatus = (TextView) itemView.findViewById(R.id.txtStatus);
            txtName = (TextView) itemView.findViewById(R.id.txtName);
            txtDates = (TextView) itemView.findViewById(R.id.txtDates);
            txtUrgent = (TextView) itemView.findViewById(R.id.txtUrgent);
            linearLayout = itemView.findViewById(R.id.mark);
        }
    }
}
