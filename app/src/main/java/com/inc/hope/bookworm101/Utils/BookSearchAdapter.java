package com.inc.hope.bookworm101.Utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.inc.hope.bookworm101.databinding.BookSearchLayoutBinding;

/**
 * Created by aryan on 09/12/17.
 */

public class BookSearchAdapter extends ArrayAdapter<String> {


    public BookSearchAdapter(@NonNull Context context) {
        super(context, 0);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        BookSearchLayoutBinding mBinding;

        if (convertView == null){
            mBinding = BookSearchLayoutBinding.inflate(LayoutInflater.from(getContext()), parent, false);
            convertView = mBinding.getRoot();
            convertView.setTag(mBinding);
        }else {
            mBinding = (BookSearchLayoutBinding) convertView.getTag();
        }

        mBinding.textTitle.setText("Aryan");

      /**  BookInfo bookInfo = getItem(position);
        if (bookInfo != null){
            mBinding.thumbnail.setImageBitmap(bookInfo.getImage());
            mBinding.textTitle.setText(bookInfo.getTitle());
            mBinding.textAuthor.setText(bookInfo.getAuthor());
        }else {
            mBinding.textTitle.setText("null");
            mBinding.textAuthor.setText("null");
        } **/

        return convertView;

    }
}
