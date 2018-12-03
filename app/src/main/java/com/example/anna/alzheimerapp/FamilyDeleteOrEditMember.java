package com.example.anna.alzheimerapp;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FamilyDeleteOrEditMember extends AppCompatActivity implements View.OnClickListener{
    private EditText editTextName, editTextRelationship, editTextSearch;
    private Button buttonSearch, buttonDelete;
    private ImageButton imageButtonDelete;
    ImageView imageView;
    FamilyDbHelper familyDbHelper;
    private MemberAdapterToEdit memberAdapterToEdit;
    private List<String> readmembers = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_delete_or_edit_member);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        familyDbHelper = new FamilyDbHelper(this);
        SQLiteDatabase sqLiteDatabase = familyDbHelper.getReadableDatabase();
        memberAdapterToEdit = new MemberAdapterToEdit(this, familyDbHelper.readMembers(sqLiteDatabase, null));
        recyclerView.setAdapter(memberAdapterToEdit);

        init();
//        imageView = (ImageView)findViewById(R.id.imageView);
    }


    public void init() {
        buttonDelete = (Button)findViewById(R.id.buttonDelete);
        buttonDelete.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonDelete:
                Toast.makeText(this, "lol", Toast.LENGTH_SHORT).show();

                break;
        }

    }

//
//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.buttonSearch:
//                RecyclerView recyclerView = findViewById(R.id.recyclerview);
//                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//                recyclerView.setLayoutManager(linearLayoutManager);
//                familyDbHelper = new FamilyDbHelper(this);
//                SQLiteDatabase sqLiteDatabase = familyDbHelper.getReadableDatabase();
//                String searchName;
//                if( editTextSearch.getText().toString().equals("")){
//                    searchName = null;
//                } else {
//                    searchName = editTextSearch.getText().toString();
//                }
//                memberAdapter = new MemberAdapter(this, familyDbHelper.readMembers(sqLiteDatabase, searchName));
//                recyclerView.setAdapter(memberAdapter);
//                break;
//            default:
//        }
//    }
}

