package com.example.trending;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.firebase.auth.FirebaseAuth;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    FirebaseAuth auth;
    ViewFlipper viewFlipper;
    ImageView iv;


    public HomeFragment() {
        // Required empty public constructor
    }

    Button b1, b2, b3, b4, b5, b6, b7, b8, b9;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        b1 = v.findViewById(R.id.ml);
        int images[] = {R.drawable.non, R.drawable.hello,
                R.drawable.chinna, R.drawable.frd,
                R.drawable.pex, R.drawable.cyber,R.drawable.sdd,
                R.drawable.unna,R.drawable.download,R.drawable.junnu,
                R.drawable.cse,R.drawable.ai,R.drawable.hello,R.drawable.sdd,
        };
        viewFlipper = v.findViewById(R.id.v_flipper);
        for (int image : images) {
            flipperImages(image);
        }



    auth=FirebaseAuth.getInstance();
        ConnectivityManager conMgr =  (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
        if (netInfo == null){
            //EventLogTags.Description.setVisibility(View.INVISIBLE);
            new AlertDialog.Builder(getActivity())
                    .setTitle(getResources().getString(R.string.app_name))
                    .setMessage("Please Check your Internetconnection")
                    .setPositiveButton("OK", null).show();
        }else {
            Toast.makeText(getActivity(), "Iternet is Available", Toast.LENGTH_SHORT).show();
        }


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent i = new Intent(getActivity(),Button1Activity.class);
               startActivity(i);
            }
        });
        b2 = v.findViewById(R.id.iot);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),Button2Activity.class);
                startActivity(i);
            }
        });
        b3 = v.findViewById(R.id.robo);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),Button3Activity.class);
                startActivity(i);
            }
        });
        b4 = v.findViewById(R.id.andriod);
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),Button4Activity.class);
                startActivity(i);
            }
        });
        b5 = v.findViewById(R.id.phy);
      b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),Button5Activity.class);
                startActivity(i);
            }
        });
        b6 = v.findViewById(R.id.ai);
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),Button6Activity.class);
                startActivity(i);
            }
        });
        b7 = v.findViewById(R.id.cyber);
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),Button7Activity.class);
                startActivity(i);
            }
        });
        b8 = v.findViewById(R.id.bc);
        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),Button8Activity.class);
                startActivity(i);
            }
        });
        b9 = v.findViewById(R.id.web);
        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),Button9Activity.class);
                startActivity(i);
            }
        });
        return v;
    }

    private void flipperImages(int image) {
        ImageView imageView=new ImageView( getActivity() );
        imageView.setBackgroundResource( image );
        viewFlipper.addView( imageView );
        viewFlipper.setFlipInterval( 2000 );
        viewFlipper.setAutoStart( true );

        viewFlipper.setInAnimation( getActivity(),android.R.anim.slide_in_left );
        viewFlipper.setOutAnimation( getActivity(),android.R.anim.slide_out_right );


    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        inflater.inflate(R.menu.drawermenu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.logout:
            {
                sognout();
                break;


            }
        }
        return super.onOptionsItemSelected(item);
    }
    private void sognout()
    {

        SharedPreferences.Editor editor=getActivity().getSharedPreferences("shaida", getActivity().MODE_PRIVATE).edit();
        editor.putInt("shaida",0);
        editor.apply();
        auth.signOut();
        Intent i1=new Intent(getActivity(), HomeFragment.class);
        startActivity(i1);

    }
}
