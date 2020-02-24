package com.example.trending;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity2 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mDrawerlayout;
    private ActionBarDrawerToggle mToogle;

    FragmentManager fragmentManager;
    NavigationView nv;
    private GoogleSignInClient mGoogleSignInClient;
    FirebaseAuth auth;
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        auth=FirebaseAuth.getInstance();
        preferences = getSharedPreferences("MyPrefrences",MODE_PRIVATE);
        mDrawerlayout=(DrawerLayout)findViewById(R.id.drawer);
        nv = findViewById(R.id.navV);
        nv.setNavigationItemSelectedListener(this);
        mToogle=new ActionBarDrawerToggle(this,mDrawerlayout,R.string.open,R.string.close);
        mDrawerlayout.addDrawerListener(mToogle);
        mToogle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();
        String name = preferences.getString("name","nodata");
        String email = preferences.getString("email","nodata");
        String imgurl = preferences.getString("photourl","nodata");;
        Toast.makeText(this, ""+name+email, Toast.LENGTH_SHORT).show();
        View v = nv.getHeaderView(0);
        TextView tv_name = v.findViewById(R.id.name);
        TextView tv_email = v.findViewById(R.id.email);
        ImageView iv = v.findViewById(R.id.image);
        tv_name.setText(name);
        tv_email.setText(email);
        Glide.with(this).load(imgurl).into(iv);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


    }
    @Override

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(mToogle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.home:
                fragmentManager.beginTransaction().
                        replace(R.id.fragment_container,new HomeFragment())
                        .commit();
                mDrawerlayout.closeDrawers();
                break;
            case R.id.settings:
                fragmentManager.beginTransaction().
                        replace(R.id.fragment_container,new SettingsFragment())
                        .commit();
                mDrawerlayout.closeDrawers();
                break;
            case R.id.admin:
                fragmentManager.beginTransaction().replace(R.id.fragment_container,new AdminLoginFragment()).commit();
                mDrawerlayout.closeDrawers();
                break;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                mGoogleSignInClient.signOut();
                Intent i = new Intent(this,GoogleButton.class);
                startActivity(i);
                callsignout();

                break;

        }

        return true;
    }

    private void callsignout()
    {
        SharedPreferences.Editor editor=getSharedPreferences("shaida",getApplication().MODE_PRIVATE).edit();
        editor.putInt("shaida",0);
        editor.apply();
        auth.signOut();
        finish();
        Intent i1=new Intent(this, GoogleButton.class);
        startActivity(i1);

    }

}




