package com.example.naveed.tryapplic;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Random;

public class regstrparent extends AppCompatActivity {
 /*  private TextInputLayout parent;
   private TextInputLayout pe;
    private TextInputLayout cn;

    private TextInputLayout cg;
    private TextInputLayout passwparent;
    ImageView parent_image;
   private Button parent_sigup;
    private FirebaseAuth mAuthan;
    private FirebaseUser current_user;
    private DatabaseReference mdatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regstrparent);
        mAuthan = FirebaseAuth.getInstance();

        parent= findViewById(R.id.parent_name);
        pe = findViewById(R.id.parent_email);
        cn= findViewById(R.id.child_name);
        cg= findViewById(R.id.child_grade);
       passwparent = findViewById(R.id.parent_pass);
        parent_sigup= findViewById(R.id.parentsign);
        getSupportActionBar().setTitle("Parent SignUP");


        parent_sigup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = parent.getEditText().getText().toString();
                String pass = passwparent.getEditText().getText().toString();
                String email = pe.getEditText().getText().toString();
                String childname = cn.getEditText().getText().toString();
                String childgrade = cg.getEditText().getText().toString();
                String role="parent";

                if (!TextUtils.isEmpty(name) || !TextUtils.isEmpty(email) || !TextUtils.isEmpty(pass)) {
                    reguser(name,email,pass,childname,childgrade,role);


                }
            }
        });

        }

        private void reguser(final String name, final String email,final String pass, final String childname, final String childgrade, final String role){





            mAuthan.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){

                        current_user= FirebaseAuth.getInstance().getCurrentUser();
                        String uid= current_user.getUid();
                        // String uid= displayname;

                        DatabaseReference myteacher;

                        mdatabase= FirebaseDatabase.getInstance().getReference("school").child(uid);

                        /////////////////////////////////////////////////////////////

                        Random r =new Random();

                        int n = 100000 + r.nextInt(900000);
                       // String code = String.valueOf(n);
                        ////////////////////////////////////////////////////////

                        String role ="teacher";
                           HashMap<String,String> usermap=new HashMap<>();
                         usermap.put("name" , name);
                        usermap.put("email" , email);
                        usermap.put("schoolkey" , "1234");
                           usermap.put("image" , "default");
                         usermap.put("thumb_nail" ,"default" );
                         usermap.put("role" ,role);

                      //  CreateUser usermap = new CreateUser(name,email,pass,code,"default","default","1234",uid);

                        mdatabase.setValue(usermap)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        sendVarificationEmail();
                                    }
                                });

                        // myteacher=mdatabase.child("teacher").child("1234");
                        // myteacher.child("name").setValue("teachera");
                        //  myteacher.child("name").setValue("teachera");

                        // Intent reg=new Intent(regstractivity.this,NavActivity.class);
                        //   reg.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                        // startActivity(reg);
                        //finish();
                    }
                    else{
                        Toast.makeText(regstrparent.this, "ghalat", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    public void sendVarificationEmail()
    {
        current_user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(getApplicationContext(),"Email Sent For Varification",Toast.LENGTH_SHORT).show();

                            //finish();
                            mAuthan.signOut();
                            Log.i("naveed", "signout successfull ");
                            finish();
                            Intent myintent = new Intent(regstrparent.this,login.class);
                            startActivity(myintent);
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Email cannot be sent",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }*/
 private TextInputLayout regname;


    private FirebaseAuth mAuth;
    FirebaseUser current_user;
    private DatabaseReference mdatabase;
    private TextInputLayout parent;
    private TextInputLayout pe;
    private TextInputLayout cn;

    private TextInputLayout cg;
    private TextInputLayout passwparent;
    private Button parent_sigup;
    private Toolbar mtoolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regstrparent);
        mAuth = FirebaseAuth.getInstance();
        parent = findViewById(R.id.parent_name);
        pe = findViewById(R.id.parent_email);
        cn = findViewById(R.id.child_name);
        cg = findViewById(R.id.child_grade);
        passwparent = findViewById(R.id.parent_pass);
        parent_sigup = findViewById(R.id.parentsign);

        parent_sigup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = parent.getEditText().getText().toString();
                String pass = passwparent.getEditText().getText().toString();
                String email = pe.getEditText().getText().toString();
                String childname = cn.getEditText().getText().toString();
                String childgrade = cg.getEditText().getText().toString();
                String role = "parent";

                if (!TextUtils.isEmpty(name) || !TextUtils.isEmpty(email) || !TextUtils.isEmpty(pass)) {
                    reguser(name, email, pass, childname, childgrade, role);


                }
            }
        });




    }




    private void reguser(final String name, final String email,final String pass, final String childname, final String childgrade, final String role){

        mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    current_user= FirebaseAuth.getInstance().getCurrentUser();
                    String uid= current_user.getUid();
                    // String uid= displayname;

                    DatabaseReference myteacher;

                    mdatabase= FirebaseDatabase.getInstance().getReference("school").child(uid);

                    /////////////////////////////////////////////////////////////

                    Random r =new Random();

                    int n = 100000 + r.nextInt(900000);
                    // String code = String.valueOf(n);
                    ////////////////////////////////////////////////////////

                    String role ="parent";
                    HashMap<String,String> usermap=new HashMap<>();
                    usermap.put("name" , name);
                    usermap.put("email" , email);
                    usermap.put("schoolkey" , "1234");
                    usermap.put("image" , "default");
                    usermap.put("thumb_nail" ,"default" );
                    usermap.put("role" ,role);

                    //  CreateUser usermap = new CreateUser(name,email,pass,code,"default","default","1234",uid);

                    mdatabase.setValue(usermap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                   sendVarificationEmail();
                                }
                            });

                    // myteacher=mdatabase.child("teacher").child("1234");
                    // myteacher.child("name").setValue("teachera");
                    //  myteacher.child("name").setValue("teachera");

                    // Intent reg=new Intent(regstractivity.this,NavActivity.class);
                    //   reg.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                    // startActivity(reg);
                    //finish();
                }
                else{
                    Toast.makeText(regstrparent.this, "ghalat", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    public void sendVarificationEmail()
    {
        current_user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(getApplicationContext(),"Email Sent For Varification",Toast.LENGTH_SHORT).show();

                            //finish();
                            mAuth.signOut();
                            Log.i("naveed", "signout successfull ");
                            finish();
                            Intent myintent = new Intent(regstrparent.this,Login.class);
                            startActivity(myintent);
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Email cannot be sent",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }



    }


