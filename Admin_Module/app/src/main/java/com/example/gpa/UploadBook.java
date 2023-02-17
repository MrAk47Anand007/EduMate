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

public class UploadBook extends AppCompatActivity {
    private CardView BookName;

    private EditText bookTitle;
    private Button uploadBookbtn;
    private final int Req = 1;
    private Uri bookData;
    private DatabaseReference dbreference;
    private StorageReference storageReference;
    String downloadUrl = "";
    private ProgressDialog pd;
    private TextView bookTextView;
    private String bookName, bookNameTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_book);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        dbreference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();
        pd = new ProgressDialog(this);

        bookTextView = findViewById(R.id.bookText);
        BookName = findViewById(R.id.SelectBook);
        bookTitle = findViewById(R.id.editBookText);
        uploadBookbtn = findViewById(R.id.uploadBookButton);
        BookName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
        uploadBookbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookNameTitle = bookTitle.getText().toString();
                if (bookNameTitle.isEmpty()) {
                    bookTitle.setError("Empty");
                    bookTitle.requestFocus();
                } else if (bookData == null) {
                    Toast.makeText(UploadBook.this, "Please Upload Book", Toast.LENGTH_SHORT).show();
                } else {
                    uploadBook();
                }

            }
        });
    }

    private void uploadBook() {
        pd.setTitle("Please wait....");
        pd.setMessage("Uploading Book....");
        pd.show();

        StorageReference reference = storageReference.child("Books/" + bookName + "-" + System.currentTimeMillis() + ".pdf");
        reference.putFile(bookData).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
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
                Toast.makeText(UploadBook.this, "Something went wrong", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void uploadData(String downloadUrl) {
        String uniquekey = dbreference.child("Books").push().getKey();

        HashMap data = new HashMap();
        data.put("bookTitle", bookNameTitle);
        data.put("bookUrl", downloadUrl);

        dbreference.child("Books").child(uniquekey).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                pd.dismiss();
                Toast.makeText(UploadBook.this, "Book Uploaded Sucessfully", Toast.LENGTH_SHORT).show();
                bookTitle.setText("");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(UploadBook.this, "Failed To Upload Book", Toast.LENGTH_SHORT).show();
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
            bookData = data.getData();


            if (bookData.toString().startsWith("content://")) {
                Cursor cursor = null;
                try {
                    cursor = UploadBook.this.getContentResolver().query(bookData, null, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        bookName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (bookData.toString().startsWith("file://")) {
                bookName = new File(bookData.toString()).getName();
            }
            bookTextView.setText(bookName);
        }
    }
}