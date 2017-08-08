package src.main.java.admin.utils;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DataSource {

  public static DataSource     datasource;
  private ComboPooledDataSource cpds;

  public DataSource()  {
    try{
      cpds = new ComboPooledDataSource();
      cpds.setDriverClass("org.postgresql.Driver"); //loads the jdbc driver
      cpds.setJdbcUrl("jdbc:postgresql://voto.db:5432/VOTOPY");
      cpds.setUser("ucsa2014");
      cpds.setPassword("ucsa2014");

      // the settings below are optional -- c3p0 can work with defaults
      cpds.setMinPoolSize(10);
      cpds.setAcquireIncrement(5);
      cpds.setMaxPoolSize(20);
      cpds.setMaxStatements(180);
    }
    catch(Exception e){
      System.out.println(e);
    }
}
  
  public static DataSource getInstance() throws IOException, SQLException, PropertyVetoException {
    if (datasource == null) {
        datasource = new DataSource();
        return datasource;
    } else {
        return datasource;
    }
}

public Connection getConnection() throws SQLException {
    return this.cpds.getConnection();
}
  
}
