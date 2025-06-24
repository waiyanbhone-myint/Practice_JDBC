package com.ps.service;

import com.ps.dao.interfaces.BookDAO;
import com.ps.dao.interfaces.BorrowedBookDAO;
import com.ps.dao.interfaces.MemberDAO;

public class LibraryService {
    private BookDAO bookDAO;
    private BorrowedBookDAO borrowedBookDAO;
    private MemberDAO memberDAO;

    public LibraryService(BookDAO bookDAO, BorrowedBookDAO borrowedBookDAO, MemberDAO memberDAO) {
        this.bookDAO = bookDAO;
        this.borrowedBookDAO = borrowedBookDAO;
        this.memberDAO = memberDAO;
    }

    public void borrowBook(int bookId, int memberId, String dueDate){
        if(bookDAO.getBookById(bookId)!=null){
            var book = bookDAO.getBookById(bookId);
            if(book.getTotal_copies() > 0){

            }else{
                System.out.println("No copies available.");
            }

        }else {
            System.out.println("Book is not in the database");
        }

    }
}
