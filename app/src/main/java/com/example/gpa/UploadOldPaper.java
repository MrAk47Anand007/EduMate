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

public class UploadOldPaper extends AppCompatActivity {
    private CardView oldPaperName;

    private EditText paperTitle;
    private Button uploadPaperbtn;
    private final int Req = 1;
    private Uri paperData;
    private DatabaseReference dbreference;
    private StorageReference storageReference;
    String downloadUrl = "";
    private ProgressDialog pd;
    private TextView paperTextView;
    private String paperName, paperNameTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_old_paper);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        dbreference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();
        pd = new ProgressDialog(this);

        paperTextView = findViewById(R.id.oldPaperText);
        oldPaperName = findViewById(R.id.SelectOldPaper);
        paperTitle = findViewById(R.id.editOldPaperText);
        uploadPaperbtn = findViewById(R.id.uploadOldPaperButton);
        oldPaperName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
        uploadPaperbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paperNameTitle = paperTitle.getText().toString();
                if (paperNameTitle.isEmpty()) {
                    paperTitle.setError("Empty");
                    paperTitle.requestFocus();
                } else if (paperData == null) {
                    Toast.makeText(UploadOldPaper.this, "Please Upload Paper", Toast.LENGTH_SHORT).show();
                } else {
                    uploadBook();
                }

            }
        });
    }

    private void uploadBook() {
        pd.setTitle("Please wait....");
        pd.setMessage("Uploading Paper....");
        pd.show();

        StorageReference reference = storageReference.child("Papers/" + paperName);
        reference.putFile(paperData).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
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
                Toast.makeText(UploadOldPaper.this, "Something went wrong", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void uploadData(String downloadUrl) {
        String uniquekey = dbreference.child("Papers").push().getKey();

        HashMap data = new HashMap();
        data.put("paperTitle", paperNameTitle);
        data.put("paperUrl", downloadUrl);

        dbreference.child("Papers").child(uniquekey).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                pd.dismiss();
                Toast.makeText(UploadOldPaper.this, "Paper Uploaded Sucessfully", Toast.LENGTH_SHORT).show();
                paperTitle.setText("");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(UploadOldPaper.this, "Failed To Upload Paper", Toast.LENGTH_SHORT).show();
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
            paperData = data.getData();


            if (paperData.toString().startsWith("content://")) {
                Cursor cursor = null;
                try {
                    cursor = UploadOldPaper.this.getContentResolver().query(paperData, null, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        paperName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (paperData.toString().startsWith("file://")) {
                paperName = new File(paperData.toString()).getName();
            }
            paperTextView.setText(paperName);
        }
    }
}