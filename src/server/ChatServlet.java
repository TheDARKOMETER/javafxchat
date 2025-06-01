/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
/**
 *
 * @author Administrator
 */
public class ChatServlet extends HttpServlet {
    private String message;
    public void init() throws ServletException {
        message = "Hello World";
    }
    
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException { 
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        out.println("<h1>" + message + "/<h1>");
    }
    
    public void destroy() {
        // do nothing;
    }
}
