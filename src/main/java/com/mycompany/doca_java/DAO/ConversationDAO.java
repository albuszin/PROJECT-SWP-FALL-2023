/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.doca_java.DAO;

import com.mycompany.doca_java.DTO.ConversationDTO;
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
public class ConversationDAO {

    private List<ConversationDTO> ListOfConversation;

    public List<ConversationDTO> getListOfConversation() {
        return ListOfConversation;
    }

    public void getListTheConversationByUserID(int user_id) throws SQLException, ClassNotFoundException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBconnect.makeConnection();
            if (con != null) {
                //2.create sql string
                String sql = " SELECT [conversation_id], [product_id], [buyer_id], [seller_id]\n"
                        + "    FROM [dbo].[conversation]\n"
                        + "    WHERE [buyer_id] = ? OR [seller_id] = ?";
                //3.create stm obj
                stm = con.prepareStatement(sql);
                stm.setInt(1, user_id);
                stm.setInt(2, user_id);
                //4.execute
                rs = stm.executeQuery();
                //5.process (Note: Luu y Khi SU DUNG IF/WHILE)
                while (rs.next()) {
                    int conversation_id = rs.getInt("conversation_id");
                    int product_id = rs.getInt("product_id");
                    int buyer_id = rs.getInt("buyer_id");
                    int seller_id = rs.getInt("seller_id");

                    ConversationDTO conversation= new ConversationDTO(conversation_id, product_id, buyer_id, seller_id);
                    if (this.ListOfConversation
                            == null) {
                        this.ListOfConversation = new  ArrayList<>();
                    }
                    this.ListOfConversation.add(conversation);
                }
            }
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

}
