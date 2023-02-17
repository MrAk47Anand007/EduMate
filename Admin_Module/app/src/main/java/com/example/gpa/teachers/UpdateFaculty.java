package com.example.gpa.teachers;

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
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class UpdateFaculty extends AppCompatActivity {
    private ImageView UpdateFacultyImage;
    private EditText UpdateFacultyName, UpdateFacultyEmail, UpdateFacultyPost;
    private Button UpdateBtn, DeleteBtn;


    private String name, email, post, image;
    private final int Req = 1;
    private Bitmap bitmap = null;
    private StorageReference storageReference;
    private DatabaseReference reference, dbref;
    private String downloadUrl, category, uniqueKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_faculty);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        reference = FirebaseDatabase.getInstance().getReference().child("Faculty");
        storageReference = FirebaseStorage.getInstance().getReference();

        name = getIntent().getStringExtra("name");
        email = getIntent().getStringExtra("email");
        image = getIntent().getStringExtra("image");
        post = getIntent().getStringExtra("post");

        uniqueKey = getIntent().getStringExtra("key");
        category = getIntent().getStringExtra("category");


        UpdateFacultyImage = findViewById(R.id.UpdateFacultyImage);
        UpdateFacultyName = findViewById(R.id.updateNameFaculty);
        UpdateFacultyEmail = findViewById(R.id.updateEmailFaculty);
        UpdateFacultyPost = findViewById(R.id.updatePostFaculty);
        UpdateBtn = findViewById(R.id.updateFacultyButton1);
        DeleteBtn = findViewById(R.id.deleteFacultyButton1);

        try {
            Picasso.get().load(image).into(UpdateFacultyImage);
        } catch (Exception e) {
            e.printStackTrace();
        }

        UpdateFacultyEmail.setText(email);
        UpdateFacultyName.setText(name);
        UpdateFacultyPost.setText(post);

        UpdateFacultyImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        UpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = UpdateFacultyName.getText().toString();
                email = UpdateFacultyEmail.getText().toString();
                post = UpdateFacultyPost.getText().toString();
                checkValidation();
            }
        });

        DeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData();
            }
        });


    }

    private void deleteData() {


        reference.child(category).child(uniqueKey).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                Toast.makeText(UpdateFaculty.this, "Faculty Deleted", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UpdateFaculty.this, UploadFaculty.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(UpdateFaculty.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkValidation() {

        if (name.isEmpty()) {
            UpdateFacultyName.setError("Empty");
            UpdateFacultyName.requestFocus();
        } else if (post.isEmpty()) {
            UpdateFacultyPost.setError("Empty");
            UpdateFacultyPost.requestFocus();
        } else if (email.isEmpty()) {
            UpdateFacultyEmail.setError("Empty");
            UpdateFacultyEmail.requestFocus();
        } else if (bitmap == null) {
            updateData(image);
        } else {
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
        uploadTask.addOnCompleteListener(UpdateFaculty.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
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
                                    updateData(downloadUrl);
                                }
                            });
                        }
                    });
                } else {

                    Toast.makeText(UpdateFaculty.this, "Something went wrong", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private void updateData(String s) {

        HashMap hp = new HashMap();
        hp.put("name", name);
        hp.put("post", post);
        hp.put("email", email);
        hp.put("image", s);


        reference.child(category).child(uniqueKey).updateChildren(hp).addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {

                Toast.makeText(UpdateFaculty.this, "Faculty Updated", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UpdateFaculty.this, UploadFaculty.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UpdateFaculty.this, "Something went wrong", Toast.LENGTH_SHORT).show();

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
            UpdateFacultyImage.setImageBitmap(bitmap);
        }
    }
}