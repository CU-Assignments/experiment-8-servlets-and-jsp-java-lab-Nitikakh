<%@ page import="java.sql.*" %>
<html>
<head><title>Attendance Form</title></head>
<body>
  <form action="AttendanceServlet" method="post">
    Student Name: <input type="text" name="name"><br>
    Date: <input type="date" name="date"><br>
    Status: <select name="status">
      <option value="Present">Present</option>
      <option value="Absent">Absent</option>
    </select><br>
    <input type="submit" value="Submit Attendance">
  </form>
</body>
</html>

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class AttendanceServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String name = request.getParameter("name");
        String date = request.getParameter("date");
        String status = request.getParameter("status");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb", "root", "password");
            PreparedStatement ps = con.prepareStatement("INSERT INTO attendance(name, date, status) VALUES (?, ?, ?)");
            ps.setString(1, name);
            ps.setString(2, date);
            ps.setString(3, status);
            ps.executeUpdate();
            con.close();

            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<h2>Attendance submitted successfully!</h2>");
        } catch (Exception e) {
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}
