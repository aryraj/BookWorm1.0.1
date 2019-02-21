package com.inc.hope.bookworm101.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.SearchSuggestionsAdapter;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.inc.hope.bookworm101.R;
import com.inc.hope.bookworm101.Utils.BookSearchAdapter;
import com.inc.hope.bookworm101.Utils.Constants;
import com.inc.hope.bookworm101.databinding.ListLayoutBinding;
import com.inc.hope.bookworm101.fragments.BrowseFragment;
import com.inc.hope.bookworm101.fragments.HomeFragment;
import com.inc.hope.bookworm101.fragments.NewsfeedFragment;

import java.util.ArrayList;
import java.util.HashMap;


/**   TODO: 1. COMPLETE THE WHOLE FRONT END : EditProfileActivity, Adding new books to ViewUserCollection, NewArrivalBookDetail - NestedLayout
 *    TODO: 2. Add Google and Facebook Sign Up and Log In
 *    TODO: 3. Configure Google Books API
 *    TODO: 4. Add Browse functionality. Genre based book classification without searching
 *    TODO: 5. Add Firebase Phone Verification : Last Priority
 *    TODO: 6. Complete the firebase back-end
  */


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private NavigationView navigationView;
    private String name;

    private final String book_names[] = Constants.book_names;

    private final String book_urls[] = Constants.book_urls;

    private FloatingSearchView mSearchView;

    private BookSearchAdapter mAdapter;

    Fragment fragment = null;
    Class fragmentClass = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSearchView = (FloatingSearchView) findViewById(R.id.toolbar);

        fragmentClass = HomeFragment.class;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }





        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_fragment, fragment).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        mSearchView.attachNavigationDrawerToMenuButton(drawer);
        mSearchView.setBackground(getResources().getDrawable(R.drawable.tv_capsule));

        setUpSearchBar();

        navigationView = (NavigationView) findViewById(R.id.nav_view);




        navigationView.setNavigationItemSelectedListener(MainActivity.this);
        View headerView = navigationView.getHeaderView(0);

        final TextView tv_nav_header = (TextView) headerView.findViewById(R.id.tv_nav_header_main);
        ImageView imageView = (ImageView) headerView.findViewById(R.id.imageView);


        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){
                    // User is signed in
                    String email = user.getEmail();
                    navigationView.getMenu().clear();
                    navigationView.inflateMenu(R.menu.activity_main_drawer_login);
                    tv_nav_header.setText(email);
                    tv_nav_header.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            return;
                        }
                    });


                } else {
                    // User is not signed in
                    navigationView.getMenu().clear();
                    navigationView.inflateMenu(R.menu.activity_main_drawer);
                    tv_nav_header.setText("Sign In or Register");
                    tv_nav_header.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        }
                    });


                }
            }
        };





    }

    @Override
    public void onStart() {
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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        }
        else {
            super.onBackPressed();
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_search){
                startActivity(new Intent(MainActivity.this, BookSynopsisActivity.class));

        }

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        Class fragmentClass = null;

        if (id == R.id.nav_home) {
            fragmentClass = HomeFragment.class;

        } else if (id == R.id.nav_newsfeed) {
            fragmentClass = NewsfeedFragment.class;

        } else if (id == R.id.nav_browse) {
            fragmentClass = BrowseFragment.class;

        } else if (id == R.id.nav_share) {
            shareApp();

        } else if (id == R.id.nav_my_acc){

        } else if (id == R.id.nav_order_history){

        }  else if (id == R.id.nav_about){

        } else if (id == R.id.nav_feedback){

        } else if (id == R.id.nav_sign_out) {
            mAuth.signOut();
            fragmentClass = HomeFragment.class;

        }

            try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }








        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_from_left);

        FragmentManager fragmentManager = getSupportFragmentManager();

        // Replace fragmentCotainer with your container id
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.main_fragment);
        // Return if the class are the same
        if(currentFragment.getClass().equals(fragment.getClass())) { return true; }


        transaction.replace(R.id.main_fragment, fragment).addToBackStack("back").commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);




        return true;

    }

    private void setUpSearchBar(){

        mSearchView.setOnMenuItemClickListener(new FloatingSearchView.OnMenuItemClickListener() {
            @Override
            public void onActionMenuItemSelected(MenuItem item) {

                if (item.getItemId() == R.id.search){
                    Toast.makeText(MainActivity.this, "Voice Search Pressed!", Toast.LENGTH_SHORT).show();
                }

            }
        });

       mSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
           @Override
           public void onSuggestionClicked(SearchSuggestion searchSuggestion) {
               
           }

           @Override
           public void onSearchAction(String currentQuery) {

           }
       });

        mSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, String newQuery) {
                if (!oldQuery.equals("") && newQuery.equals("")){
                    mSearchView.clearSuggestions();
                } else {
                    mSearchView.showProgress();
                    MainActivity.super.onSearchRequested();

                    mSearchView.hideProgress();
                }
            }
        });


    }

    private void shareApp(){

        try {

            String referral = "";
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, "Indian Pronunciation League");
            String sAux = "\nTry out this really amazing app to enhance your english pronunciation. " +
                    "Use referral code: " + referral + "\n\n";
            sAux = sAux + "https://play.google.com/store/apps/details?id=com.indianpronounciation.ipl\n\n";
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            startActivity(Intent.createChooser(i, "choose one"));
        } catch(Exception e) {
            //e.toString();
        }

    }



}
