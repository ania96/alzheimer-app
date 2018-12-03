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
import android.os.Environment;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

public class FamilyAddMember extends AppCompatActivity implements View.OnClickListener {
    FamilyDbHelper familyDbHelper;
    private Button buttonAddPhoto, buttonConfirmMember;
    private TextView textViewRelationship, textViewName;
    private EditText editTextName, editTextRelationship;
    private ImageView imageViewAddedPhoto;
    private MemberAdapter memberAdapter;
    static final int REQUEST_CAMERA = 1;
    static final int SELECT_FILE = 2;
    File image_file;
    String imageFilePath;
    String path;
    Bitmap chosenImage;

    //creata a file in internal storage
    @RequiresApi(api = Build.VERSION_CODES.N)
    private String saveToInternalStorage(Bitmap bitmapImage, long id) throws IOException {
        Log.d("FOLDER", "caling function!");
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);


        //zapisanie zdjecia do pliku
        File mypath = new File(directory, id + ".jpg");
        path = mypath.getAbsolutePath();
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath, true);

            ExifInterface ei = new ExifInterface(fos.getFD());
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED);

            Bitmap rotatedBitmap = null;
            switch (ExifInterface.ORIENTATION_ROTATE_90) {

                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotatedBitmap = rotateImageAngle(bitmapImage, 90);
                    break;

                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotatedBitmap = rotateImageAngle(bitmapImage, 180);
                    break;

                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotatedBitmap = rotateImageAngle(bitmapImage, 270);
                    break;

                case ExifInterface.ORIENTATION_NORMAL:
                default:
                    rotatedBitmap = bitmapImage;
            }
            // Use the compress method on the BitMap object to write image to the OutputStream
            rotatedBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return directory.getAbsolutePath();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_add_member);
        familyDbHelper = new FamilyDbHelper(this);

        init();
    }

    public void init() {
        buttonAddPhoto = (Button) findViewById(R.id.buttonAddPhoto);
        buttonAddPhoto.setOnClickListener(this);
        buttonConfirmMember = (Button) findViewById(R.id.buttonConfirmMember);
        buttonConfirmMember.setOnClickListener(this);
        textViewRelationship = (TextView) findViewById(R.id.textViewRelationship);
        textViewName = (TextView) findViewById(R.id.textViewName);
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextRelationship = (EditText) findViewById(R.id.editTextRelationship);
        imageViewAddedPhoto = (ImageView) findViewById(R.id.imageViewAddedPhoto);
    }

    public void addNewMember() {

        if (editTextName.getText().toString().matches("") || editTextRelationship.getText().toString().matches("") || imageViewAddedPhoto.getDrawable() == null) {
            Toast.makeText(this, "Uzupełnij wszystkie wymagane pola", Toast.LENGTH_SHORT).show();
            return;
        } else {
            String name = editTextName.getText().toString();
            String relationship = editTextRelationship.getText().toString();


            ContextWrapper cw = new ContextWrapper(getApplicationContext());
            // path to /data/data/yourapp/app_data/imageDir
            File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
            File mypath = new File(directory, "TEST.jpg");
            path = mypath.getAbsolutePath();
            String image = imageViewAddedPhoto.toString();
            imageViewAddedPhoto.setImageURI(null);
//            imageViewAddedPhoto.setImageURI(imgUri);

            SQLiteDatabase sqLiteDatabase = familyDbHelper.getWritableDatabase();
            long id = familyDbHelper.addMember(name, relationship, sqLiteDatabase);

            try {
                saveToInternalStorage(chosenImage, id);
            } catch (IOException e) {
                e.printStackTrace();
            }


            Cursor cursor = sqLiteDatabase.query(FamilyContract.FamilyEntry.TABLE_NAME,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null);
            ArrayList<String> existentColumns = new ArrayList<>(Arrays.asList(cursor.getColumnNames()));
            cursor.close();
            for (String colum : existentColumns) {
                Log.d("BASE", colum);
            }
            Toast.makeText(FamilyAddMember.this, "Dodano nowego członka rodziny", Toast.LENGTH_LONG).show();
            FamilyBrowseMembers fbm = new FamilyBrowseMembers();

            cursor = sqLiteDatabase.query(FamilyContract.FamilyEntry.TABLE_NAME,
                    new String[]{FamilyContract.FamilyEntry.NAME, FamilyContract.FamilyEntry.RELATIONSHIP},
                    null, null, null, null, null);

            cursor.moveToFirst();
            Log.d("name", cursor.getString(cursor.getColumnIndex(FamilyContract.FamilyEntry.NAME)));
            long l = DatabaseUtils.queryNumEntries(sqLiteDatabase, FamilyContract.FamilyEntry.TABLE_NAME);
            Log.d("COUNT", Long.toString(l));

            imageViewAddedPhoto.setImageDrawable(null);
            editTextName.getText().clear();
            editTextRelationship.getText().clear();
        }

    }

    public static Bitmap rotateImageAngle(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }


    //metoda odpowiedzialna za sposob w jaki ma byc dodane zdjecie
    public void chooseMetodToChoosePhoto() {
        final CharSequence[] items = {"Zrób zdjęcie", "Wybierz zdjęcie z galerii", "Zamknij"};

        AlertDialog.Builder builder = new AlertDialog.Builder(FamilyAddMember.this);
        builder.setTitle("Dodaj zdjęcie");
        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (items[i].equals("Zrób zdjęcie")) {
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(takePictureIntent, REQUEST_CAMERA);
//                        File file = getFile();
//                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));

                    }
//
                } else if (items[i].equals("Wybierz zdjęcie z galerii")) {
                    Intent choosePictureIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                    choosePictureIntent.setType("image/*");
                    startActivityForResult(choosePictureIntent, SELECT_FILE);

                } else if (items[i].equals("Zamknij")) {
                    dialogInterface.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) {
                Bundle bundle = data.getExtras();
                final Bitmap bmp = (Bitmap) bundle.get("data");
                imageViewAddedPhoto.setImageBitmap(bmp);

                chosenImage = bmp;

            }
            if (requestCode == SELECT_FILE) {
                try {
                    final Uri imageUri = data.getData();
                    final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    imageViewAddedPhoto.setImageBitmap(selectedImage);
//                    imageViewAddedPhoto.s
                    chosenImage = selectedImage;
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
//                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
                }

            } else {
//                Toast.makeText(this, "You haven't picked Image", Toast.LENGTH_LONG).show();
            }
