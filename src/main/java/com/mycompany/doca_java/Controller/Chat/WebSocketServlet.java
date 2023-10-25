/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.doca_java.Controller.Chat;


import com.mycompany.doca_java.DAO.MessageDAO;
import com.mycompany.doca_java.DTO.MessageDTO;
import jakarta.servlet.RequestDispatcher;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author Admin
 */
@ServerEndpoint(value = "/websocket")
public class WebSocketServlet extends HttpServlet {

    public static final String CHAT_PAGE = "Chat.jsp";
    private static List<MessageDTO> listOfMessage = new ArrayList<>();

    @OnOpen
    public void onOpen(Session session) {
        WebSocketSessionManager.addSession(session);
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        WebSocketSessionManager.sendMessageToAll(message);
    }

    @OnClose
    public void onClose(Session session) {
        WebSocketSessionManager.removeSession(session);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        int conversation_id = Integer.parseInt(request.getParameter("conversationID"));
        String url = "";
        try {
            MessageDAO dao = new MessageDAO();
            dao.getListMessageByConversationID(conversation_id);
            List<MessageDTO> ListOfMessage = dao.getListOfMessage();
            if (ListOfMessage != null) {
                request.setAttribute("ListOfMessage", ListOfMessage);
            }
            request.setAttribute("stateConvers", conversation_id);
            url = CHAT_PAGE;
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (NamingException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
        }
    }

// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
