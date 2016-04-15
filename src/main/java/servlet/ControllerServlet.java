package servlet;

import action.Action;
import action.ActionFactory;
import action.ActionResult;
import connection.DBConnection;
import entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(name = "ControllerServlet", urlPatterns = "/do/*")
public class ControllerServlet extends javax.servlet.http.HttpServlet {
    private ActionFactory actionFactory;

    @Override
    public void init() throws ServletException {
        actionFactory = new ActionFactory();
    }

    @Override
    public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String actionName = req.getMethod() + req.getPathInfo();
        Action action = actionFactory.getAction(actionName);

//        testConnection();

        if (action == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Not found");
            return;
        }

        ActionResult result = action.execute(req, resp);

        if (result.isRedirect()) {
            String location = req.getContextPath() + "/do/" + result.getView();
            resp.sendRedirect(location);
        } else {
            String path = "/WEB-INF/jsp/" + result.getView() + ".jsp";
            req.getRequestDispatcher(path).forward(req, resp);
        }

    }

//    public void testConnection() {
//        System.out.println("!!!!!-1");
//        DBConnection dbc = new DBConnection();
//        System.out.println("!!!!!-2");
//        Connection con = dbc.getConnection();
//
//        try {
//            User user = new User();
//            user.setFirstName("Kolya");
//            user.setLastName("Ivanov");
//            user.setEmail("ivalov@gmail.com");
//            System.out.println("!!!!!-3");
//            PreparedStatement statement = con.prepareStatement("insert into user (firstName, lastName, email) values (?, ?, ?)");
//            System.out.println("!!!!!-4");
//            statement.setString(1, user.getFirstName());
//            statement.setString(2, user.getLastName());
//            statement.setString(3, user.getEmail());
//            statement.executeUpdate();
//            System.out.println("!!!!!-5");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
}
