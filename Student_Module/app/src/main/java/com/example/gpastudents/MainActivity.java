package com.example.gpastudents;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.gpastudents.auth.auth;
import com.example.gpastudents.book.BookActivity;
import com.example.gpastudents.curriculum.curriculumActivity;
import com.example.gpastudents.oldPaper.oldPaperActivity;
import com.example.gpastudents.submission.SubmissionActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bottomNavigationView;
    private NavController navController;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        navController = Navigation.findNavController(this, R.id.fragment);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_drawer);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.Start, R.string.Close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        navigationView.setNavigationItemSelectedListener(this);

        NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_book:
                startActivity(new Intent(this, BookActivity.class));
                break;
            case R.id.navigation_curriculum:
                startActivity(new Intent(this, curriculumActivity.class));
                break;
            case R.id.navigation_oldpaper:
                startActivity(new Intent(this, oldPaperActivity.class));
                break;
            case R.id.navigation_submission:
                startActivity(new Intent(this, auth.class));
                break;
            case R.id.navigation_website:
                String url=("http://www.gpamravati.ac.in/gpamravati_new/");
                Uri uri = Uri.parse(url);
                startActivity(new Intent(Intent.ACTION_VIEW,uri));
                break;
            case R.id.navigation_share:
                Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show();
                break;

        }

        return true;
    }
}