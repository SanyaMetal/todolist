package models

import java.sql.{Connection, DriverManager}

object DatabaseConnector {
  private val url = "jdbc:postgresql://localhost:5432/todolist_db"
  private val username = "postgres"
  private val password = "1221"

  def getConnection: Connection = {
    Class.forName("org.postgresql.Driver")
    DriverManager.getConnection(url,username,password)
  }

    

  }




