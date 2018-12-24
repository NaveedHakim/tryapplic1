package com.example.naveed.tryapplic;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ParentHome extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;
    Uri imageuri;

    private ImageButton edit_image;
    private final int GALLERY_PICK = 1;
    private DatabaseReference mdatabase;
    private FirebaseUser mcurrentuser;
    private CircleImageView image_view;
    private TextView display_name;
    private TextView schol_key;

    private StorageReference mystrogeref;
    private ProgressDialog mprogressbar;

    TextView c_name,c_email;
    ImageView c_pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mAuth = FirebaseAuth.getInstance(); //from main

        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("School System");



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        mcurrentuser = FirebaseAuth.getInstance().getCurrentUser();

        mystrogeref = FirebaseStorage.getInstance().getReference();

        String current_uid = mcurrentuser.getUid();
        mdatabase = FirebaseDatabase.getInstance().getReference().child("school").child(current_uid);
        edit_image = (ImageButton) findViewById(R.id.imageButton);
        display_name = (TextView) findViewById(R.id.user_name);
        schol_key = (TextView) findViewById(R.id.School_key);
        image_view= (CircleImageView) findViewById(R.id.user_images);
        View header = navigationView.getHeaderView(0);
        c_name = header.findViewById(R.id.name);
        c_email = header.findViewById(R.id.email);
        c_pic = header.findViewById(R.id.imageView);
        mdatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Toast.makeText(showaccount.this, dataSnapshot.toString(), Toast.LENGTH_SHORT).show();
                String name = dataSnapshot.child("name").getValue().toString();
                String image = dataSnapshot.child("image").getValue().toString();
                String email = dataSnapshot.child("email").getValue().toString();
                String schoolkey = dataSnapshot.child("schoolkey").getValue().toString();
                display_name.setText(name);
                schol_key.setText(schoolkey);

                c_name.setText(name);
                c_email.setText(email);

                Picasso.get().load(image).into(image_view);
                Picasso.get().load(image).into(c_pic);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        edit_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(galleryIntent, "Select Image"), GALLERY_PICK);

            }
        });
    }

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser==null){
            sendToStart();
        }
    }

    private void sendToStart() {
        Intent startIntent=new Intent(ParentHome.this,Login.class);
        startActivity(startIntent);
        finish();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if(item.getItemId()==R.id.log_out){
            FirebaseAuth.getInstance().signOut();
            sendToStart();
        }


        return true;
    }

    private boolean isStartup = true;

    public void itemselected(int id){
        if(isStartup) {
            ((FrameLayout) findViewById(R.id.content_nav)).removeAllViews();
            isStartup = false;
        }
        Fragment fragment=null;
        // Handle navigation view item clicks here.
        switch (id){
            case R.id.Add_teacher:
                fragment = new AddTeacher();
                break;
        }
        if(fragment!=null){

            FragmentTransaction ft= getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_nav,fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        itemselected(id);

        if (id == R.id.nav_share) {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_TEXT, "You can write here any thing");
            startActivity(i.createChooser(i, "Invite using: "));

        }
        else if(id == R.id.nav_send)
        {
            Intent myintent = new Intent(ParentHome.this,JoinTeacherActivity.class);
            startActivity(myintent);
        }



        return true;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_PICK && resultCode == RESULT_OK) {
            imageuri = data.getData();
            if (resultCode == RESULT_OK) {
                mprogressbar = new ProgressDialog(ParentHome.this);
                mprogressbar.setTitle("uploading image");
                mprogressbar.setMessage("please wait while system uploads image");
                mprogressbar.setCanceledOnTouchOutside(false);
                mprogressbar.show();
                String current_user_id = mcurrentuser.getUid();
                StorageReference filepath = mystrogeref.child("profile_image").child(current_user_id + ".jpg");
                filepath.putFile(imageuri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                        if (task.isSuccessful()) {
                            String download_url = task.getResult().getDownloadUrl().toString();
                            mdatabase.child("image").setValue(download_url).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        mprogressbar.dismiss();
                                        Toast.makeText(ParentHome.this, "sent to db", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                });
            }



            //Toast.makeText(showaccount.this,imageuri,Toast.LENGTH_LONG).show();
            //   CropImage.activity(imageuri).setAspectRatio(1, 1).start(this);

        }

    }
}

    /*public static String random() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(100);
        char tempChar;
        for (int i = 0; i < randomLength; i++){
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }*/


