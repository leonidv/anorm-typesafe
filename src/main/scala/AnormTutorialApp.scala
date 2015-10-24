/**
 * Created by leonidv on 25.10.15.
 */

import anorm._
import anorm.SqlParser._


object AnormTutorialApp {

  case class Person(id : Int, name : String, salary : Option[Int])

  def main(args: Array[String]) {
    import java.sql._

   implicit val connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/anorm-tutorial", "leonidv","1234567")


   val parser =
     int("id") ~ str("name") ~ get[Option[Int]]("salary") map {
       case id ~ name ~ salary => Person(id, name, salary)
     }

   val name = "peter";
   val salary = "500"//"five thousands";
   val x =
     SQL"""
          insert into person(name, salary)
          VALUES ($name, $salary)
          RETURNING id, name, salary""".single()
   println(x.as(parser).get)
  connection.close();
  }
}
