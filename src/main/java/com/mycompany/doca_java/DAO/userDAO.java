/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.doca_java.DAO;

import com.mycompany.doca_java.DTO.userDTO;
import com.mycompany.doca_java.util.DBconnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author Admin
 */
public class userDAO {

    private List<userDTO> ListOfUser;

    public List<userDTO> getListOfUser() {
        return ListOfUser;
    }

    public userDTO checkLogin(String username, String password)
            throws SQLException, NamingException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        userDTO result = null;
        try {
            //1.make connection
            con = DBconnect.makeConnection();
            if (con != null) {
                //2.create sql string
                String sql = "SELECT [user_id]\n"
                        + "      ,[Gender]\n"
                        + "      ,[email]\n"
                        + "      ,[mobile_num]\n"
                        + "      ,[status]\n"
                        + "      ,[role_id]\n"
                        + "      ,[avatar]\n"
                        + "FROM [dbo].[users]\n"
                        + "WHERE [username] = ? AND [password] = ?;";
                //3.create stm obj
                stm = con.prepareStatement(sql);
                stm.setString(1, username);
                stm.setString(2, password);
                //4.execute
                rs = stm.executeQuery();
                //5.process (Note: Luu y Khi SU DUNG IF/WHILE)
                if (rs.next()) {
//                    result = true;
                    //map:get data from ResultSet & set data to property's DTO
                    int user_ID = rs.getInt("user_id");
                    String Gender = rs.getString("Gender");
                    String email = rs.getString("email");
                    String phone = rs.getString("mobile_num");
                    boolean status = rs.getBoolean("status");
                    boolean roleID = rs.getBoolean("role_id");
                    String avatar = rs.getString("avatar");
                    result = new userDTO(user_ID, username, password, Gender, email, phone, status, roleID, avatar);
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }

        }
        return result;
    }

