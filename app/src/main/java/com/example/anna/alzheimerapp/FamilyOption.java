package com.example.anna.alzheimerapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class FamilyOption extends AppCompatActivity implements View.OnClickListener {
    private Button  buttonBrowseMembers, buttonDeleteOrEditMember, buttonAddMember;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_option);
        init();

    }
    public void init(){
        buttonBrowseMembers = (Button)findViewById(R.id.buttonBrowseMembers);
        buttonBrowseMembers.setOnClickListener(this);
        buttonAddMember = (Button)findViewById(R.id.buttonAddMember);
        buttonAddMember.setOnClickListener(this);
        buttonDeleteOrEditMember = (Button)findViewById(R.id.buttonDeleteOrEditMember);
        buttonDeleteOrEditMember.setOnClickListener(this);
    }



    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.buttonAddMember:
                Intent intent = new Intent(FamilyOption.this, FamilyAddMember.class);
                startActivity(intent);
                break;
            case R.id.buttonBrowseMembers:
                Intent intent2 = new Intent(FamilyOption.this, FamilyBrowseMembers.class);
                startActivity(intent2);
                break;
            case R.id.buttonDeleteOrEditMember:
                Intent intent3 = new Intent(FamilyOption.this, FamilyDeleteOrEditMember.class);
                startActivity(intent3);
                break;

        }
    }
}
