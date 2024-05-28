package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Blob;

public class SaveContactsActivity extends AppCompatActivity {
    private static final int SELECT_PICTURE = 101;
    Button saveButton, cancelButton, insert_image_btn;
    EditText name, number;
    ImageView insert_image;
    boolean isAllFieldsChecked = false;

    private byte[] selectedphotobytearray;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_contacts);
        name = findViewById(R.id.name_text);
        number = findViewById(R.id.phone_number);
        saveButton = findViewById(R.id.savebutton);
        cancelButton = findViewById(R.id.cancelbutton);
        insert_image_btn = findViewById(R.id.insert_img);
        insert_image = findViewById(R.id.insert_image_view);
        DB = new DBHelper(this);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAllFieldsChecked = CheckAllFields();
                if (isAllFieldsChecked) {
                    addContact();
                }
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelContact();
            }
        });
        insert_image_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });
    }

    private void addContact() {
        String nameTxt = name.getText().toString();
        String numTxt = number.getText().toString();
        Boolean checkinsertdata = DB.insertuserdata(nameTxt, numTxt );
        if (checkinsertdata == true) {
            Toast.makeText(SaveContactsActivity.this, "Saved Contact", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(SaveContactsActivity.this, "Unable to save Contact", Toast.LENGTH_SHORT).show();
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void cancelContact() {
        Toast.makeText(SaveContactsActivity.this, "No contact saved as you pressed cancel", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    void imageChooser() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                    insert_image.setImageBitmap(bitmap);
                    selectedphotobytearray = getByteArrayFromBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private byte[] getByteArrayFromBitmap(Bitmap bitmap){
        ByteArrayOutputStream  byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream);
        byte[] bytesImage = byteArrayOutputStream.toByteArray();
        return bytesImage;
    }

    private boolean CheckAllFields() {
        if (name.length() == 0) {
            name.setError("This field is required");
            return false;
        }

        if (number.length() == 0) {
            number.setError("This field is required");
            return false;
        }

        return true;
    }

}