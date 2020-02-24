package com.example.trending;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class AdminFunctionalityFragment extends Fragment {


    private static final int PICKFILE_REQUEST_CODE = 22;

    public AdminFunctionalityFragment() {
        // Required empty public constructor
    }

    StorageReference storageReference;
    FirebaseStorage storage;

    FirebaseDatabase database;
    DatabaseReference reference;
    Spinner sp;
    EditText et_videoid /*et_videod, et_videoids ,et_videis*/;
    Button b_upload,b_submit;
    TextView tv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_admin_functionality, container, false);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Technologies");
        et_videoid = v.findViewById(R.id.videoid);
        /*et_videod = v.findViewById(R.id.videod);
        et_videoids = v.findViewById(R.id.videoids);
        et_videis = v.findViewById(R.id.videis);*/
        sp = v.findViewById(R.id.technologyname);
        b_upload = v.findViewById(R.id.uploadbutton);
        b_submit = v.findViewById(R.id.submitbutton);
        tv = v.findViewById(R.id.documentname);
        b_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileExplorer();
            }
        });
        b_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });
        return v;
    }

    private void saveData() {
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String filepath = uri.toString();
                String technology= sp.getSelectedItem().toString();
                String videoid = et_videoid.getText().toString();
             /*final    String vi = et_videis.getText().toString();
                String video = et_videoids.getText().toString();
                String vide = et_videod.getText().toString();*/
                MyResource resource = new MyResource(technology,filepath,videoid);
                reference.child(technology).push().setValue(resource).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getActivity(), "Details Saved Successfully...", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });

    }

    private void openFileExplorer() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent, PICKFILE_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICKFILE_REQUEST_CODE){
            if(resultCode == RESULT_OK){
                Uri u = data.getData();
                tv.setText(""+u);
                fileUploading(u);
            }
        }
    }

    private void fileUploading(Uri u) {
        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setMessage("Uploading File");
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setMax(100);
        pd.setCancelable(false);
        pd.show();
        storageReference=storageReference.child("Files/"+ UUID.randomUUID().toString());
        storageReference.putFile(u).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getActivity(),
                        "Image Uploaded Successfully...", Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(),
                        ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred())
                        / taskSnapshot.getTotalByteCount();
                pd.setProgress((int) progress);

            }
        });

    }
}
