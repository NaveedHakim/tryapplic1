package com.example.naveed.tryapplic;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ParentListActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseAuth auth;
   private FirebaseUser user;
    MembersAdapter adapter;
    CreateUser createUser;
    ArrayList<CreateUser> namelist;
    DatabaseReference reference,userReference;
    String parentid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_list);



        recyclerView = findViewById(R.id.recyclerview);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        namelist = new ArrayList<>();

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new MembersAdapter(namelist,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        Toolbar toolbar = findViewById(R.id.toolbar);


        userReference = FirebaseDatabase.getInstance().getReference().child("school");
        reference = FirebaseDatabase.getInstance().getReference("school").child(user.getUid()).child("CircleMembers");



        Log.i("naveed",reference.getKey());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                namelist.clear();
                Log.i("naveed"," Clearing List....");
                Log.i("naveed"," datasnapshot count "+dataSnapshot.getChildrenCount());
                if (dataSnapshot.exists())
                {
                    Log.i("naveed",dataSnapshot.getChildrenCount()+"  ........");


                    for (DataSnapshot dss: dataSnapshot.getChildren())
                    {



                        Log.i("naveed",dss.getKey());
                        parentid = dss.child("circleMemberId").getKey();
                        Log.i("naveed",parentid.toString());

                        userReference.child(dss.getKey())
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        Log.i("naveed",dataSnapshot.toString());
                                        Log.d("naveed", "onDataChange: Creating user and adding to list");


                                        namelist.add(dataSnapshot.getValue(CreateUser.class));


                                        if (namelist.get(0) == null)
                                            Log.d("naveed", "onDataChange: This is ulln");
                                        Log.i("naveed",namelist.get(0)+" working....."+ dataSnapshot.getValue());
                                        adapter.notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        Toast.makeText(getApplicationContext(),databaseError.getMessage(),Toast.LENGTH_SHORT).show();
                                    }
                                });

                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),databaseError.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });

        adapter = new MembersAdapter(namelist,getApplicationContext());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


    }
}
