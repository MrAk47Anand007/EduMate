package com.example.gpa.submissionView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gpa.R;
import com.github.barteksc.pdfviewer.PDFView;
import com.krishna.fileloader.FileLoader;
import com.krishna.fileloader.listener.FileRequestListener;
import com.krishna.fileloader.pojo.FileResponse;
import com.krishna.fileloader.request.FileLoadRequest;

import java.io.File;

public class pdfViewactivity extends AppCompatActivity {
    private String url;
    private PDFView pdfView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_viewactivity);

        url = getIntent().getStringExtra("pdfUrl");
        pdfView = findViewById(R.id.pdfView);
        progressBar = findViewById(R.id.pdfprogressbar);

        loadPDF(url);
    }

    private void loadPDF(String url) {
        FileLoader.with(this)
                .load(url)
                .fromDirectory("test4", FileLoader.DIR_INTERNAL)
                .asFile(new FileRequestListener<File>() {
                    @Override
                    public void onLoad(FileLoadRequest request, FileResponse<File> response) {
                        progressBar.setVisibility(View.GONE);
                        File loadedFile = response.getBody();
                        pdfView.fromFile(loadedFile)
                                .password(null)
                                .defaultPage(0)
                                .enableSwipe(true)
                                .swipeHorizontal(false)
                                .enableDoubletap(true)
                                .spacing(5)
                                .load();
                    }

                    @Override
                    public void onError(FileLoadRequest request, Throwable t) {
                        Toast.makeText(pdfViewactivity.this,"Error"+t.getMessage(),Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                });

    }

}


