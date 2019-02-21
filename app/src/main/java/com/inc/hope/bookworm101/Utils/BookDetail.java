package com.inc.hope.bookworm101.Utils;

/**
 * Created by aryan on 18/11/17.
 */


    // Getter class to get data from database and inflate into the recyclerview.
    // for setting value just un-comment the setter part


public class BookDetail {

    private String book_name;
    private String book_image_url;

    public String getBook_name(){
        return book_name;
    }

    public String getBook_image_url(){
        return book_image_url;
    }

   /** public void setBook_name(String book_name){
        this.book_name = book_name;

    }

    public void setBook_image_url(String book_image_url){
        this.book_image_url = book_image_url;
    } **/


   public BookDetail(){
        // Empty constructor required for datasnapshot.getValue() method

    }

    // This method actually reads the url and name from the database
    // in future, if you need to add author and stuff
    // just add one more parameter to this and modify the above constructors and you're good to go

    public BookDetail(String book_name, String book_image_url){
        this.book_name = book_name;
        this.book_image_url = book_image_url;
    }

}
