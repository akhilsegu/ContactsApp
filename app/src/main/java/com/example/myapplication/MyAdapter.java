package com.example.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private Context context;
    private ArrayList name_id,number_id;
    // private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 101;
    private DBHelper dbHelper;

    public MyAdapter(Context context, ArrayList name_id, ArrayList number_id) {
        this.context = context;
        this.name_id = name_id;
        this.number_id = number_id;
        dbHelper=new DBHelper(context);
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.userentry, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name_id.setText(String.valueOf(name_id.get(position)));
        holder.number_id.setText(String.valueOf(number_id.get(position)));

    /*    byte[] photoByteArray= dbHelper.getPhotoById(String.valueOf(name_id.get(position)));
        if(photoByteArray!=null){
            Bitmap photobitmap= BitmapFactory.decodeByteArray(photoByteArray, 0, photoByteArray.length);
            holder.photoImageview.setImageBitmap(photobitmap);
        }*/

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Do you want to make a call")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //    if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE)
                                //           != PackageManager.PERMISSION_GRANTED) {
                                //     ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_CALL_PHONE);
                                // } else {
                                initiateCall(position);
                                // }
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                Toast.makeText(context.getApplicationContext(), "you choose not to make a call",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.setTitle("Mobile calling");
                alert.show();
            }
        });

    }



    void initiateCall(int position) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + number_id.get(position)));
        context.startActivity(callIntent);
        Toast.makeText(context, "Calling " + name_id.get(position), Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return name_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name_id, number_id;
        ImageView photoImageview;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name_id = itemView.findViewById(R.id.contact_name1);
            number_id = itemView.findViewById(R.id.phonenumber_1);
            photoImageview=itemView.findViewById(R.id.photo_contact);
        }
    }

}
