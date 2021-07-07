package com.zachary.homework;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
  private String message;

  public void init() {
    message = "Hello World!";
  }

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    response.setContentType("text/html");

    // Hello
//    PrintWriter out = response.getWriter();
//    out.println("<<%@ taglib uri=\"/WEB-INF/custom-tags.tld\" prefix=\"sm\" %>\n" +
//            "<sm:common-response-headers />");
//    out.println("<html><body>");
//    out.println("<h1>" + message + "</h1>");
//    out.println("</body></html>");
    response.sendRedirect("toHome.jsp");
  }

  public void destroy() {
  }
}