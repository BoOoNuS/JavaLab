package ua.nure.server;

import ua.nure.logic.DataParser;
import ua.nure.logic.Handler;
import ua.nure.logic.Verifier;
import ua.nure.model.User;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;


/**
 * Created by Stanislav on 30.10.2016.
 */
@WebServlet("/edit")
public class EditServlet extends HttpServlet {

    public static final String INCORRECT_DATA_TRABL_PAGE = "<html><body>Incorrect data<br/><br/><a href=\"/views/edit.jsp\">Back</a></body></html>";
    public static final String ADMIN_STATUS = "Admin";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("EditServlet#doGet");
        req.getSession().setAttribute("editingLogin", req.getParameter("login"));
        req.getRequestDispatcher("/views/edit.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("EditServlet#doPost");
        Handler handler = Verifier.getHandler();
        PrintWriter out = resp.getWriter();
        HttpSession session = req.getSession();
        String status = (String) session.getAttribute("status");
        String login = (String) session.getAttribute("editingLogin");
        String password = req.getParameter("password");
        String fullName = req.getParameter("fullName");
        try {
            if (DataParser.initParser().checkAll(login, password, fullName)) {
                User user;
                user = handler.getUserByLogin(login);
                user.setFullName(fullName);
                user.setMail(req.getParameter("mail"));
                user.setPassword(password);
                handler.editUser(user);
                session.setAttribute("usersList", handler.getUsers());
                if (ADMIN_STATUS.equals(status)) {
                    System.out.println("EditServlet#admin");
                    req.getRequestDispatcher("/views/admin.jsp").forward(req, resp);
                } else {
                    System.out.println("EditServlet#user");
                    session.setAttribute("user", user);
                    req.getRequestDispatcher("/views/user.jsp").forward(req, resp);
                }
            } else {
                System.out.println("EditServlet#trabl - incorrect");
                out.write(INCORRECT_DATA_TRABL_PAGE);
            }
        } catch (SQLException e) {
            System.out.println("EditServlet#doPost - " +
                    e.getMessage());
        }
    }
}
