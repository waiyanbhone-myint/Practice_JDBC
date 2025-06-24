package com.ps;

import com.ps.dao.impl.BookDAOImpl;
import com.ps.dao.impl.BorrowedBookDAOImpl;
import com.ps.dao.impl.MemberDAOImpl;
import com.ps.dao.interfaces.BookDAO;
import com.ps.dao.interfaces.MemberDAO;
import com.ps.model.Book;
import com.ps.model.BorrowedBook;
import com.ps.model.Member;

public class Main {
    public static void main(String[] args) {

        var borrowedBookDaoImpl = new BorrowedBookDAOImpl();

        System.out.println("Deleting data to DB");
        System.out.println("====================");
        borrowedBookDaoImpl.deleteBorrowedBook(14);
        for(var book : borrowedBookDaoImpl.getAllBorrowedBook()){
            System.out.printf("%-10s %-10s %-10s %-20s %-20s %-20s %n",
                    book.getBorrow_id(),
                    book.getBook_id(),
                    book.getMember_id(),
                    book.getBorrow_date(),
                    book.getDue_date(),
                    book.getReturn_date());
        }
    }
}