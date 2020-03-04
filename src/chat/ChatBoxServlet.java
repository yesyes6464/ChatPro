package chat;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import user.UserDAO;

@WebServlet("/ChatBoxServlet")
public class ChatBoxServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String userID = request.getParameter("userID");
		if(userID == null || userID.equals("")) {
			response.getWriter().write("");
		} else {
			try {
				HttpSession session = request.getSession();
				if(!URLDecoder.decode(userID,"UTF-8").equals((String)session.getAttribute("userID"))) {
					response.getWriter().write("");
					return;
				}
				userID = URLDecoder.decode(userID,"UTF-8");
				response.getWriter().write(getBox(userID));
			} catch (Exception e) {
				System.out.println("chatBoxServlet 오류발생"+e);
				response.getWriter().write("");
			}
		
		}
	}

	public String getBox(String userID) {
		StringBuffer result = new StringBuffer("");
		result.append("{\"result\":[");
		//json : 어떠한 개발언어든 공통적으로 사용할 수 있는 하나의 배열같은 것을 표현하고 담을 수 있는 약
		ChatDAO chatDAO = new ChatDAO();
		ArrayList<ChatDTO> chatList = chatDAO.getBox(userID);
		if(chatList.size()==0) return "";
		//  int i=0; i< chatList.size();i++ 를 아래와 같이 바꿈으로써 가장 최근에 온 메세지가 위쪽으로 정렬 되도록 함.
		for(int i= chatList.size()-1; i>=0;i--) {
			//자신이 받는 사람일때에 한해서 현재 읽지 않은 메세지의 개수를 함께 출력
			String unread = "";
			String userProfile = "";
			if(userID.equals(chatList.get(i).getToID())) {
				unread = chatDAO.getUnreadChat(chatList.get(i).getFromID(), userID) + "";
				if(unread.equals("0")) unread = "";
			}
			if(userID.equals(chatList.get(i).getToID())) {
				userProfile = new UserDAO().getProfile(chatList.get(i).getFromID());
			} else {
				userProfile = new UserDAO().getProfile(chatList.get(i).getToID());
			}
			result.append("[{\"value\": \"" + chatList.get(i).getFromID() + "\"},");
			result.append("{\"value\": \"" + chatList.get(i).getToID() + "\"},");
			result.append("{\"value\": \"" + chatList.get(i).getChatContent() + "\"},");
			result.append("{\"value\": \"" + chatList.get(i).getChatTime() + "\"},");
			result.append("{\"value\": \"" + unread + "\"},");
			result.append("{\"value\": \"" + userProfile + "\"}]");
			
			if(i != 0) result.append(",");
		}
		result.append("], \"last\":\""+chatList.get(chatList.size()-1).getChatID() + "\"}");
		return result.toString();
	}
	
}



