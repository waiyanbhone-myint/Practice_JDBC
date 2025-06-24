package com.ps.dao.impl;

import com.ps.dao.interfaces.MemberDAO;
import com.ps.model.Member;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class MemberDAOImpl implements MemberDAO {

    Properties properties = new Properties();

    private void loadProperties() {
        try (var input = getClass().getClassLoader().getResourceAsStream("application.properties");) {
            if (input == null) {
                throw new RuntimeException("Unable to find properties file");
            }
            properties.load(input);

        } catch (Exception e) {
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
    public List<Member> getAllMember() {

        List<Member> members = new ArrayList<>();

        String query = "SELECT * FROM members";
        loadProperties();

        try (var connection = createDataSource().getConnection();
             var preparedStatement = connection.prepareStatement(query);
             var resultSet = preparedStatement.executeQuery();
        ) {
            while (resultSet.next()) {
                int memberId = resultSet.getInt("member_id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String phone = resultSet.getString("phone");
                String membershipDate = resultSet.getString("membership_date");

                var eachMember = new Member(memberId, name, email, phone, membershipDate);

                members.add(eachMember);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return members;
    }

    @Override
    public Member getMemberById(int id) {

        String query = "SELECT * FROM members WHERE member_id = ?";

        loadProperties();

        try (var connection = createDataSource().getConnection();
             var prepareStatement = connection.prepareStatement(query);
             //var resultSet = prepareStatement.executeQuery();
        ) {
            prepareStatement.setInt(1, id);
            var resultSet = prepareStatement.executeQuery();

            if (resultSet.next()) {
                int memberId = resultSet.getInt("member_id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String phone = resultSet.getString("phone");
                String membershipDate = resultSet.getString("membership_date");

                return new Member(memberId, name, email, phone, membershipDate);
            }

            return null;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insertMember(Member member) {

        String query = "INSERT INTO members (name, email, phone, membership_date) VALUES (?, ?, ?, ?)";
        loadProperties();

        try (var connection = createDataSource().getConnection();
             var preparedStatement = connection.prepareStatement(query);
        ) {

            preparedStatement.setString(1, member.getName());
            preparedStatement.setString(2, member.getEmail());
            preparedStatement.setString(3, member.getPhone());
            preparedStatement.setString(4, member.getMemberShipDate());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void updateMember(Member member) {

        String query = "UPDATE members SET name = ?, email = ?, phone = ?, membership_date = ? WHERE member_id = ?";

        loadProperties();

        try (var connection = createDataSource().getConnection();
             var preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setString(1, member.getName());
            preparedStatement.setString(2, member.getEmail());
            preparedStatement.setString(3, member.getPhone());
            preparedStatement.setString(4, member.getMemberShipDate());
            preparedStatement.setInt(5, member.getMemberId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void deleteMember(int id) {

        String query = "DELETE FROM members WHERE member_id = ? ";

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
