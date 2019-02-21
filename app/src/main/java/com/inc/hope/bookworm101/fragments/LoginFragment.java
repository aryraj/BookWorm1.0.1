package com.inc.hope.bookworm101.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.inc.hope.bookworm101.Activity.MainActivity;
import com.inc.hope.bookworm101.R;

/**
 * Created by aryan on 19/11/17.
 */

public class LoginFragment extends Fragment {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private EditText ed_login_email, ed_login_password;
    private String str_login_email, str_login_password;
    private Button btn_login;
    private ProgressDialog mProgressDialog;

    private Typeface tf;

    public LoginFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        mAuth = FirebaseAuth.getInstance();
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setMessage("Logging In");

        tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Walkway_Bold.ttf");

        perform(view);
        return view;
    }

    private void signIn(String email, String password){
        mProgressDialog.show();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("TAG", "signInWithEmail:onComplete:" + task.isSuccessful());
                        mProgressDialog.dismiss();

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w("TAG", "signInWithEmail:failed", task.getException());
                            Toast.makeText(getActivity().getApplicationContext(), "Authentication Failed! User not registered.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            startActivity(new Intent(getActivity().getApplicationContext(), MainActivity.class));
                        }

                        // ...
                    }
                });

    }

    private void perform(View view){
        ed_login_email = view.findViewById(R.id.ed_login_email);
        ed_login_email.setTypeface(tf);
        ed_login_password = view.findViewById(R.id.ed_login_password);
        ed_login_password.setTypeface(tf);

        btn_login = view.findViewById(R.id.btn_login);
        btn_login.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_login_email = ed_login_email.getText().toString().trim();
                str_login_password = ed_login_password.getText().toString().trim();
                signIn(str_login_email, str_login_password);
            }
        });
    }

}
