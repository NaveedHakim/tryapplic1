package com.example.naveed.tryapplic;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class JoinTeacherActivity extends AppCompatActivity {


    DatabaseReference reference,currentReference;
    FirebaseAuth auth;
    FirebaseUser user;
    String current_user_id,join_user_id;
    DatabaseReference circleReference;
    Button submit;
    TextView code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_teacher);
        submit = findViewById(R.id.submit);
        code = findViewById(R.id.code);


        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        current_user_id = user.getUid();

        reference = FirebaseDatabase.getInstance().getReference().child("school");
        currentReference = FirebaseDatabase.getInstance().getReference().child("school").child(current_user_id);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitButtononClick();
            }
        });

    }




    public void submitButtononClick()
    {
        //joining circle by add id to another node
        Query query = reference.orderByChild("code").equalTo(code.getText().toString());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    CreateUser createUser = null;
                    for (DataSnapshot childDss : dataSnapshot.getChildren())
                    {
                        createUser = childDss.getValue(CreateUser.class);
                        join_user_id = createUser.userId;

                        circleReference = FirebaseDatabase.getInstance().getReference().child("school")
                                .child(join_user_id).child("CircleMembers");

                        TeacherJoin teacherJoin = new TeacherJoin(current_user_id);
                        TeacherJoin teacherJoin1 = new TeacherJoin(join_user_id);

                        circleReference.child(user.getUid()).setValue(teacherJoin)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful())
                                        {
                                            Toast.makeText(getApplicationContext(),"Successfully joined Teacher", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Code Not Found in database",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
