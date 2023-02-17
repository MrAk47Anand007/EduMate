package com.example.gpa.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gpa.MainActivity;
import com.example.gpa.R;

public class auth extends AppCompatActivity {
    private EditText name, password;
    private Button loginbtn;
    private String ID, Pass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        name = findViewById(R.id.editauthname);
        password = findViewById(R.id.editauthpassword);
        loginbtn = findViewById(R.id.authbutton);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ID = name.getText().toString();
                Pass = password.getText().toString();

                if (ID.equals("") || Pass.equals("")) {
                    name.setError("Error");
                    name.requestFocus();
                    password.setError("Error");
                    password.requestFocus();
                } else if (ID.equals("Admin2021") & Pass.equals("Gpait")) {

                    startActivity(new Intent(auth.this, MainActivity.class));
                    Toast.makeText(auth.this, "Your Enter Right Credentials", Toast.LENGTH_SHORT).show();
                    name.setText("");
                    password.setText("");
                } else {
                    Toast.makeText(auth.this, "Your Enter Wrong Credentials", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}