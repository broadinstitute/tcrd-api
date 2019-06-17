package tcrd.model

case class Status(
  id: String,
  message: String)

object Status {
  object Ids {
    val initializing: String = "initializing"
    val ready: String = "ready"
    val failed: String = "failed"
  }
  def initializing(message: String): Status = Status(Ids.initializing, message)
  def ready(message: String): Status = Status(Ids.ready, message)
  def failed(message: String): Status = Status(Ids.failed, message)
}
