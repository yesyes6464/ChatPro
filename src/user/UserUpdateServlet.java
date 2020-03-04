package user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/UserUpdateServlet")
public class UserUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String userID = request.getParameter("userID");
		HttpSession session = request.getSession();
		String userPASS = request.getParameter("userpass1");
		String userPASS2 = request.getParameter("userpass2");
		String username = request.getParameter("username");
		String userage = request.getParameter("userage");
		String usergender = request.getParameter("usergender");
		String useremail = request.getParameter("useremail");
		if(userID == null || userID.equals("") || userPASS == null || userPASS.equals("")
				|| username == null || username.equals("") || userage == null || userage.equals("") || usergender == null || usergender.equals("") ||
				useremail == null || useremail.equals("")) {
			request.getSession().setAttribute("messageType", "오류메시지");
			request.getSession().setAttribute("messageContent", "모든 내용을 입력하세요.");
			response.sendRedirect("update.jsp");
			return;
		}
		if(!userID.equals((String)session.getAttribute("userID"))){
			session.setAttribute("messageType", "오류 메시지");
			session.setAttribute("messageContent","접근할 수 없습니다.");
			response.sendRedirect("index.jsp");
			return;
		}
		if(!userPASS.equals(userPASS2)) {
			request.getSession().setAttribute("messageType", "오류메시지");
			request.getSession().setAttribute("messageContent", "비밀번호가 서로 다릅니다.");
			response.sendRedirect("update.jsp");
			return;
		}
		int result = new UserDAO().update(userID, userPASS2, username, userage, usergender, useremail);
		if(result == 1) {
			//사용자가 회원가입을 했을때 자동으로 로그인이 이루어지면서 index페이지로 이동
			request.getSession().setAttribute("userID", userID);
			request.getSession().setAttribute("messageType", "확인 메시지");
			request.getSession().setAttribute("messageContent", "회원정보 수정에 성공했습니다.");
			response.sendRedirect("index.jsp");
			return;
		} else {
			request.getSession().setAttribute("messageType", "오류메시지");
			request.getSession().setAttribute("messageContent", "데이터베이스 오류가 발생했습니다.");
			response.sendRedirect("update.jsp");
			return;
		}
	}

}
