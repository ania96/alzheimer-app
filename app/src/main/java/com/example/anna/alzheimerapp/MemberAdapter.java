package com.example.anna.alzheimerapp;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.MemberViewHolder>{
    private Context mContext;
    private Cursor mCursor;
    private List<String> readMembers;

    public MemberAdapter(Context context, Cursor cursor){
        mContext=context;
        mCursor=cursor;

    }

    public class MemberViewHolder extends RecyclerView.ViewHolder{

        public TextView nameText;
        public TextView relationshipText;
        public ImageView imageView;


        public MemberViewHolder(@NonNull View itemView) {
            super(itemView);

            //szukamy textviews, za pomoca których beda wyswietlane dane w pojedynczym rzedzie w recyclerview
            nameText = itemView.findViewById(R.id.memberName);
            relationshipText = itemView.findViewById(R.id.memberRelationship);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }



    @NonNull
    @Override
    public MemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater= LayoutInflater.from(mContext);
        //wyswietlamy naszego nowego czlonka rodziny w recyclerview?
        View view = inflater.inflate(R.layout.member_item, parent, false);
        return new MemberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberViewHolder holder, int position) {
        if(!mCursor.moveToPosition(position)){
            return;
        }
        String name = mCursor.getString(mCursor.getColumnIndex(FamilyContract.FamilyEntry.NAME));
        String relationship = mCursor.getString(mCursor.getColumnIndex(FamilyContract.FamilyEntry.RELATIONSHIP));
//        String image = mCursor.getString(mCursor.getColumnIndex(FamilyContract.FamilyEntry.IMAGE));
        long id = mCursor.getLong(mCursor.getColumnIndex(FamilyContract.FamilyEntry.ID));
        //sciezka do odczytania zdjecia
        File imgFile = new File("data/data/com.example.anna.alzheimerapp/app_imageDir/"+ id + ".jpg");

        if(imgFile.exists()){

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            holder.imageView.setImageBitmap(myBitmap);

        }
        holder.nameText.setText(name);
        holder.relationshipText.setText(relationship);
    }

    @Override
    public int getItemCount() {
        //chcemy zwrocic tak duzo elementów, jak mamy w BD
        return mCursor.getCount();
    }
    public void updateList(List<String> newList){
        readMembers = new ArrayList<>();
        readMembers.addAll(newList);
        notifyDataSetChanged();

    }

}
