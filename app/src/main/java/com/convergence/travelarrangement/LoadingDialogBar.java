package com.convergence.travelarrangement;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.widget.TextView;

public class LoadingDialogBar {
    Context context;
    Dialog dialog;

    public LoadingDialogBar(Context context){
        this.context = context;
    }

    public void showDialog(String title){
        dialog=new Dialog(context);
        dialog.setContentView(R.layout.dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView titleLoading = dialog.findViewById(R.id.titleLoading);
        titleLoading.setText(title);
        dialog.create();
        dialog.show();
    }

    public void showAlert(String message){
        dialog=new Dialog(context);
        dialog.setContentView(R.layout.alert);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView titleLoading = dialog.findViewById(R.id.titleLoading);
        titleLoading.setText(message);
        dialog.create();
        dialog.show();
        Handler handler = null;
        handler = new Handler();
        handler.postDelayed(new Runnable(){
            public void run(){
                dialog.cancel();
                dialog.dismiss();
            }
        }, 5000);
    }

    public void hideDialog(){
        dialog.dismiss();
    }

}
