package com.example.trending;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Button9Activity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference reference;
    RecyclerView rv;
    List<MyResource> resourceList;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button9);
        resourceList = new ArrayList<>();
        rv=findViewById(R.id.recycler);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Technologies");
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("loading");
        pd.show();


        reference.child("WebTechnolgy").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    MyResource resource = snapshot.getValue(MyResource.class);
                    resourceList.add(resource);
                }
                Toast.makeText(Button9Activity.this, ""+resourceList.size(), Toast.LENGTH_SHORT).show();
                rv.setLayoutManager(new LinearLayoutManager(Button9Activity.this));
                WebAdapter adapter = new  WebAdapter(Button9Activity.this,resourceList);
                rv.setAdapter(adapter);
                pd.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

