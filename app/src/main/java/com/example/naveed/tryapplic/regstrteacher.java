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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class regstrteacher extends AppCompatActivity {
private TextInputLayout regname;
private TextInputLayout regemail;
private TextInputLayout pass;
private Button regbtn;

private FirebaseAuth mAuth;
FirebaseUser current_user;
private DatabaseReference mdatabase;

private Toolbar mtoolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regstractivity);
        mAuth = FirebaseAuth.getInstance();
        mtoolbar=(Toolbar) findViewById(R.id.register_toolbar);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle("School System");
        regname=findViewById(R.id.teacher_class);
        regemail=findViewById(R.id.email);
        pass= findViewById(R.id.login_pass);
        regbtn= findViewById(R.id.button);
        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String displayname= regname.getEditText().getText().toString();
            String displaymail= regemail.getEditText().getText().toString();
            String displaynpass= pass.getEditText().getText().toString();

            if(!TextUtils.isEmpty(displayname) || !TextUtils.isEmpty(displaymail) || !TextUtils.isEmpty(displaynpass) ){
                reguser(displayname,displaymail,displaynpass);

            }
            }


        });

    }
    private void reguser(final String displayname, final String displaymail, final String displaynpass) {
        mAuth.createUserWithEmailAndPassword(displaymail,displaynpass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    current_user= FirebaseAuth.getInstance().getCurrentUser();
                  String uid= current_user.getUid();
                  // String uid= displayname;

                    DatabaseReference myteacher;

                    mdatabase=FirebaseDatabase.getInstance().getReference("school").child(uid);

                    /////////////////////////////////////////////////////////////

                    Random r =new Random();

                    int n = 100000 + r.nextInt(900000);
                    String code = String.valueOf(n);
                    ////////////////////////////////////////////////////////

                    String role ="teacher";
               //     HashMap<String,String> usermap=new HashMap<>();
                //   usermap.put("name" , displayname);
                 //  usermap.put("email" , displaymail);
                 //   usermap.put("schoolkey" , "1234");
                 //   usermap.put("image" , "default");
                 //   usermap.put("thumb_nail" ,"default" );
                 ///   usermap.put("code" ,code);

                    CreateUser usermap = new CreateUser(displayname,displaymail,displaynpass,code,"default","default","1234",uid,role);
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
                    Toast.makeText(regstrteacher.this, "ghalat", Toast.LENGTH_SHORT).show();
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
                            Intent myintent = new Intent(regstrteacher.this,Login.class);
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
