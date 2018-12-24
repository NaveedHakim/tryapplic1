package com.example.naveed.tryapplic;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.squareup.picasso.Picasso;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class AddTeacher extends Fragment {
    private TextInputLayout teacher_name;
    private TextInputLayout teacher_email;
    private TextInputLayout teacher_class;
    private TextInputLayout pass;
    private Button button;
    private FirebaseAuth mAuth;

    private FirebaseUser current_user;
    private DatabaseReference mdatabase;
    private String curren_state;
    DataSnapshot ds;
    String name;
    DatabaseReference teacher;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_teacher, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Add New Teacher");

      //  mAuth = FirebaseAuth.getInstance();

        teacher_name = (TextInputLayout) getView().findViewById(R.id.teacher_name);
        teacher_email = (TextInputLayout) getView().findViewById(R.id.email);

        teacher_class = (TextInputLayout) getView().findViewById(R.id.teacher_class);
        pass = (TextInputLayout) getView().findViewById(R.id.login_pass);
        button = (Button) getView().findViewById(R.id.submit);
        curren_state="not friends";

        button.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String teacher_regname = teacher_name.getEditText().getText().toString();
                String teacher_regemail = teacher_email.getEditText().getText().toString();
                String teacher_regclass = teacher_class.getEditText().getText().toString();
                String teacher_regpass = pass.getEditText().getText().toString();
                String adminname="naveed";
                mAuth = FirebaseAuth.getInstance();

                if (!TextUtils.isEmpty(teacher_regname) || !TextUtils.isEmpty(teacher_regemail) || !TextUtils.isEmpty(teacher_regclass) || !TextUtils.isEmpty(teacher_regpass))
                    regteacher(teacher_regname, teacher_regclass, teacher_regemail, teacher_regpass,adminname);

                button.setEnabled(false);

            }
        }));


    }

    private void regteacher(final String teacher_regname, final String teacher_regclass, final String teacher_regemail, String teacher_regpass, String adminname) {
        mAuth.createUserWithEmailAndPassword(teacher_regemail, teacher_regpass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    //  current_user = FirebaseAuth.getInstance().getCurrentUser();
                    //String uid = current_user.getUid();
                    DataSnapshot ds = null;

                    mdatabase = FirebaseDatabase.getInstance().getReference();
                    mdatabase.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                name = ds.getKey();
                                String dsa = (String) dataSnapshot.child(name).getValue();

                                if (dsa.equals("naveed")) {
                                    // String name = dataSnapshot.child().child("name").getValue().toString();
                                    //   image = dataSnapshot.child(name).child("image").getValue().toString();
                                    teacher = mdatabase.child("teacher").child(teacher_regclass);
                                    HashMap<String, String> teachermap = new HashMap<>();
                                    teachermap.put("name", teacher_regname);
                                    teachermap.put("image", "default");
                                    teachermap.put("thumb_nail", "default");
                                    teacher.setValue(teachermap)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {

                                                    //    sendVarificationEmail();
                                                }
                                            });
                                    String schoolkey = dataSnapshot.child(name).child("schoolkey").getValue().toString();
                                    //   display_name.setText(name);
                                    //  schol_key.setText(schoolkey);

                                }

                            }
                            // Toast.makeText(showaccount.this, dataSnapshot.toString(), Toast.LENGTH_SHORT).show();


                            //      Picasso.get().load(image).into(image_view);


                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }


                    });
                }
            }

        });

   }
}



