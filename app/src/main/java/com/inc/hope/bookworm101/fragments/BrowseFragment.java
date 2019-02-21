package com.inc.hope.bookworm101.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.inc.hope.bookworm101.R;
import com.inc.hope.bookworm101.Utils.GenreAdapter;
import com.inc.hope.bookworm101.Utils.GenreDetail;
import com.inc.hope.bookworm101.Utils.SpacesItemDecoration;

import java.util.ArrayList;

import static com.inc.hope.bookworm101.Utils.Constants.genres;


public class BrowseFragment extends Fragment {


    public BrowseFragment() {
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
        View view =  inflater.inflate(R.layout.fragment_browse, container, false);
        initView(view);
        return view;
    }

    private void initView(View view){
        RecyclerView recyclerView = view.findViewById(R.id.rv_browse);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<GenreDetail> genreDetails = prepareData();
        GenreAdapter genreAdapter = new GenreAdapter(getActivity(), genreDetails);
        int spacingInPixels = 34;
        recyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        recyclerView.setAdapter(genreAdapter);




    }

    private ArrayList<GenreDetail> prepareData(){
        ArrayList<GenreDetail> genreList = new ArrayList<>();
        for (int i = 0; i < genres.length; i++){
            GenreDetail genreDetail = new GenreDetail();
            genreDetail.setGenre_name(genres[i]);
            genreList.add(genreDetail);
        }

        return genreList;
    }

}
