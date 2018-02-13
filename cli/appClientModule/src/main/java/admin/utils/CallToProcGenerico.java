package src.main.java.admin.utils;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CallToProcGenerico {
  
  private static JDBCExample ds = new JDBCExample();
  
  static Connection jdbcConnection = null;
  


  
  public String callProc(Integer numero) throws SQLException{
    jdbcConnection = ds.getConnection();
    Statement statement = jdbcConnection.createStatement();
    String query = "select f_convnl(25) as a ";
    CallableStatement ps = jdbcConnection.prepareCall(query);
    ResultSet rs = ps.executeQuery();
    List<String> listOfData = new ArrayList<String>();
    
    while(rs.next()){
      System.out.println(rs.getString(1));
    }
    return rs.getString(1);
    
    //System.out.println(rs.findColumn("a"));
    
  
  }

}
