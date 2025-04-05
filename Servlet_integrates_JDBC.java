import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class EmployeeServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String id = request.getParameter("id");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb", "root", "password");
            Statement stmt = con.createStatement();
            String query = "SELECT * FROM employees";
            if (id != null && !id.isEmpty()) {
                query += " WHERE id = " + id;
            }
            ResultSet rs = stmt.executeQuery(query);

            out.println("<h2>Employee List</h2><ul>");
            while (rs.next()) {
                out.println("<li>" + rs.getInt("id") + " - " + rs.getString("name") + "</li>");
            }
            out.println("</ul>");
            con.close();
        } catch (Exception e) {
            out.println("Error: " + e.getMessage());
        }
    }
}

<!DOCTYPE html>
<html>
<head><title>Search Employees</title></head>
<body>
  <form action="EmployeeServlet" method="get">
    Enter Employee ID: <input type="text" name="id">
    <input type="submit" value="Search">
  </form>
</body>
</html>
