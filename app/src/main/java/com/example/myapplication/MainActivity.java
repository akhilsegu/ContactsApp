package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CALL_PHONE = 101;
    private Context context;
    RecyclerView recyclerView;
    ArrayList<String> namelist, numberlist;
    DBHelper DB;
    MyAdapter adapter;
    private ItemTouchHelper itemTouchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        DB = new DBHelper(this);
        checkPermissions();

        namelist = new ArrayList<>();
        numberlist = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerview);
        adapter = new MyAdapter(this, namelist, numberlist);
        recyclerView.setAdapter(adapter);
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                if (direction == 4) {
                    int id = viewHolder.getAdapterPosition();
                    DB.deleteItem(namelist.get(viewHolder.getAdapterPosition()));
                    numberlist.remove(viewHolder.getAdapterPosition());
                    namelist.remove(viewHolder.getAdapterPosition());
                    Toast.makeText(context, "Deleted ", Toast.LENGTH_SHORT).show();
                    adapter.notifyItemRemoved(id);
                }
                if (direction == 8) {
                    Intent intent = new Intent(context, UpdateContactsActivity.class);
                    intent.putExtra("name", namelist.get(viewHolder.getAdapterPosition()));
                    intent.putExtra("number",numberlist.get(viewHolder.getAdapterPosition()));
                    startActivity(intent);
                }
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                final int DIRECTION_LEFT = 0;
                final int DIRECTION_RIGHT = 1;
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE && isCurrentlyActive) {
                    int direction = dX > 0 ? DIRECTION_RIGHT : DIRECTION_LEFT; //negative x -left,postive x-right

                    int absoluteDisplacement = Math.abs((int) dX);
                    Paint paint = new Paint();
                    paint.setColor(Color.RED);
                    paint.setTextSize(60);
                    Paint paint1 = new Paint();
                    paint1.setColor(Color.BLACK);
                    paint1.setTextSize(60);
                    switch (direction) {
                        case DIRECTION_LEFT:
                            View itemview = viewHolder.itemView;
                            ColorDrawable bg = new ColorDrawable();
                            bg.setColor(Color.BLUE);
                            int yPos = (int) ((itemview.getTop() + itemview.getHeight() / 2)  - ((paint.descent() + paint.ascent()) / 2));
                            bg.setBounds(itemview.getLeft(), itemview.getTop(), itemview.getRight(), itemview.getBottom());
                            bg.draw(c);
                            String text = "Delete";
                            c.drawText(text, itemview.getRight()-600,  yPos, paint);
                            break;
                        case DIRECTION_RIGHT:
                            View itemview1 = viewHolder.itemView;
                            ColorDrawable bg1 = new ColorDrawable();
                            bg1.setColor(Color.RED);
                            int yPos1 = (int) ((itemview1.getTop() + itemview1.getHeight() / 2)  - ((paint.descent() + paint.ascent()) / 2));
                            bg1.setBounds(itemview1.getLeft(), itemview1.getTop(), itemview1.getRight(), itemview1.getBottom());
                            bg1.draw(c);
                            String text1 = "Update";
                            c.drawText(text1, itemview1.getRight()-600,  yPos1, paint1);
                            break;
                    }
                }

            }
        };
        itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        displayData();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL_PHONE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(context, "Permission allowed to make calls", Toast.LENGTH_SHORT).show();

            } else {
                // Permission denied, show a message or handle it accordingly
                Toast.makeText(context, "Permission denied to make calls,Allow them from settings when required", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_contact, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.menu_add_contacts) {
            addContacts();
            return true;
        }
        if(itemId==R.id.menu_to_fragment){
            displayFragment();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void displayFragment() {
        Intent i=new Intent(context,DummyActivity.class);
        startActivity(i);
    }

    private void addContacts() {
        Intent saveIntent = new Intent(context, SaveContactsActivity.class);
        startActivity(saveIntent);
    }

    private void displayData() {
        Cursor cursor = DB.getData();

        if (cursor.getPosition() == 0) {
            // Toast.makeText(this, "No Contacts saved in your DB", Toast.LENGTH_SHORT).show();
            return;
        } else {
            while (cursor.moveToNext()) {
                namelist.add(cursor.getString(0));
                numberlist.add(cursor.getString(1));
                //    byte[] imageBytes=cursor.getBlob(2);
                //   Bitmap objectBitmap= BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);

            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL_PHONE);
        }
    }
}