//                Uri uri = data.getData();
//                String src = getPath(uri);
//
//                File imgFile = new File(src);
//
//                if(imgFile.exists()){
//                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
//                    imageViewAddedPhoto.setImageBitmap(myBitmap);
//                    chosenImage = myBitmap;
//                    Toast.makeText(FamilyAddMember.this, "Zapisano", Toast.LENGTH_LONG).show();
//
//                }

//
//
//
//                    imageViewAddedPhoto.setImageBitmap(myBitmap);
//    //                saveToInternalStorage(bmp);
//                    chosenImage = myBitmap;
//                    Toast.makeText(FamilyAddMember.this, "Zapisano", Toast.LENGTH_LONG).show();
//


        }
    }

    public void rotateImage(Bitmap bitmap) {
        ExifInterface exifInterface = null;
        int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            default:
        }
//        Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap,0,0, bitmap.getWidth(), bitmap.getHeight(),matrix, true);
        imageViewAddedPhoto.setImageBitmap(bitmap);
    }

    public String getPath(Uri uri) {

        String path = null;
        String[] projection = {MediaStore.Files.FileColumns.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);

        if (cursor == null) {
            path = uri.getPath();
        } else {
            cursor.moveToFirst();
            int column_index = cursor.getColumnIndexOrThrow(projection[0]);
            path = cursor.getString(column_index);
            cursor.close();
        }

        return ((path == null || path.isEmpty()) ? (uri.getPath()) : path);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonAddPhoto:
                chooseMetodToChoosePhoto();
                break;
            case R.id.buttonConfirmMember:
                addNewMember();
                break;

        }
    }
}
