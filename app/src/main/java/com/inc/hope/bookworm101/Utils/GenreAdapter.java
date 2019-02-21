package com.inc.hope.bookworm101.Utils;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.inc.hope.bookworm101.R;
import com.inc.hope.bookworm101.fragments.NewsfeedFragment;

import java.util.ArrayList;

/**
 * Created by aryan on 23/11/17.
 */

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.ViewHolder> {

    private ArrayList<GenreDetail> arrayList;
    private Context context;

    public GenreAdapter(Context context, ArrayList<GenreDetail> arrayList){
        this.arrayList = arrayList;
        this.context = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.browse_row_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.textView.setText(arrayList.get(position).getGenre_name());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();

    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView textView;
        private CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.tv_genre);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Fragment fragment = new NewsfeedFragment();

            AppCompatActivity activity = (AppCompatActivity) v.getContext();
           activity.getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, fragment ).commit();

        }
    }

}
