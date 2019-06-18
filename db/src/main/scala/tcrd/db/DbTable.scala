package tcrd.db

import scalikejdbc.{ConnectionPool, DB}
import scalikejdbc.withSQL

case class DbTable(schema: Schema) {

  Class.forName("org.h2.Driver")
  ConnectionPool.singleton("jdbc:h2:mem:hello", "user", "pass")

   val x = withSQL {
     ???
    }

}
