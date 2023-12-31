/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.doca_java.Controller;

import com.mycompany.doca_java.DAO.ProductDAO;
import com.mycompany.doca_java.DTO.ProductDTO;
import com.mycompany.doca_java.DTO.userDTO;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;

/**
 *
 * @author Admin
 */
@WebServlet(name = "PostPorductV2", urlPatterns = {"/PostPorductV2"})
@MultipartConfig
public class PostPorductV2 extends HttpServlet {

    private static final long serialVersionUID = 1L;
    public static final String postSucc = "postProductSucces.jsp";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

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
        HttpSession session = request.getSession(true);
        userDTO account = (userDTO) session.getAttribute("USER_NAME");
        String url = "";
        try {
            String categoryPost = request.getParameter("category_Product");
            String title = request.getParameter("title");
            String content = request.getParameter("content");
            String price = request.getParameter("fee");
            boolean isFree = true;
            String inputFee = "0";
            if (price != null && price.equals("fee")) {
                inputFee = request.getParameter("input-fee");
                isFree = false;
                // Xử lý giá trị của ô input khi radio button "Tính phí" được chọn
            } else {
                // Xử lý khi radio button "Miễn phí" được chọn hoặc không có radio button nào được chọn
            }
            // Location
            String city = request.getParameter("city");
            String district = request.getParameter("district");
            String ward = request.getParameter("ward");
            String address = city + "-" + district + "-" + ward;
            // Lấy ngày và giờ hiện tại
            LocalDateTime currentDateTime = LocalDateTime.now();
            // Chuyển đổi thành kiểu dữ liệu Timestamp
            Timestamp timePosted = Timestamp.valueOf(currentDateTime);
            String uploadDir = "C:\\Users\\ADMIN\\OneDrive\\Documents\\NetBeansProjects\\Doca_Quang\\src\\main\\webapp\\imgIn_DOCA";
            String fileName = null;
// Lấy ngày và giờ hiện tại
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
            String timestamp = dateFormat.format(new Date());

// Lấy phần tên file gốc
            Part filePart = request.getPart("file");
            String originalFileName = filePart.getSubmittedFileName();

// Tạo tên file mới
            int dotIndex = originalFileName.lastIndexOf(".");
            if (dotIndex != -1) {
                String extension = originalFileName.substring(dotIndex);
                fileName = timestamp + extension;
            } else {
                fileName = timestamp;
            }

// Tạo đường dẫn file trên server
            String filePath = uploadDir + File.separator + fileName;

// Lưu file
            filePart.write(filePath);
             fileName="imgIn_DOCA/" + fileName;
//            response.getWriter().println("File uploaded successfully." + uploadDir);
            ProductDAO dao = new ProductDAO();
            ProductDTO product
                    = new ProductDTO(account.getUser_ID(), Integer.parseInt(categoryPost), title, content, fileName,
                            isFree, Float.parseFloat(inputFee), address, timePosted, true, "pending", null);
            boolean result = false;
            result = dao.createPostProduct(product);
            if (result == true) {
                request.setAttribute("product", product);
                url = postSucc;
            }

        } catch (SQLException ex) {
            Logger.getLogger(PostPorductV2.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PostPorductV2.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            Logger.getLogger(PostPorductV2.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            response.sendRedirect(url);
        }
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
