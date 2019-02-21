package com.inc.hope.bookworm101.Utils;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.inc.hope.bookworm101.Activity.BookSynopsisActivity;
import com.inc.hope.bookworm101.R;

import java.util.List;

/**
 * Created by aryan on 18/11/17.
 */

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    private List<BookDetail> book;
    private Context context;
    OnItemClickListener mItemClickListener;

    public DataAdapter(Context context, List<BookDetail> book){
        this.book = book;
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tv_book_name.setText(book.get(position).getBook_name());
        Glide.with(context).load(book.get(position).getBook_image_url()).dontAnimate().placeholder(R.drawable.stub).into(holder.iv_book_cover);

    }

    @Override
    public int getItemCount() {

        return book.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView tv_book_name;
        private ImageView iv_book_cover;
        private CardView cardView;




        public ViewHolder(View itemView) {
            super(itemView);

            tv_book_name = (TextView)itemView.findViewById(R.id.tv_book_name);
            iv_book_cover = (ImageView) itemView.findViewById(R.id.iv_book_cover);
            cardView = itemView.findViewById(R.id.cardView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(itemView, getLayoutPosition(), tv_book_name.getText().toString());
            }

        }
    }
    public interface OnItemClickListener {
        public void onItemClick(View view , int position, String s);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
}
