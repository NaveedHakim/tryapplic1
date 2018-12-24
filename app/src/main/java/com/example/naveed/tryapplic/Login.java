package com.example.naveed.tryapplic;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    private Button needaccount;
    private Button login;
private TextView signup;
    private String uid;
    private TextInputLayout myemail;
    private TextInputLayout mypass;
   private String email, pass;
   private  FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        login= findViewById(R.id.btn_login);
        myemail= (TextInputLayout) findViewById(R.id.myemail) ;
        mypass=(TextInputLayout) findViewById(R.id.mypass);
        signup= findViewById(R.id.link_signup);
        mAuth=FirebaseAuth.getInstance();



signup.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent signupasi= new Intent(Login.this, signupas.class);
        startActivity(signupasi);
        finish();
    }
});
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             email= myemail.getEditText().getText().toString();
             pass= mypass.getEditText().getText().toString();
                if((!TextUtils.isEmpty(email)) || (!TextUtils.isEmpty(pass))){
                    // login_User(email,pass);
                    login();

                }
            }
        });

    }


    private FirebaseUser current_uid;
    private String curentuid;
    private DatabaseReference databaseReference;
    ProgressDialog progressDialog;

    public void login(){
        login.setEnabled(false);
        progressDialog = new ProgressDialog(Login.this);

        progressDialog.setTitle("uploading image");
        progressDialog.setMessage("please wait while system uploads image");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        mAuth.signInWithEmailAndPassword(email,pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            FirebaseUser user =mAuth.getCurrentUser();
                            progressDialog.dismiss();
                            if (user.isEmailVerified()){
                                current_uid= mAuth.getCurrentUser();
                                curentuid= current_uid.getUid();

                                Toast.makeText(Login.this, "Authentication complete.  ",
                                        Toast.LENGTH_SHORT).show();
                                databaseReference = FirebaseDatabase.getInstance().getReference().child("school");

                                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        for (DataSnapshot childuid : dataSnapshot.getChildren())
                                        {
                                            uid = childuid.getKey();
                                            //  Toast.makeText(login.this, uid,Toast.LENGTH_LONG).show();
                                            if (uid.equals(curentuid)){
                                                if(dataSnapshot.child(curentuid).child("role").getValue().toString().equals("parent")){
                                                    Intent myintent = new Intent(Login.this,ParentHome.class);
                                                    startActivity(myintent);
                                                    finish();
                                                    break;
                                                }
                                                else if (dataSnapshot.child(curentuid).child("role").getValue().toString().equals("teacher")){
                                                    Intent myintent = new Intent(Login.this,TeacherHome.class);
                                                    startActivity(myintent);
                                                    finish();
                                                }

                                            }
                                        }

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });







                            }
                            else
                            {
                                progressDialog.dismiss();

                                Toast.makeText(getApplicationContext(),"Email Not varified",Toast.LENGTH_SHORT).show();
                                login.setEnabled(true);
                            }

                            // Toast.makeText(getApplicationContext(),"user Login Successfull",Toast.LENGTH_LONG).show();
                        }
                        else{
                            progressDialog.dismiss();

                            Toast.makeText(getApplicationContext(),"Wrong Email or Password",Toast.LENGTH_LONG).show();
                            login.setEnabled(true);

                        }
                    }
                });
    }
}

 /* needaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reg_intent= new Intent(Startactivity.this,signupas.class);
                startActivity(reg_intent);
            }
        });*/