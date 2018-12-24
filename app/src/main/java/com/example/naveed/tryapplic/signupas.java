package com.example.naveed.tryapplic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class signupas extends AppCompatActivity {
    Button teacher;
    Button parentb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupas);
        teacher= findViewById(R.id.SignUpAsTeacher);
        parentb= findViewById(R.id.SignUpAsParent);
        teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iamteacher= new Intent(signupas.this,regstrteacher.class);
                startActivity(iamteacher);
            }
        });
        parentb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iamteacher= new Intent(signupas.this,regstrparent.class);
                startActivity(iamteacher);
            }
        });

    }

}
