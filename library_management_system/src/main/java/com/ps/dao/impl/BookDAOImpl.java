package com.ps.dao.impl;

import com.mysql.cj.jdbc.result.UpdatableResultSet;
import com.ps.dao.interfaces.BookDAO;
import com.ps.model.Book;
import org.apache.commons.dbcp2.BasicDataSource;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class BookDAOImpl implements BookDAO {

    Properties properties = new Properties();

    private void loadProperties() {
        try (var input = getClass().getClassLoader()
                .getResourceAsStream("application.properties")) {

            if (input == null) {
                throw new RuntimeException("Unable to find application.properties.");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Error loading properties file", e);
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
    public List<Book> getAllBook() {

        loadProperties();

        String query = "SELECT * FROM books";

        List<Book> books = new ArrayList<>();

        try (var connection = createDataSource().getConnection();
             var preparedStatement = connection.prepareStatement(query);
             var resultSet = preparedStatement.executeQuery();
        ) {

            while (resultSet.next()) {
                int bookId = resultSet.getInt("book_id");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                String isbn = resultSet.getString("isbn");
                int availableCopies = resultSet.getInt("available_copies");
                int totalCopies = resultSet.getInt("total_copies");

                Book book = new Book(bookId, title, author, isbn, availableCopies, totalCopies);

                books.add(book);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return books;
    }

    @Override
    public Book getBookById(int id) {

        loadProperties();

        String query = "SELECT * FROM books WHERE book_id = ?";

        try (var connection = createDataSource().getConnection();
             var preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setInt(1, id);
            var resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int bookId = resultSet.getInt("book_id");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                String isbn = resultSet.getString("isbn");
                int availableCopies = resultSet.getInt("available_copies");
                int totalCopies = resultSet.getInt("total_copies");

                return new Book(bookId, title, author, isbn, availableCopies, totalCopies);
            }

            return null;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insertBook(Book book) {

        loadProperties();

        String query = "INSERT INTO books (title, author, isbn, available_copies, total_copies) VALUES (?, ?, ?, ?, ?)";

        try (var connection = createDataSource().getConnection();
             var preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setString(3, book.getIsbn());
            preparedStatement.setInt(4, book.getAvailable_copies());
            preparedStatement.setInt(5, book.getTotal_copies());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void updateBook(Book book) {

        loadProperties();

        String query = "UPDATE books SET title = ?, author = ?, isbn = ?, available_copies = ?, total_copies = ? WHERE book_id = ?";

        try (var connection = createDataSource().getConnection();
             var preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setString(3, book.getIsbn());
            preparedStatement.setInt(4, book.getAvailable_copies());
            preparedStatement.setInt(5, book.getTotal_copies());
            preparedStatement.setInt(6, book.getBookid());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void deleteBook(int id) {

        loadProperties();

        String query = "DELETE FROM books WHERE book_id = ?";

        try (var connection = createDataSource().getConnection();
             var preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

    }


}
