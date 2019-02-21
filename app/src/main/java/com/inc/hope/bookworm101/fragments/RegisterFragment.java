package com.inc.hope.bookworm101.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.inc.hope.bookworm101.Activity.MainActivity;
import com.inc.hope.bookworm101.R;

/**
 * Created by aryan on 19/11/17.
 */

public class RegisterFragment extends Fragment {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Context context;
    private Button btn_register;
    private EditText ed_first_name, ed_last_name, ed_reg_email, ed_reg_password;
    private String str_first_name, str_last_name, str_reg_email, str_reg_password;
    private ProgressDialog mProgressDialog;

    public RegisterFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        context = getActivity().getApplicationContext();
        mAuth = FirebaseAuth.getInstance();
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setMessage("Signing Up");
        perform(view);

        return view;
    }



    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);

        }
    }

    private void createUser(final String email,final String password, final String name){
        mProgressDialog.show();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("MainActivity.this", "createUserWithEmail:onComplete:" + task.isSuccessful());
                        mProgressDialog.dismiss();

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(context, "Authentication failed. " + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(context, "Successfully registered!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context, MainActivity.class);
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(name).build();
                            user.updateProfile(profileUpdates);
                            startActivity(intent);

                        }


                    }
                });
    }

    private void perform(View view){

        ed_first_name = view.findViewById(R.id.ed_first_name);
        ed_last_name = view.findViewById(R.id.ed_last_name);
        ed_reg_email = view.findViewById(R.id.ed_register_email);
        ed_reg_password = view.findViewById(R.id.ed_register_password);



        btn_register = view.findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                str_first_name = ed_first_name.getText().toString().trim();
                str_last_name = ed_last_name.getText().toString();
                str_reg_email = ed_reg_email.getText().toString().trim();
                str_reg_password = ed_reg_password.getText().toString().trim();

                if (TextUtils.isEmpty(str_reg_email)) {
                    Toast.makeText(context, "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(str_reg_password)) {
                    Toast.makeText(context, "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (str_reg_password.length() < 6) {
                    Toast.makeText(context, "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                createUser(str_reg_email, str_reg_password, str_first_name);


            }
        });

    }

}
