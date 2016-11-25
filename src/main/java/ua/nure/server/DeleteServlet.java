package ua.nure.server;

import ua.nure.logic.Handler;
import ua.nure.logic.Verifier;
import ua.nure.model.User;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Stanislav on 30.10.2016.
 */
@WebServlet("/delete")
public class DeleteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("DeleteServlet#doGet");
        req.getSession().setAttribute("deletingUser", new User(req.getParameter("login"), req.getParameter("fullName"),
                req.getParameter("mail"), req.getParameter("password")));
        req.getRequestDispatcher("/views/delete.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("DeleteServlet#doPost");
        User deletingUser = (User) req.getSession().getAttribute("deletingUser");
        try {
            Handler handler = Verifier.getHandler();
            handler.removeUser(deletingUser);
            req.getSession().setAttribute("usersList", handler.getUsers());
            req.getRequestDispatcher("/views/admin.jsp").forward(req, resp);
        } catch (SQLException e) {
            System.out.println("DeleteServlet#doPost - " +
                    e.getMessage());
        }
    }
}
