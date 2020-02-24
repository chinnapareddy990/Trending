package com.example.trending;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;


/**
 * A simple {@link Fragment} subclass.
 */
public class LogoutFragment extends Fragment {

    FirebaseAuth auth;


    public LogoutFragment() {
        // Required empty public constructor
    }
    TextView tv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragmen

        View v = inflater.inflate(R.layout.fragment_logout,container,false);
        tv= v.findViewById(R.id.tvv);
        return v;

    }





    }




