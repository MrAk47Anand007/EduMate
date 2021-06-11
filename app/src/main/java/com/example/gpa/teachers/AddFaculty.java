package com.example.gpa.teachers;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gpa.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;

public class AddFaculty extends AppCompatActivity {


    private ImageView selectFacultyImage;
    private EditText addFacultyName, addFacultyEmail, addFacultyPost;
    private Spinner addFacultyCategory;
    private Button addFacultyBtn;

    private final int Req = 1;
    private Bitmap bitmap;
    private String category;
    private String name, email, post, downloadUrl = "";
    private ProgressDialog pd;
    private StorageReference storageReference;
    private DatabaseReference reference, dbref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_faculty);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        selectFacultyImage = findViewById(R.id.addfacultyImage);
        addFacultyName = findViewById(R.id.editFacultyText);
        addFacultyEmail = findViewById(R.id.editFacultyEmail);
        addFacultyPost = findViewById(R.id.editFacultyPost);
        addFacultyCategory = findViewById(R.id.Post_category);
        addFacultyBtn = findViewById(R.id.uploadFacultyButton);

        pd = new ProgressDialog(this);

        reference = FirebaseDatabase.getInstance().getReference().child("Faculty");
        storageReference = FirebaseStorage.getInstance().getReference();

        String[] items = new String[]{"Select Department", "Information Technology", "Computer Engineering"};
        addFacultyCategory.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items));
        addFacultyCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = addFacultyCategory.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        selectFacultyImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        addFacultyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation();
            }
        });


    }

    private void checkValidation() {
        name = addFacultyName.getText().toString();
        email = addFacultyEmail.getText().toString();
        post = addFacultyPost.getText().toString();

        if (name.isEmpty()) {
            addFacultyName.setError("Empty");
            addFacultyName.requestFocus();
        } else if (email.isEmpty()) {
            addFacultyEmail.setError("Empty");
            addFacultyEmail.requestFocus();
        } else if (post.isEmpty()) {
            addFacultyPost.setError("Empty");
            addFacultyPost.requestFocus();
        } else if (category.equals("Select Department")) {
            Toast.makeText(this, "Please Select the department", Toast.LENGTH_SHORT).show();
        } else if (bitmap == null) {
            pd.setMessage("Uploading");
            pd.show();
            insertData();
        } else {
            pd.setMessage("Uploading");
            pd.show();
            uploadImage();
        }


    }

    private void uploadImage() {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] finalimg = baos.toByteArray();
        final StorageReference filePath;
        filePath = storageReference.child("Faculty").child(finalimg + "jpg");
        final UploadTask uploadTask = filePath.putBytes(finalimg);
        uploadTask.addOnCompleteListener(AddFaculty.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    downloadUrl = String.valueOf(uri);
                                    insertData();
                                }
                            });
                        }
                    });
                } else {
                    pd.dismiss();
                    Toast.makeText(AddFaculty.this, "Something went wrong", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }


    private void insertData() {


        dbref = reference.child(category);
        final String uniquekey = dbref.push().getKey();

        facultyData FacultyData = new facultyData(name, email, post, downloadUrl, uniquekey);

        dbref.child(uniquekey).setValue(FacultyData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                pd.dismiss();
                Toast.makeText(AddFaculty.this, "Faculty Details Uploaded", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(AddFaculty.this, "Something went wrong", Toast.LENGTH_SHORT).show();

            }
        });
    }


    private void openGallery() {

        Intent pickimg = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickimg, Req);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Req && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            selectFacultyImage.setImageBitmap(bitmap);
        }
    }

}