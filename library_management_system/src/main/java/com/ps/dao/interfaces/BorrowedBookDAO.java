package com.ps.dao.interfaces;

import com.ps.model.BorrowedBook;

import java.util.List;

public interface BorrowedBookDAO {
    List<BorrowedBook> getAllBorrowedBook();
    BorrowedBook getBorrowedBookById(int id);
    void insertBorrowedBook(BorrowedBook borrowedBook);
    void updateBorrowedBook(BorrowedBook borrowedBook);
    void deleteBorrowedBook(int id);
}
