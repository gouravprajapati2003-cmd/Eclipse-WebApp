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
import com.app.services.OTPService;
import com.app.util.JWTUtil;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String firstName = request.getParameter("fName_k");
		String lastName = request.getParameter("lName_k");
		String mobileNum = request.getParameter("mob_k");
		String email = request.getParameter("email_k");
		String password = request.getParameter("password_k");
		
		
		 // ---------------- VALIDATION START ----------------

//	    // 1️⃣ Email Validation
//	    if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
//	        response.getWriter().println("Invalid Email Format");
//	        return;
//	    }
//
//	    // 2️⃣ Mobile Validation (10 digit only)
//	    if (mobileNum == null || !mobileNum.matches("\\d{10}")) {
//	        response.getWriter().println("Mobile number must be 10 digits");
//	        return;
//	    }
//
//	    // 3️⃣ Password Validation
//	    // Minimum 8 characters, at least 1 uppercase, 1 lowercase, 1 digit, 1 special char
//	    if (password == null || 
//	        !password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$")) {
//
//	        response.getWriter().println(
//	            "Password must be 8+ chars with uppercase, lowercase, number & special character");
//	        return;
//	    }
	    
	    
	    if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
	        response.sendRedirect("register.html?error=Invalid Email Format");
	        return;
	    }

	    if (!mobileNum.matches("\\d{10}")) {
	        response.sendRedirect("register.html?error=Mobile Number must be 10 digits");
	        return;
	    }

	    if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$")) {
	        response.sendRedirect("register.html?error=Password must be 8+ chars with uppercase, lowercase, number & special character");
	        return;
	    }
	    
	    
	    response.sendRedirect("register.html?success=Registration Successful! Check Email");
	    
	    

	    // ---------------- VALIDATION END ----------------
		
		
		boolean dataSaveStatus = DatabaseConnection.insertUserData(firstName, lastName,Long.parseLong(mobileNum) , email, password);
		if(dataSaveStatus) {
			int OTP = (int)((Math.random() * 900000) + 100000);
		
			String token = JWTUtil.createJWT(email, Long.parseLong(mobileNum) , firstName + " " + lastName, OTP);
			DatabaseConnection.insertToken(token);
			boolean OTPSentStatus = OTPService.sendVerificationLink(email, firstName + " " + lastName, token);
			
			if(OTPSentStatus) {
				//HttpSession session = request.getSession(); // this creates a new session
				//session.setAttribute("sentOTP", OTP);
				//session.setAttribute("email", email);
				//response.sendRedirect("verifyOTP.html");
				int instanceOTP = OTP;
				response.sendRedirect("verificationLinkSentPage.html");
				System.out.println(AppLogger.SUCCESS_LOG("LINK SEND SUCCESSFUL"));
				System.out.println(AppLogger.SUCCESS_LOG("DATA SAVED SUCCESSFUL"));
			}else {
				System.out.println(AppLogger.ERROR_LOG("LINK SEND FAILED"));
			}
		}else {
			System.out.println(AppLogger.ERROR_LOG("DATA SAVED FAILED"));
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




