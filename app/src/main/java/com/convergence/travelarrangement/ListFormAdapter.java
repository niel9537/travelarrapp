package com.convergence.travelarrangement;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.convergence.travelarrangement.model.ListForm;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListFormAdapter extends RecyclerView.Adapter<ListFormAdapter.MyViewHolder> {
    List<ListForm> listForm;
    Context context;



    public ListFormAdapter(List<ListForm> listForm, Context context) {
        this.listForm = listForm;
        this.context = context;
    }

    @Override
    public ListFormAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listform, parent, false);
        ListFormAdapter.MyViewHolder mViewHolder = new ListFormAdapter.MyViewHolder(mView);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(ListFormAdapter.MyViewHolder holder, final int position) {
        String SHARED_PREF_NAME = "mypref";
        String KEY_ROLE = "role";
        String role = "";
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        role = preferences.getString(KEY_ROLE, null);
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
        }else if(listForm.get(position).getStatus().equals("7")){
            holder.txtStatus.setText("Status : Menunggu konfirmasi dari Title C");
            holder.linearLayout.setBackgroundResource(R.color.yellowanedot);
        }else if(listForm.get(position).getStatus().equals("8")){
            holder.txtStatus.setText("Status : Permintaan ditolak oleh Title C, form dibatalkan");
            holder.linearLayout.setBackgroundResource(R.color.redanedot);
        }else if(listForm.get(position).getStatus().equals("9")){
            holder.txtStatus.setText("Status : Permintaan dikonfirmasi oleh Title C");
        }else if(listForm.get(position).getStatus().equals("99")){
            holder.txtStatus.setText("Status : Permintaan budget ditolak oleh tim finance, form dibatalkan");
            holder.linearLayout.setBackgroundResource(R.color.redanedot);
        }else{
            holder.txtStatus.setText("Status : Tidak terdefinisikan");
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder=new AlertDialog.Builder(v.getContext());
                View view=LayoutInflater.from(context).inflate(R.layout.modal_detailform,null);
                TextView txtCreateat = (TextView) view.findViewById(R.id.txtCreateat);
                txtCreateat.setText(listForm.get(position).getCreateat());
                TextView txtDesc = (TextView) view.findViewById(R.id.txtDesc);
                TextView txtDescription = (TextView) view.findViewById(R.id.txtDescription);
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

    @Override
    public int getItemCount() {
        return listForm.size();
    }

    public void setFilter(List<ListForm> newsArrayList) {
        listForm.clear();
        listForm.addAll(newsArrayList);
        notifyDataSetChanged();
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
