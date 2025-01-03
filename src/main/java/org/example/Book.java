package org.example;

import java.util.Objects;

public class Book {
    private String title;
    private String author;
    private String price;
    private boolean isBestSeller;

    public Book(String title, String author, String price, boolean isBestSeller) {
        this.title = title;
        this.author = author;
        this.price = price;
        this.isBestSeller = isBestSeller;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", price='" + price + '\'' +
                ", isBestSeller=" + isBestSeller +
                '}';
    }


    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;
        Book book = (Book) obj;
        return Objects.equals(title, book.title) &&
                Objects.equals(author, book.author) &&
                Objects.equals(price, book.price) &&
                isBestSeller == book.isBestSeller;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getPrice() { return price; }
    public void setPrice(String price) { this.price = price; }

    public boolean isBestSeller() { return isBestSeller; }
    public void setBestSeller(boolean bestSeller) { isBestSeller = bestSeller; }

}
