package com.ps.model;

public class Book {

    private int bookid;
    private String title;
    private String author;
    private String isbn;
    private int available_copies;
    private int total_copies;

    public Book(String title, String author, String isbn, int available_copies, int total_copies) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.available_copies = available_copies;
        this.total_copies = total_copies;
    }

    public Book(int bookid, String title, String author, String isbn, int available_copies, int total_copies) {
        this.bookid = bookid;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.available_copies = available_copies;
        this.total_copies = total_copies;
    }

    public int getBookid() {
        return bookid;
    }

    public void setBookid(int bookid) {
        this.bookid = bookid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getAvailable_copies() {
        return available_copies;
    }

    public void setAvailable_copies(int available_copies) {
        this.available_copies = available_copies;
    }

    public int getTotal_copies() {
        return total_copies;
    }

    public void setTotal_copies(int total_copies) {
        this.total_copies = total_copies;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookid=" + bookid +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", isbn='" + isbn + '\'' +
                ", available_copies=" + available_copies +
                ", total_copies=" + total_copies +
                '}';
    }
}
