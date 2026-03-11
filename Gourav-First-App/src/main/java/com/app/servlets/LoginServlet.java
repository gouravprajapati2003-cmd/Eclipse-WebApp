package com.app.servlets;

import java.io.IOException;

import com.app.database.DatabaseConnection;
import com.app.logs.AppLogger;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import shadow.org.bson.Document;


/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email_k");
		String password = request.getParameter("password_k");

		
		Document loginUser=  DatabaseConnection.loginUser(email);
		
		if(loginUser != null) {
			if(email.equals(loginUser.getString("userEmail")) && password.equals(loginUser.getString("userPassword")) && (loginUser.getBoolean("isVerified")== true)) {
				response.sendRedirect("home.html");
				System.out.println(AppLogger.SUCCESS_LOG("ACCOUNT IS VERIFIED"));
			}else if(email.equals(loginUser.getString("userEmail")) && password.equals(loginUser.getString("userPassword")) && (loginUser.getBoolean("isVerified")== false)) {
				System.out.println(AppLogger.ERROR_LOG("ACCOUNT NOT VERIFIED"));
			}
			else {
				System.out.println(AppLogger.INFO_LOG("YOUR PASSWORD IS INCORRECT"));
			}
			
		}
		
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}