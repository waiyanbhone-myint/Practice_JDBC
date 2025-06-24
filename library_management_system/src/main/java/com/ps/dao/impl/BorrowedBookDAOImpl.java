package com.ps.dao.impl;

import com.ps.dao.interfaces.BookDAO;
import com.ps.dao.interfaces.BorrowedBookDAO;
import com.ps.dao.interfaces.MemberDAO;
import com.ps.model.Book;
import com.ps.model.BorrowedBook;
import com.ps.model.Member;
import org.apache.commons.dbcp2.BasicDataSource;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class BorrowedBookDAOImpl implements BorrowedBookDAO {

    Properties properties = new Properties();

    private void loadProperties() {

        try (var input = getClass().getClassLoader().getResourceAsStream("application.properties");) {
            if (input == null) {
                throw new RuntimeException("Unable to find properties file.");
            }
            properties.load(input);

        } catch (IOException e) {
            throw new RuntimeException("Error loading properties file.");
        }
    }

    private BasicDataSource createDataSource() {
        var basicDataSource = new BasicDataSource();
        String serverName = properties.getProperty("serverName");
        int port = Integer.parseInt(properties.getProperty("port"));
        String databaseName = properties.getProperty("databaseName");

        basicDataSource.setUsername(System.getenv("MYSQL_USER"));
        basicDataSource.setPassword(System.getenv("MYSQL_PASSWORD"));

        basicDataSource.setUrl("jdbc:mysql://" + serverName + ":" + port + "/" + databaseName);

        return basicDataSource;
    }

    @Override
    public List<BorrowedBook> getAllBorrowedBook() {
        List<BorrowedBook> borrowedBooks = new ArrayList<>();

        String query = "SELECT * FROM borrowed_books";
        loadProperties();

        try (var connection = createDataSource().getConnection();
             var preparedStatement = connection.prepareStatement(query);
             var resultSet = preparedStatement.executeQuery();
        ) {
            while (resultSet.next()) {
                int borrowId = resultSet.getInt("borrow_id");
                int bookId = resultSet.getInt("book_id");
                int memberId = resultSet.getInt("member_id");
                String borrowDate = resultSet.getString("borrow_date");
                String dueDate = resultSet.getString("due_date");
                String returnDate = resultSet.getString("return_date");

                var borrowedBook = new BorrowedBook(borrowId, bookId, memberId, borrowDate, dueDate, returnDate);

                borrowedBooks.add(borrowedBook);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return borrowedBooks;
    }

    @Override
    public BorrowedBook getBorrowedBookById(int id) {
        String query = "SELECT * FROM borrowed_books WHERE borrow_id = ?";

        loadProperties();

        try (var connection = createDataSource().getConnection();
             var prepareStatement = connection.prepareStatement(query);
        ) {
            prepareStatement.setInt(1, id);
            var resultSet = prepareStatement.executeQuery();

            if(resultSet.next()){
                int borrow_id = resultSet.getInt("borrow_id");
                int book_id = resultSet.getInt("book_id");
                int member_id = resultSet.getInt("member_id");
                String borrow_date = resultSet.getString("borrow_date");
                String due_date = resultSet.getString("due_date");
                String return_date = resultSet.getString("return_date");

                return new BorrowedBook(borrow_id, book_id, member_id, borrow_date, due_date, return_date);
            }
            return null;


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insertBorrowedBook(BorrowedBook borrowedBook) {
        BookDAO bookDAO = new BookDAOImpl();
        MemberDAO memberDAO = new MemberDAOImpl();

        Book book = bookDAO.getBookById(borrowedBook.getBook_id());
        Member member = memberDAO.getMemberById(borrowedBook.getMember_id());

        if(book == null){
            throw new RuntimeException("Book with Id " + borrowedBook.getBorrow_id() + " does not exit.");
        }
        if(member == null){
            throw new RuntimeException("Member with Id " + borrowedBook.getMember_id() + " does not exit.");
        }

        String query = "INSERT INTO borrowed_books (book_id, member_id, borrow_date, due_date) VALUES (?, ?, ?, ?)";
        loadProperties();

        try (var connection = createDataSource().getConnection();
             var preparedStatement = connection.prepareStatement(query);
        ) {

            preparedStatement.setInt(1, borrowedBook.getBook_id());
            preparedStatement.setInt(2, borrowedBook.getMember_id());
            preparedStatement.setString(3, borrowedBook.getBorrow_date());
            preparedStatement.setString(4, borrowedBook.getDue_date());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void updateBorrowedBook(BorrowedBook borrowedBook) {
        String query = "UPDATE borrowed_books SET book_id = ?, member_id = ?, borrow_date = ?, due_date = ?, return_date = ? WHERE borrow_id = ?";

        loadProperties();

        try (var connection = createDataSource().getConnection();
             var preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setInt(1, borrowedBook.getBook_id());
            preparedStatement.setInt(2, borrowedBook.getMember_id());
            preparedStatement.setString(3, borrowedBook.getBorrow_date());
            preparedStatement.setString(4, borrowedBook.getDue_date());
            preparedStatement.setString(5, borrowedBook.getReturn_date());
            preparedStatement.setInt(6, borrowedBook.getBorrow_id());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void deleteBorrowedBook(int id) {
        String query = "DELETE FROM borrowed_books WHERE borrow_id = ? ";

        loadProperties();

        try (var connection = createDataSource().getConnection();
             var preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
