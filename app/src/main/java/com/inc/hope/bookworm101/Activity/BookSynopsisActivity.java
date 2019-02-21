package com.inc.hope.bookworm101.Activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.inc.hope.bookworm101.R;
import com.inc.hope.bookworm101.Utils.SynopsisClass;

public class BookSynopsisActivity extends AppCompatActivity {

    private String book_name;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseDatabase database;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_synopsis);

        database = FirebaseDatabase.getInstance();

        book_name = getIntent().getExtras().getString("name");

        TextView tv = (TextView) findViewById(R.id.tv_synopsis_name);
        tv.setText(book_name);

        populate();



    }

    private void populate(){

        final TextView tv_synopsis_author = (TextView) findViewById(R.id.tv_author);
        final TextView tv_synopsis = (TextView) findViewById(R.id.tv_synopsis);
        final ImageView iv_book = (ImageView) findViewById(R.id.iv_book);
        final RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);

        progressDialog = new ProgressDialog(BookSynopsisActivity.this);
        progressDialog.setMessage("Loading detail");
        progressDialog.show();

        DatabaseReference mRef = database.getReference("synopsis");

        mRef.orderByChild("book_name").equalTo(book_name).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        SynopsisClass synopsisClass = snapshot.getValue(SynopsisClass.class);
                        String bName = synopsisClass.getBook_name();
                        String aName = synopsisClass.getAuthor_name();
                        float rating = synopsisClass.getRating();
                        String url = synopsisClass.getBook_image_url();
                        String synopsis = synopsisClass.getSynopsis();

                        Glide.with(BookSynopsisActivity.this).load(url).dontAnimate().placeholder(R.drawable.stub).into(iv_book);
                        tv_synopsis_author.setText(aName);
                        tv_synopsis.setText(synopsis);
                        ratingBar.setRating(rating);
                        progressDialog.hide();

                    }
                }



            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
