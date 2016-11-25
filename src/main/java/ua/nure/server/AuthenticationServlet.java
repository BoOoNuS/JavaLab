package ua.nure.server;

import ua.nure.logic.Handler;
import ua.nure.logic.Verifier;
import ua.nure.model.User;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

/**
 * Created by Stanislav on 30.10.2016.
 */
@WebServlet("/authentication")
public class AuthenticationServlet extends HttpServlet {

    private static final String NOT_DETERMINED_TRABL_PAGE = "<html><body>The user does not determined<br/><br/><a href=\"/\">Back</a></body></html>";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("AuthenticationServlet#doPost");
        Verifier verifier = Verifier.initVerifier();
        Handler handler = Verifier.getHandler();
        PrintWriter out = resp.getWriter();
        HttpSession session = req.getSession();
        String login = req.getParameter("login");
        User user = new User(login, null, null, req.getParameter("password"));
        try {
            session.setAttribute("user", user);
            session.setAttribute("usersList", handler.getUsers());
            if (verifier.identifyAdmin(login, user)) {
                System.out.println("AuthenticationServlet#adminPage");
                user.setFullName(verifier.getAdminByLogin(login).getUser().getFullName());
                user.setMail(verifier.getAdminByLogin(login).getUser().getMail());
                session.setAttribute("status", "Admin");
                req.getRequestDispatcher("/views/admin.jsp").forward(req, resp);
            } else if (handler.identify(user)) {
                System.out.println("AuthenticationServlet#userPage");
                user.setFullName(handler.getUserByLogin(login).getFullName());
                user.setMail(handler.getUserByLogin(login).getMail());
                session.setAttribute("status", "User");
                req.getRequestDispatcher("/views/user.jsp").forward(req, resp);
            }
        } catch (SQLException e) {
            System.out.println("AuthenticationServlet#adminPage - " +
                    e.getMessage());
        }
        System.out.println("AuthenticationServlet#trabl - identify");
        out.write(NOT_DETERMINED_TRABL_PAGE);
    }
}
