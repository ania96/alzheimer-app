package com.example.anna.alzheimerapp;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
public class MemberAdapterToEdit  extends RecyclerView.Adapter<MemberAdapterToEdit.MemberViewHolder> {
    private Context mContext;
    private Cursor mCursor;
    private List<String> readMembers;
    private FamilyDbHelper familyDbHelper;

    public MemberAdapterToEdit(Context context, Cursor cursor){
        mContext=context;
        mCursor=cursor;
    }
    public class MemberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView nameText;
        public TextView relationshipText;
        public ImageView imageView;
        public Button buttonDelete;
        private long id;

        public void setId(long id) {
            this.id = id;
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.buttonDeleteMember:
                    deleteMember();
                    break;
            }
        }
        public void deleteMember(){
            familyDbHelper = new FamilyDbHelper(mContext);
            SQLiteDatabase sqLiteDatabase = familyDbHelper.getReadableDatabase();
            familyDbHelper.deleteMemberById(id, sqLiteDatabase);
            Toast.makeText(mContext, "Krewny został usunięty", Toast.LENGTH_SHORT).show();
            ContextWrapper cw = new ContextWrapper(mContext);
            File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
            File file = new File(directory, id + ".jpg");
            boolean deleted = file.delete();
        }
        public MemberViewHolder(@NonNull View itemView) {
            super(itemView);

            nameText = itemView.findViewById(R.id.memberName);
            relationshipText = itemView.findViewById(R.id.memberRelationship);
            imageView = itemView.findViewById(R.id.imageView);
            buttonDelete= itemView.findViewById(R.id.buttonDeleteMember);
            buttonDelete.setOnClickListener(this);
        }
    }
    @NonNull
    @Override
    public MemberAdapterToEdit.MemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater= LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.member_item_to_edit, parent, false);
        return new MemberAdapterToEdit.MemberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberAdapterToEdit.MemberViewHolder holder, int position) {
        if(!mCursor.moveToPosition(position)){
            return;
        }
        String name = mCursor.getString(mCursor.getColumnIndex(FamilyContract.FamilyEntry.NAME));
        String relationship = mCursor.getString(mCursor.getColumnIndex(FamilyContract.FamilyEntry.RELATIONSHIP));
        long id = mCursor.getLong(mCursor.getColumnIndex(FamilyContract.FamilyEntry.ID));
        holder.setId(id);

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
        return mCursor.getCount();
    }
    public void updateList(List<String> newList){
        readMembers = new ArrayList<>();
        readMembers.addAll(newList);
        notifyDataSetChanged();
    }
}

