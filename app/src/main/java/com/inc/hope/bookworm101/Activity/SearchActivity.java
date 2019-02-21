package com.inc.hope.bookworm101.Activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Toast;

import com.inc.hope.bookworm101.R;
import com.inc.hope.bookworm101.Utils.BookInfo;
import com.inc.hope.bookworm101.Utils.BookSearchAdapter;
import com.inc.hope.bookworm101.databinding.ActivitySearchBinding;

public class SearchActivity extends AppCompatActivity {

    private ActivitySearchBinding mBinding;
    private String BUNDLE_POSITION = "BUNDLE_POSITION";
    private int mFirstVisible;
    private BookSearchAdapter mAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //This method receives the parameter savedInstanceState, which is a Bundle object containing
        //the activity's previously saved state. If the activity has never existed before, the value
        //of the Bundle object is null.

        super.onCreate(savedInstanceState);
        if (savedInstanceState != null){
            mFirstVisible = savedInstanceState.getInt(BUNDLE_POSITION, 0);
        }

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        //This is to view the textview when the listview is empty.
        // textSearch is the id of the textview.
        mBinding.listView.setEmptyView(mBinding.textSearch);
        mAdapter = new BookSearchAdapter(this);

        mAdapter.add("Aryan");

        mBinding.listView.setAdapter(mAdapter);
       /** mBinding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                BookInfo bookInfo = mAdapter.getItem(position);

                if (bookInfo != null){

                    Toast.makeText(SearchActivity.this, "Pressed : " + bookInfo.getTitle(), Toast.LENGTH_SHORT).show();

                }
            }
        }); **/
        hideKeyboard();
        checkConnectivity();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(BUNDLE_POSITION, mBinding.listView.getFirstVisiblePosition());
    }

    public void onSearchButtonClick(View view) {

    }

    private boolean checkConnectivity(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = networkInfo != null && networkInfo.isConnectedOrConnecting();
        if (!isConnected){
            mBinding.textSearch.setText("No Internet Connection");

        }
        return isConnected;
    }

    private void hideKeyboard(){
        View view = this.getCurrentFocus();
        if (view != null){
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        mBinding.focusGainer.requestFocus();

    }

}