    public void getAllTheUser() throws SQLException, ClassNotFoundException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBconnect.makeConnection();
            if (con != null) {
                //2.create sql string
                String sql = "SELECT [user_id], [username], [password], [Gender], [email], [mobile_num], [status], [role_id], [avatar]\n"
                        + "FROM [DOCA_platform].[dbo].[users];";
                //3.create stm obj
                stm = con.prepareStatement(sql);
                //4.execute
                rs = stm.executeQuery();
                //5.process (Note: Luu y Khi SU DUNG IF/WHILE)
                while (rs.next()) {

                    int user_ID = rs.getInt("user_id");
                    String userName = rs.getString("username");
                    String password = rs.getString("password");
                    String Gender = rs.getString("Gender");
                    String email = rs.getString("email");
                    String phone = rs.getString("mobile_num");
                    boolean status = rs.getBoolean("status");
                    boolean roleID = rs.getBoolean("role_id");
                    String avatar = rs.getString("avatar");
                    //5.1.2 set properties of pro
                    userDTO user
                            = new userDTO(user_ID, userName, password, Gender, email, phone, status, roleID, avatar);

                    //5.2 add data to list
                    if (this.ListOfUser
                            == null) {
                        this.ListOfUser = new ArrayList<>();

                    }//end account list has not existed
                    if (user.isRoleID()) {
                        this.ListOfUser.add(user);
                    }
                }//end map DB to DTO
            }//end connect is available
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (stm != null) {
                con.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    public userDTO getUserbyProductID(int productID)
            throws SQLException, ClassNotFoundException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        userDTO user = null;
        try {
            con = DBconnect.makeConnection();
            if (con != null) {
                //2.create sql string
                String sql = "SELECT u.[user_id], u.[username], u.[password], u.[Gender], u.[email], u.[mobile_num], u.[status], u.[role_id], u.[avatar]\n"
                        + "FROM [DOCA_platform].[dbo].[users] u\n"
                        + "INNER JOIN [DOCA_platform].[dbo].[product] p ON u.[user_id] = p.[user_id]\n"
                        + "WHERE p.[product_id] = ?";
                //3.create stm obj
                stm = con.prepareStatement(sql);
                stm.setInt(1, productID);
                //4.execute
                rs = stm.executeQuery();
                //5.process (Note: Luu y Khi SU DUNG IF/WHILE)
                while (rs.next()) {

                    int user_ID = rs.getInt("user_id");
                    String userName = rs.getString("username");
                    String password = rs.getString("password");
                    String Gender = rs.getString("Gender");
                    String email = rs.getString("email");
                    String phone = rs.getString("mobile_num");
                    boolean status = rs.getBoolean("status");
                    boolean roleID = rs.getBoolean("role_id");
                    String avatar = rs.getString("avatar");
                    //5.1.2 set properties of pro
                    user
                            = new userDTO(user_ID, userName, password, Gender, email, phone, status, roleID, avatar);

                }//end map DB to DTO
            }//end connect is available
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (stm != null) {
                con.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return user;
    }

    public boolean createUser(userDTO user) throws SQLException, ClassNotFoundException, NamingException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBconnect.makeConnection();
            if (con != null) {
                String sql = "INSERT INTO users ([username], "
                        + "[password], "
                        + "[Gender], "
                        + "[email], "
                        + "[mobile_num], "
                        + "[status], "
                        + "[role_id], "
                        + "[avatar]) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                ps = con.prepareStatement(sql);
                ps.setString(1, user.getUserName());
                ps.setString(2, user.getPassword());
                ps.setString(3, user.getGender());
                ps.setString(4, user.getEmail());
                ps.setString(5, user.getMobileNum());
                ps.setBoolean(6, user.isStatus());
                ps.setBoolean(7, user.isRoleID());
                ps.setString(8, user.getAvatar());
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    return true;
                }
            }
        } finally {
            if (ps != null) {
                con.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return false;
    }

    public boolean isUsernameAvailable(String username) {
        Connection con = null;
        try {
            con = DBconnect.makeConnection();
            if (con != null) {
                String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, username);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count == 0; // If count is 0, the username is available
                }
            }
        } catch (SQLException | ClassNotFoundException | NamingException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isEmailAvailable(String email) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBconnect.makeConnection();
            if (con != null) {
                String sql = "SELECT COUNT(*) FROM users WHERE email = ?";
                ps = con.prepareStatement(sql);
                ps.setString(1, email);
                rs = ps.executeQuery();

                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count == 0; // If count is 0, the email is available; otherwise, it's taken.
                }
            }
        } catch (SQLException | ClassNotFoundException | NamingException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (ps != null) {
                con.close();
            }
            if (con != null) {
                con.close();
            }
        }

        return false; // Return false in case of an error
    }
     public userDTO getUserbyPostID(int postID)
            throws SQLException, ClassNotFoundException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        userDTO user=null;
        try {
            con = DBconnect.makeConnection();
            if (con != null) {
                //2.create sql string
                String sql = "SELECT u.[user_id], u.[username], u.[password], u.[Gender], u.[email], u.[mobile_num], u.[status], u.[role_id], u.[avatar]\n"
                        + "FROM [DOCA_platform].[dbo].[users] u\n"
                        + "INNER JOIN [DOCA_platform].[dbo].[post] po ON u.[user_id] = po.[user_id]\n"
                        + "WHERE po.[post_id] = ?";
                //3.create stm obj
                stm = con.prepareStatement(sql);
                stm.setInt(1, postID);
                //4.execute
                rs = stm.executeQuery();
                //5.process (Note: Luu y Khi SU DUNG IF/WHILE)
                while (rs.next()) {

                    int user_ID = rs.getInt("user_id");
                    String userName = rs.getString("username");
                    String password = rs.getString("password");
                    String Gender = rs.getString("Gender");
                    String email = rs.getString("email");
                    String phone = rs.getString("mobile_num");
                    boolean status = rs.getBoolean("status");
                    boolean roleID = rs.getBoolean("role_id");
                    String avatar = rs.getString("avatar");
                    //5.1.2 set properties of pro
                    user
                            = new userDTO(user_ID, userName, password, Gender, email, phone, status, roleID, avatar);

                }//end map DB to DTO
            }//end connect is available
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (stm != null) {
                con.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return user;
    }

}
