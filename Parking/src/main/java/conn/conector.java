package conn;

import java.sql.*;

public class conector {
    private String url = "jdbc:mysql://localhost:3306/parking";
    private String usr = "root";
    private String pwd = "1234";
    
    public Connection con() throws Exception{
        return DriverManager.getConnection(url, usr, pwd);
    }
}
