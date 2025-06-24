package com.ps;

import com.ps.dao.impl.BookDAOImpl;
import com.ps.dao.impl.BorrowedBookDAOImpl;
import com.ps.dao.impl.MemberDAOImpl;
import com.ps.dao.interfaces.BookDAO;
import com.ps.dao.interfaces.BorrowedBookDAO;
import com.ps.dao.interfaces.MemberDAO;
import com.ps.model.Book;
import com.ps.model.BorrowedBook;
import com.ps.model.Member;
import com.ps.service.LibraryService;

public class Main {
    public static void main(String[] args) {

        BookDAO bookDAO = new BookDAOImpl();
        BorrowedBookDAO borrowedBookDAO = new BorrowedBookDAOImpl();
        MemberDAO memberDAO = new MemberDAOImpl();

        LibraryService libraryService = new LibraryService(bookDAO, borrowedBookDAO, memberDAO);
        libraryService.borrowBook(2, 3, "2025-06-24");


    }
}