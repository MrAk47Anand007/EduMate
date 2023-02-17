package com.example.gpa;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.HashMap;
import java.util.Objects;

public class UploadCarriculum extends AppCompatActivity {

    private CardView Curriculum;

    private EditText carriculumTitle;
    private Button uploadcarriculumbtn;
    private final int Req = 1;
    private Uri carriculumData;
    private DatabaseReference dbreference;
    private StorageReference storageReference;
    String downloadUrl = "";
    private ProgressDialog pd;
    private TextView carriculumTextView;
    private String carriculumName, carriculumNameTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_carriculum);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        dbreference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();
        pd = new ProgressDialog(this);

        carriculumTextView = findViewById(R.id.carriculumText);
        Curriculum = findViewById(R.id.SelectCarriculum);
        carriculumTitle = findViewById(R.id.editCarriculumText);
        uploadcarriculumbtn = findViewById(R.id.uploadCarriculumButton);
        Curriculum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
        uploadcarriculumbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carriculumNameTitle = carriculumTitle.getText().toString();
                if (carriculumNameTitle.isEmpty()) {
                    carriculumTitle.setError("Empty");
                    carriculumTitle.requestFocus();
                } else if (carriculumData == null) {
                    Toast.makeText(UploadCarriculum.this, "Please Upload Curriculum", Toast.LENGTH_SHORT).show();
                } else {
                    uploadcarriculum();
                }

            }
        });
    }

    private void uploadcarriculum() {
        pd.setTitle("Please wait....");
        pd.setMessage("Uploading Curriculum....");
        pd.show();

        StorageReference reference = storageReference.child("Curriculum/" + carriculumName + "-" + System.currentTimeMillis() + ".pdf");
        reference.putFile(carriculumData).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete()) ;

                Uri uri = uriTask.getResult();
                uploadData(String.valueOf(uri));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(UploadCarriculum.this, "Something went wrong", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void uploadData(String downloadUrl) {
        String uniquekey = dbreference.child("Books").push().getKey();

        HashMap data = new HashMap();
        data.put("carriculumTitle", carriculumNameTitle);
        data.put("carriculumUrl", downloadUrl);

        dbreference.child("Curriculum").child(uniquekey).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                pd.dismiss();
                Toast.makeText(UploadCarriculum.this, "Curriculum Uploaded Sucessfully", Toast.LENGTH_SHORT).show();
                carriculumTitle.setText("");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(UploadCarriculum.this, "Failed To Upload Curriculum", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("application/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Pdf File"), Req);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Req && resultCode == RESULT_OK) {
            carriculumData = data.getData();


            if (carriculumData.toString().startsWith("content://")) {
                Cursor cursor = null;
                try {
                    cursor = UploadCarriculum.this.getContentResolver().query(carriculumData, null, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        carriculumName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (carriculumData.toString().startsWith("file://")) {
                carriculumName = new File(carriculumData.toString()).getName();
            }
            carriculumTextView.setText(carriculumName);
        }
    }
}