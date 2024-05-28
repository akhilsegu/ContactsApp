package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class UpdateContactsActivity extends AppCompatActivity {

    private EditText name_1, number_1;
    private ImageView image_1;
    private Button update_btn;
    private DBHelper DB;
    String contactName, contactNumber;
    private ImageView contactImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_contacts);
        name_1 = findViewById(R.id.name_text_1);
        number_1 = findViewById(R.id.phone_number_1);
        update_btn = findViewById(R.id.update_btn1);
        DB = new DBHelper(UpdateContactsActivity.this);
        contactName = getIntent().getStringExtra("name");
        contactNumber = getIntent().getStringExtra("number");
        name_1.setText(contactName);
        number_1.setText(contactNumber);
        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DB.updateData(contactName, name_1.getText().toString(), number_1.getText().toString());
                Intent intent = new Intent(UpdateContactsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(this,MainActivity.class);
        startActivity(i);
        super.onBackPressed();
    }
}
