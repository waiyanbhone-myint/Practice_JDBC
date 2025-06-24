package com.ps.dao.interfaces;

import com.ps.model.Book;

import java.util.List;

public interface BookDAO {

    List<Book> getAllBook();
    Book getBookById(int id);
    void insertBook(Book book);
    void updateBook(Book book);
    void deleteBook(int id);

}
