package com.example.anna.alzheimerapp;

import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.support.v7.widget.Toolbar;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class FamilyBrowseMembers extends AppCompatActivity implements View.OnClickListener {
    private EditText editTextName, editTextRelationship, editTextSearch;
    private Button buttonSearch;
    ImageView imageView;
    FamilyDbHelper familyDbHelper;
    private MemberAdapter memberAdapter;
   ;
    private List<String> readmembers = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_browse_members);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        familyDbHelper = new FamilyDbHelper(this);
        SQLiteDatabase sqLiteDatabase = familyDbHelper.getReadableDatabase();
        memberAdapter = new MemberAdapter(this, familyDbHelper.readMembers(sqLiteDatabase, null));
        recyclerView.setAdapter(memberAdapter);

        init();
//        imageView = (ImageView)findViewById(R.id.imageView);
    }


    public void init() {
        buttonSearch = (Button) findViewById(R.id.buttonSearch);
        buttonSearch.setOnClickListener(this);
        editTextSearch = (EditText) findViewById(R.id.editTextSearch);
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextRelationship = (EditText) findViewById(R.id.editTextRelationship);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonSearch:
                RecyclerView recyclerView = findViewById(R.id.recyclerview);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                recyclerView.setLayoutManager(linearLayoutManager);
                familyDbHelper = new FamilyDbHelper(this);
                SQLiteDatabase sqLiteDatabase = familyDbHelper.getReadableDatabase();
                String searchName;
                if( editTextSearch.getText().toString().equals("")){
                    searchName = null;
                } else {
                    searchName = editTextSearch.getText().toString();
                }
                memberAdapter = new MemberAdapter(this, familyDbHelper.readMembers(sqLiteDatabase, searchName));
                recyclerView.setAdapter(memberAdapter);
                break;
            default:
        }
    }
}

