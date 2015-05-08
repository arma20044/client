package src.main.java.admin.reportes;

import java.sql.*;
import java.util.*;

public class Conect
{
  class Blog
  {
    public int id;
    public String subject;
    public String permalink;
  }

  public static void main(String[] args)
  {
    new Conect();
  }

  public Conect() 
  {
    Connection conn = null;
    LinkedList listOfBlogs = new LinkedList();

    // connect to the database
    conn = connectToDatabaseOrDie();

    // get the data
    populateListOfTopics(conn, listOfBlogs);
    
    // print the results
    printTopics(listOfBlogs);
  }
  
  private void printTopics(LinkedList listOfBlogs)
  {
    Iterator it = listOfBlogs.iterator();
    while (it.hasNext())
    {
      Blog blog = (Blog)it.next();
      System.out.println("id: " + blog.id + ", subject: " + blog.subject);
    }
  }

  private void populateListOfTopics(Connection conn, LinkedList listOfBlogs)
  {
    try 
    {
      Statement st = conn.createStatement();
      ResultSet rs = st.executeQuery("select * from ucsaws_listas");
      while ( rs.next() )
      {
        Blog blog = new Blog();
        blog.id        = rs.getInt    ("id_lista");
        blog.subject   = rs.getString ("nombre_lista");
        blog.permalink = rs.getString ("nro_lista");
        listOfBlogs.add(blog);
      }
      rs.close();
      st.close();
    }
    catch (SQLException se) {
      System.err.println("Threw a SQLException creating the list of blogs.");
      System.err.println(se.getMessage());
    }
  }

  private Connection connectToDatabaseOrDie()
  {
    Connection conn = null;
    try
    {
      Class.forName("org.postgresql.Driver");
      String url = "jdbc:postgresql://localhost:5432/VOTOPY";
      conn = DriverManager.getConnection(url,"ucsa2014", "ucsa2014");
    }
    catch (ClassNotFoundException e)
    {
      e.printStackTrace();
      System.exit(1);
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      System.exit(2);
    }
    return conn;
  }

}
