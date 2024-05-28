package com.example.myapplication;
//not using this
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Userlist extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<String> namelist,numberlist;
    ArrayList<Bitmap> imglist;
    DBHelper DB;
    MyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DB = new DBHelper(this);

        namelist = new ArrayList<>();
        numberlist = new ArrayList<>();
        imglist=new ArrayList<>();
        recyclerView=findViewById(R.id.recyclerview);
        adapter=new MyAdapter(this,namelist, numberlist);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        displayData();
    }

    private void displayData() {
        Cursor cursor=DB.getData();

        if(cursor.getPosition()==0){
           // Toast.makeText(this, "No Contacts saved in your DB", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            while (cursor.moveToNext()){
                namelist.add(cursor.getString(0));
                numberlist.add(cursor.getString(1));
            }
        }
    }
}
