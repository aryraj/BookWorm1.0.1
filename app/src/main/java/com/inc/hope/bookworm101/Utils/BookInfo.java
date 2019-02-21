package com.inc.hope.bookworm101.Utils;

import android.graphics.Bitmap;

/**
 * Created by aryan on 09/12/17.
 */

public class BookInfo {

    private String author;
    private String title;
    private String link;
    private String id;
    private String smallThumbnail;
    private Bitmap image;

    private BookInfo() {

    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getId() {
        return id;
    }

    public String getSmallThumbnail() {
        return smallThumbnail;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "VolumeInfo{" +
                "author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", link='" + link + '\'' +
                '}';
    }

    public static class Builder {
        private String author;
        private String title;
        private String link;
        private String id;
        private String smallThumbnail;

        public Builder() {

        }

        public Builder reset() {
            author = null;
            title = null;
            link = null;
            id = null;
            smallThumbnail = null;
            return this;
        }

        public Builder setAuthor(String author) {
            this.author = author;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setLink(String link) {
            this.link = link;
            return this;
        }

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setSmallThumbnail(String smallThumbnail) {
            this.smallThumbnail = smallThumbnail;
            return this;
        }

        public BookInfo build() {
            BookInfo result = new BookInfo();
            result.author = author;
            result.title = title;
            result.link = link;
            result.id = id;
            result.smallThumbnail = smallThumbnail;

            return result;
        }
    }
}



