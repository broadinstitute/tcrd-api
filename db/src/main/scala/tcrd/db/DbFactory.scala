package tcrd.db

import scalikejdbc.ConnectionPool

object DbFactory {

  Class.forName("org.h2.Driver")
  ConnectionPool.singleton("jdbc:h2:mem:hello", "user", "pass")

  def prepareDb(schema: Schema): Db = {

    ???
  }

}
