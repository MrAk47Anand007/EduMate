package com.example.gpa.submissionView;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.gpa.R;

import java.io.File;

public class MarksActivity extends AppCompatActivity {

    private EditText presentymarks,performancemarks;
    private Button button;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marks);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);

        presentymarks= findViewById(R.id.Submissionpresentymarks);
        performancemarks = findViewById(R.id.SubmissionPerMarks);
        button = findViewById(R.id.excelbutton);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (presentymarks.getText().toString().isEmpty() || performancemarks.getText().toString().isEmpty())
                    {
                        presentymarks.setError("Error");
                        performancemarks.setError("Error");
                        presentymarks.requestFocus();
                        performancemarks.requestFocus();
                    }
                    else {

                        Toast.makeText(MarksActivity.this,"Marks is"+presentymarks.getText().toString(),Toast.LENGTH_SHORT).show();
                        ExcelFile();

                    }


            }
        });

    }

    private void ExcelFile() {
        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File CsvFile = new File(file, "/" + "product");
        
    }
}