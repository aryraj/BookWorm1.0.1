package com.inc.hope.bookworm101.Utils;

import android.os.Parcel;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

/**
 * Created by aryan on 04/12/17.
 */


// TODO:// Complete book sugegstion.

public class BookSuggestion implements SearchSuggestion {

    private String mBookName;
    private boolean mIsHistory = false;

    public BookSuggestion(String string){
        this.mBookName = string.toLowerCase();
    }


    public BookSuggestion(Parcel parcel){
        this.mBookName = parcel.readString();
        this.mIsHistory = parcel.readInt() != 0;
    }

    public void setIsHistory(boolean isHistory){
        this.mIsHistory = isHistory;
    }

    public boolean getIsHistory(){
        return this.mIsHistory;
    }

    @Override
    public String getBody() {
        return mBookName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BookSuggestion> CREATOR = new Creator<BookSuggestion>() {
        @Override
        public BookSuggestion createFromParcel(Parcel source) {
            return new BookSuggestion(source);
        }

        @Override
        public BookSuggestion[] newArray(int size) {
            return new BookSuggestion[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mBookName);
        dest.writeInt(mIsHistory ? 1 : 0);

    }
}
