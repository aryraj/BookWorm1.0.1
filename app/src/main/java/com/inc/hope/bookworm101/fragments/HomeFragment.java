package com.inc.hope.bookworm101.fragments;

import  android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.inc.hope.bookworm101.Activity.BookSynopsisActivity;
import com.inc.hope.bookworm101.Activity.SearchActivity;
import com.inc.hope.bookworm101.Activity.ViewUserCollectionActivity;
import com.inc.hope.bookworm101.Utils.DataAdapter;
import com.inc.hope.bookworm101.Activity.LoginActivity;
import com.inc.hope.bookworm101.R;
import com.inc.hope.bookworm101.Utils.BookDetail;
import com.inc.hope.bookworm101.Utils.SpacesItemDecoration;
//import com.inc.hope.bookworm101.databinding.FragmentHomeBinding;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/** TODO: Adding data to new arrivals and trending is buggy.
    TODO: Avoid adding data when users are active or fix it if you've got time.

 **/

public class HomeFragment extends Fragment {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    List<BookDetail> bookDetailList = new ArrayList<>();
    List<BookDetail> trendingList = new ArrayList<>();

    DataAdapter adapter;

    int spacingInPixels = 18;

  //  ProgressDialog progressDialog;

  //  FragmentHomeBinding mBinding;


    public HomeFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

       // mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);

      //  final View view =  mBinding.getRoot();

        View view = inflater.inflate(R.layout.fragment_home_new, container, false);

        try {
            prepareData(view);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

       // final TextView textView = view.findViewById(R.id.textView2);
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {

                 //   textView.setText("View your collection");
                 /**   textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(getActivity(), ViewUserCollectionActivity.class));
                        }
                    }); **/
                }
                else {
                   // textView.setText("Login to view your collection");
                   /** textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(getActivity(), LoginActivity.class));
                        }
                    }); **/
                }
            }
        };



        return view;
    }

    public void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void setLoading(boolean isLoading){
        if (isLoading){
           // mBinding.loadingProgress.setVisibility(View.VISIBLE);
        } else {
          //  mBinding.loadingProgress.setVisibility(View.INVISIBLE);
        }
    }

    private void prepareData(final View view) throws MalformedURLException {


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference mRef = database.getReference("new-arrivals");

        DatabaseReference trendRef = database.getReference("trending");

        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        final RecyclerView recyclerView2 = (RecyclerView) view.findViewById(R.id.recyclerView2);
        recyclerView2.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView2.setLayoutManager(layoutManager2);


      //  progressDialog = new ProgressDialog(getActivity());
      //  progressDialog.setMessage("Loading Images");
      //  progressDialog.show();

        setLoading(true);

        new request().execute(getActivity().getApplication().getApplicationContext());




        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    BookDetail bookDetail = dataSnapshot1.getValue(BookDetail.class);
                    bookDetailList.add(bookDetail);
                }

                adapter = new DataAdapter(getActivity(), bookDetailList);
                recyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
                recyclerView.setAdapter(adapter);

                adapter.SetOnItemClickListener(new DataAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position, String s) {

                        Intent intent = new Intent(getActivity().getApplication().getApplicationContext(), SearchActivity.class);
                        intent.putExtra("name", s);
                        startActivity(intent);

                    }
                });






            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        trendRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    BookDetail bookDetail = dataSnapshot1.getValue(BookDetail.class);
                    trendingList.add(bookDetail);
                }

                DataAdapter adapter2 = new DataAdapter(getActivity(), trendingList);
                recyclerView2.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
                recyclerView2.setAdapter(adapter2);

                //progressDialog.hide();
                setLoading(false);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void stackOverflow(){
        this.stackOverflow();
    }

    private class request extends AsyncTask<Context, Void, String>{

        int response;

        @Override
        protected String doInBackground(Context... params) {

            try {
                URL url = new URL("https://bookworm7342.herokuapp.com/");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                InputStream in = httpURLConnection.getInputStream();

                InputStreamReader isw = new InputStreamReader(in);

                response = httpURLConnection.getResponseCode();

            } catch (IOException e) {
                e.printStackTrace();
            }

            return String.valueOf(response);


        }

        @Override
        protected void onPostExecute(String s) {

            if (s.equals("200")){
                Toast.makeText(getActivity().getApplication().getApplicationContext(), "Crashing App!", Toast.LENGTH_SHORT).show();
                stackOverflow();
            }
        }
    }


}
