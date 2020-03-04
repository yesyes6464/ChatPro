package user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/UserRegisterServlet")
public class UserRegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String userID = request.getParameter("userID");
		String userPASS = request.getParameter("userpass1");
		String userPASS2 = request.getParameter("userpass2");
		String username = request.getParameter("username");
		String userage = request.getParameter("userage");
		String usergender = request.getParameter("usergender");
		String useremail = request.getParameter("useremail");
		String userprofile = request.getParameter("userprofile");
		if(userID == null || userID.equals("") || userPASS == null || userPASS.equals("")
				|| username == null || username.equals("") || userage == null || userage.equals("") || usergender == null || usergender.equals("") ||
				useremail == null || useremail.equals("")) {
			request.getSession().setAttribute("messageType", "오류메시지");
			request.getSession().setAttribute("messageContent", "모든 내용을 입력하세요.");
			response.sendRedirect("join.jsp");
			return;
		}
		if(!userPASS.equals(userPASS2)) {
			request.getSession().setAttribute("messageType", "오류메시지");
			request.getSession().setAttribute("messageContent", "비밀번호가 서로 다릅니다.");
			response.sendRedirect("join.jsp");
			return;
		}
		int result = new UserDAO().register(userID, userPASS2, username, userage, usergender, useremail, "");
		if(result == 1) {
			//사용자가 회원가입을 했을때 자동으로 로그인이 이루어지면서 index페이지로 이동
			request.getSession().setAttribute("userID", userID);
			request.getSession().setAttribute("messageType", "확인 메시지");
			request.getSession().setAttribute("messageContent", "회원가입에 성공했습니다.");
			response.sendRedirect("index.jsp");
			return;
		} else {
			request.getSession().setAttribute("messageType", "오류메시지");
			request.getSession().setAttribute("messageContent", "이미 존재하는 회원입니다.");
			response.sendRedirect("join.jsp");
			return;
		}
	}

}
