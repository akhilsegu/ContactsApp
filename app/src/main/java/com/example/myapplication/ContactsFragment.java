package com.example.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class ContactsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private static final int REQUEST_CALL_PHONE = 101;
    private Context context;
    RecyclerView recyclerView;
    ArrayList<String> namelist, numberlist;
    DBHelper DB;
    MyAdapter adapter;

    public ContactsFragment() {
        // Required empty public constructor
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

        View view=inflater.inflate(R.layout.fragment_contacts, container, false);
        DB = new DBHelper(view.getContext());
        namelist = new ArrayList<>();
        numberlist = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recyclerview);
        adapter = new MyAdapter(getActivity(), namelist, numberlist);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        displayData();
        return view;
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

    public static ContactsFragment newInstance(String param1, String param2) {
        ContactsFragment fragment = new ContactsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

}
