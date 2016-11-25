package ua.nure.server;

import static ua.nure.server.EditServlet.*;

import ua.nure.logic.DataParser;
import ua.nure.logic.Handler;
import ua.nure.logic.Verifier;
import ua.nure.model.User;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

/**
 * Created by Stanislav on 30.10.2016.
 */
@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {

    public static final String EXIST_TRABL_PAGE = "<html><body>This login already exist<br/><br/><a href=\"/views/registration.jsp\">Back</a></body></html>";
    public static final String INCORRECT_DATA_TRABL_PAGE = "<html><body>Incorrect data<br/><br/><a href=\"/views/registration.jsp\">Back</a></body></html>";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("RegistrationServlet#doPost");
        Verifier verifier = Verifier.initVerifier();
        Handler handler = Verifier.getHandler();
        PrintWriter out = resp.getWriter();
        String status = (String) req.getSession().getAttribute("status");
        String fullName = req.getParameter("fullName");
        String password = req.getParameter("password");
        User user = new User(req.getParameter("login"), fullName, req.getParameter("mail"), password);
        try {
            if (!handler.isExist(user.getLogin())) {
                if (DataParser.initParser().checkAll(user.getLogin(), password, fullName)) {
                    try {
                        handler.addUser(user);
                        req.setAttribute("usersList", handler.getUsers());
                        if (ADMIN_STATUS.equals(status)) {
                            System.out.println("RegistrationServlet#admin");
                            req.getRequestDispatcher("/views/admin.jsp").forward(req, resp);
                        } else {
                            System.out.println("RegistrationServlet#user");
                            req.getRequestDispatcher("/").forward(req, resp);
                        }
                    } catch (SQLException e) {
                        System.out.println("RegistrationServlet#user - " +
                                e.getMessage());
                    }
                } else {
                    System.out.println("RegistrationServlet#trabl - pars");
                    out.write(INCORRECT_DATA_TRABL_PAGE);
                }
            } else {
                System.out.println("RegistrationServlet#trabl - user exist");
                out.write(EXIST_TRABL_PAGE);
            }
        } catch (SQLException e) {
            System.out.println("RegistrationServlet#doPost - " +
                    e.getMessage());

        }

    }
}
