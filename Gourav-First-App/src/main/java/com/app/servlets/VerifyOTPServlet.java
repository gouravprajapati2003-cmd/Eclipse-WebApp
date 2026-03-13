package com.app.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import com.app.database.DatabaseConnection;
import com.app.logs.AppLogger;
import com.app.util.JWTUtil;

/**
 * Servlet implementation class VerifyOTPServlet
 */
@WebServlet("/VerifyOTPServlet")
public class VerifyOTPServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VerifyOTPServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String token = request.getParameter("token");
		
		if(DatabaseConnection.checkTokenUseCount(token)) {
			String email = JWTUtil.verifyJWT(token);
			boolean verifyStatus = DatabaseConnection.verifyUser(email);
			if(verifyStatus) {
				System.out.println(AppLogger.SUCCESS_LOG("VERIFICATION SUCCESS"));
			}
			else {
				System.out.println(AppLogger.ERROR_LOG("VERIFICATION FAILED"));
			}
		
//		String userOTP = (request.getParameter("userOTP").trim());
//		HttpSession session = request.getSession(false);

//        String email = (String)(session.getAttribute("email"));
//		
//		if(Integer.parseInt(userOTP) == sentOTP) {
//			DatabaseConnection.verifyUser(email);
		response.sendRedirect("home.html");
			System.out.println(AppLogger.SUCCESS_LOG("LINK VERIFICATION SUCCESSFUL"));
//		}else {
//			System.out.println(AppLogger.ERROR_LOG("OTP VERIFICATION FAILED"));
//		}
	} else {
		System.out.println(AppLogger.ERROR_LOG("THE TOKEN HAS BEEN USED ONCE"));
	}
//		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

