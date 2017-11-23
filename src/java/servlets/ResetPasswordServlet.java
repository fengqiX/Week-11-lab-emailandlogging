/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import businesslogic.AccountService;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author 715583
 */
public class ResetPasswordServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getRequestURL().toString();
        String action = req.getParameter("action");
        String path = getServletContext().getRealPath("/WEB-INF");
        AccountService as = new AccountService();
        String insiteurl = "/WEB-INF/login.jsp";
        if(action !=null && action.equals("passEmail"))
        {
            String email = req.getParameter("passResetEmail");
            boolean res = as.resetPassword(email, path, url);
            if(res==false)
            {
                req.setAttribute("message", "user does not exist!");
            }
            else
            {
                req.setAttribute("message", "Check your email!");
            }
            insiteurl = "/WEB-INF/reset.jsp";
        }
        if(action !=null && action.equals("setnewpassword"))
        {
            String newpassword = req.getParameter("newpassword");
            String uuid=req.getParameter("uuid");
            try{
                as.changePassword(uuid, newpassword);
                req.setAttribute("message", "reset password succefully!");
            }catch (Exception ex)
            {
                req.setAttribute("message", "Invalid operation!");
            }
            insiteurl = "/WEB-INF/resetNewPassword.jsp";
        }
        getServletContext().getRequestDispatcher(insiteurl).forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        String uuid = req.getParameter("uuid");
        String url = "/WEB-INF/reset.jsp";
        if(uuid != null)
        {
            req.setAttribute("uuid", uuid);
            url = "/WEB-INF/resetNewPassword.jsp";
        }
       getServletContext().getRequestDispatcher(url).forward(req, resp);
      
    }

}
