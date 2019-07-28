package servlet;

import javax.servlet.*;
import java.io.IOException;
import java.io.PrintWriter;

public class TheServlet implements Servlet {
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {

    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        req.getAttribute("123");
        req.getParameter("123");
        req.getParameterValues("13");
        req.setAttribute("123","object");
        RequestDispatcher dispatcher = req.getRequestDispatcher("url");
        dispatcher.forward(req,res);
        PrintWriter writer = res.getWriter();
        writer.write("1234");
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }
}
