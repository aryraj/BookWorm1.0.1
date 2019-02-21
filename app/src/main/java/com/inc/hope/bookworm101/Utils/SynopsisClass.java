package com.inc.hope.bookworm101.Utils;

/**
 * Created by aryan on 04/12/17.
 */

public class SynopsisClass {

    private String book_name;
    private String author_name;
    private String book_image_url;
    private float rating;
    private String synopsis;

    public SynopsisClass(){

    }

    public SynopsisClass(String bName, String aName, String url, float rating, String synopsis){
        this.book_name = bName;
        this.author_name = aName;
        this.book_image_url = url;
        this.rating = rating;
        this.synopsis = synopsis;
    }

    public float getRating() {
        return this.rating;
    }

    public String getAuthor_name() {
        return this.author_name;
    }

    public String getBook_image_url() {
        return this.book_image_url;
    }

    public String getBook_name() {
        return this.book_name;
    }

    public String getSynopsis() {
        return this.synopsis;
    }
}
