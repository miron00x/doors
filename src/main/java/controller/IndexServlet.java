package controller;

import dao.mysqlIMPL.DoorDaoImpl;
import entities.Door;
import services.DoorService;
import services.impl.DoorServiceImpl;
import util.DataSource;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class IndexServlet extends HttpServlet {
    private DoorServiceImpl doorService = new DoorServiceImpl();

    private void process(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        try {
            Connection connection = DataSource.getConnection();
            DoorDaoImpl doorDao = new DoorDaoImpl();
            doorDao.setConnection(connection);
            doorService.setDoorDao(doorDao);
            List<Door> doors = doorService.findAll();
            httpServletRequest.setAttribute("doors", doors);
            DataSource.returnConnection(connection);
        } catch (SQLException | ClassNotFoundException e) {
            //e.printStackTrace();
            throw new ServletException();
        }
        httpServletRequest.getRequestDispatcher("/WEB-INF/jsp/index.jsp").forward(httpServletRequest, httpServletResponse);
    }

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        process(httpServletRequest, httpServletResponse);
    }

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        process(httpServletRequest, httpServletResponse);
    }
}
