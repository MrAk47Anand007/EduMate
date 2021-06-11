package com.example.gpastudents.submission;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.gpastudents.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

public class Upload_Submission extends AppCompatActivity {
    private CardView BookName;

    private EditText bookTitle,studName,studID;
    private Button uploadBookbtn;
    private final int Req = 1;
    private Uri bookData;
    private DatabaseReference dbreference,dbref;
    private StorageReference storageReference;
    String downloadUrl = "";
    private ProgressDialog pd;
    private TextView bookTextView;
    private String bookName, bookNameTitle,SubjectName,StudentName,StudentID;
    private Spinner spinner;
    ValueEventListener listener;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_submission);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


        dbref = FirebaseDatabase.getInstance().getReference("Submission");
        dbreference = FirebaseDatabase.getInstance().getReference("SubmissionOutput");
        storageReference = FirebaseStorage.getInstance().getReference();
        pd = new ProgressDialog(this);

        bookTextView = findViewById(R.id.bookText);
        BookName = findViewById(R.id.SelectBook);
        bookTitle = findViewById(R.id.editBookText);
        //subName = findViewById(R.id.subjectName);
        spinner = (Spinner) findViewById(R.id.spinnerSubmission);
        uploadBookbtn = findViewById(R.id.uploadBookButton);
        studID = findViewById(R.id.editpdfid);
        studName = findViewById(R.id.editpdfname);

        list = new ArrayList<String>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,list);
        spinner.setAdapter(adapter);


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
                SubjectName = spinner.getSelectedItem().toString();
                StudentName = studName.getText().toString();
                StudentID = studID.getText().toString();
                if (bookNameTitle.isEmpty()) {
                    bookTitle.setError("Empty");
                    bookTitle.requestFocus();
                }else if (StudentName.isEmpty()) {
                    studName.setError("Empty");
                    studName.requestFocus();
                }else if (StudentID.isEmpty()) {
                    studID.setError("Empty");
                    studID.requestFocus();
                } else if (bookData == null) {
                    Toast.makeText(Upload_Submission.this, "Please Upload Pdf", Toast.LENGTH_SHORT).show();
                } else {
                    uploadBook();
                }

            }
        });

        fetchData();
    }

    private void fetchData() {
        listener = dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1: snapshot.getChildren())
                {
                    String title1 = snapshot1.child("title1").getValue().toString();
                    list.add(title1);
                }
                adapter.notifyDataSetChanged();

            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }

    private void uploadBook() {
        pd.setTitle("Please wait....");
        pd.setMessage("Uploading Pdf....");
        pd.show();

        StorageReference reference = storageReference.child("SubmissionOutput/" + bookName + "-" + System.currentTimeMillis() + ".pdf");
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
                Toast.makeText(Upload_Submission.this, "Something went wrong", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void uploadData(String downloadUrl) {
        String uniquekey = dbreference.child(SubjectName).push().getKey();

        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MM-yy");
        String date = currentDate.format(calForDate.getTime());

        Calendar calForTime = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm a");
        String time = currentTime.format(calForTime.getTime());




        HashMap data = new HashMap();
        data.put("pdfTitle", bookNameTitle);
        data.put("date",date);
        data.put("time",time);
        data.put("pdfUrl", downloadUrl);
        data.put("SubjectName",SubjectName);
        data.put("StudName",StudentName);
        data.put("StudID",StudentID);

        dbreference.child(uniquekey).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                pd.dismiss();
                Toast.makeText(Upload_Submission.this, "Pdf Uploaded Sucessfully", Toast.LENGTH_SHORT).show();
                bookTitle.setText("");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(Upload_Submission.this, "Failed To Upload Pdf", Toast.LENGTH_SHORT).show();
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
                    cursor = Upload_Submission.this.getContentResolver().query(bookData, null, null, null, null);
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