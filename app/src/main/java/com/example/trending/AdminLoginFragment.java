


package com.example.trending;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;


/**
 *
 *
 * A simple {@link Fragment} subclass.
 */
public class AdminLoginFragment extends Fragment {



    public AdminLoginFragment() {
        // Required empty public constructor
    }


    EditText et_username,et_password,et_mobile,et_otp;
    Button b,b1;
    FragmentManager manager;
    private String mVerificationId;

    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_admin_login, container, false);
        manager = getFragmentManager();
        mAuth = FirebaseAuth.getInstance();
        et_username = v.findViewById(R.id.adminUsername);
        et_password = v.findViewById(R.id.adminpassword);
        et_mobile = v.findViewById(R.id.phonenumberEdittext);
        et_otp = v.findViewById(R.id.otpEditText);
        b = v.findViewById(R.id.adminloginButton);
        b1=v.findViewById(R.id.generate);
       /* b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = et_username.getText().toString();
                String password = et_password.getText().toString();
                if((username.equals("Cssat")) &&(password.equals("9981")) ){
                    manager.beginTransaction().replace(R.id.fragment_container,new AdminFunctionalityFragment()).commit();


                }else {
                    Toast.makeText(getActivity(), "Please check Username and Password", Toast.LENGTH_SHORT).show();
                }
            }
        });*/
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = et_username.getText().toString();
                String password = et_password.getText().toString();
                String mobile = et_mobile.getText().toString().trim();

                if (mobile.equals("9908829268")&&(username.equals("Cssat")) &&(password.equals("9981"))){
                    if (mobile.isEmpty() || mobile.length() < 10) {
                        et_mobile.setError("Enter a valid mobile");

                        et_mobile.requestFocus();
                        return;
                    }
                sendVerificationCode(mobile);

                String code = et_otp.getText().toString().trim();
                if (code.isEmpty() || code.length() < 6) {
                    //et_otpcode.setError("Enter valid code");
                    //et_otpcode.requestFocus();
                    return;
                }


                verifyVerificationCode(code);


            }
                else {
                    Toast.makeText(getActivity(), "Access Denied", Toast.LENGTH_SHORT).show();
                }
        }
        });

        return v;
    }
    public void verify(View view) {

    }

    private void sendVerificationCode(String mobile) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91" + mobile,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks);
    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            //Getting the code sent by SMS
            String code = phoneAuthCredential.getSmsCode();

            //sometime the code is not detected automatically
            //in this case the code will be null
            //so user has to manually enter the code
            if (code != null) {
                et_otp.setText(code);
                //verifying the code
                verifyVerificationCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            //storing the verification id that is sent to the user
            mVerificationId = s;
        }
    };


    private void verifyVerificationCode(String code) {
        //creating the credential
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);

        //signing the user

        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //verification successful we will start the profile activity
                           /* Intent intent = new Intent(getActivity(), AdminFunctionalityFragment.class);
                            //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);*/
                            manager.beginTransaction().replace(R.id.fragment_container,new AdminFunctionalityFragment()).commit();

                            Toast.makeText(getActivity(),
                                    "Sucess", Toast.LENGTH_SHORT).show();

                        } else {

                            //verification unsuccessful.. display an error message
                            Toast.makeText(getActivity(),
                                    "Inavali Otp", Toast.LENGTH_SHORT).show();
                            String message = "Somthing is wrong, we will fix it soon...";

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                message = "Invalid code entered...";
                            }


                        }
                    }
                });
    }









}
