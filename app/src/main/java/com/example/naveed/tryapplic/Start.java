package com.example.naveed.tryapplic;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Start extends AppCompatActivity {

    private static final long SPLASH_TIME =50 ;

    private DatabaseReference mdatabase;
    private String currentuid;
    private FirebaseUser current_uid;
    private  FirebaseAuth mAuth;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
      new BackgroundTask().execute();

        mAuth= FirebaseAuth.getInstance();
current_uid= mAuth.getCurrentUser();
        if(current_uid!=null){
            currentuid= current_uid.getUid();
            mdatabase=  FirebaseDatabase.getInstance().getReference().child("school");
            mdatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot childuid : dataSnapshot.getChildren())
                    {
                        uid = childuid.getKey();
                        //  Toast.makeText(login.this, uid,Toast.LENGTH_LONG).show();
                        if (uid.equals(currentuid)){
                            if(dataSnapshot.child(currentuid).child("role").getValue().toString().equals("parent")){
                               Intent intentp = new Intent(Start.this,ParentHome.class);
                               startActivity(intentp);
                               finish();

                            }
                            else if (dataSnapshot.child(currentuid).child("role").getValue().toString().equals("teacher"))
                            {
                               Intent intentt = new Intent(Start.this,TeacherHome.class);
                               startActivity(intentt);
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
        Intent    loginintent=   new Intent(Start.this,Login.class);
        startActivity(loginintent);
        finish();

        }



    }


    private class BackgroundTask extends AsyncTask {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();




        }



        @Override
        protected Object doInBackground(Object[] params) {
         //   user = auth.getCurrentUser();


            /*  Use this method to load background
             * data that your app needs. */


            try {


                Thread.sleep(SPLASH_TIME);






            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
//            Pass your loaded data here using Intent

//

        }

}
}