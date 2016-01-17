package tables

import models.Order
import play.api.db.slick.Config.driver.simple._

// ... Orders table contains 3 columnns
class OrdersTable(tag: Tag) extends Table[Order](tag, "ORDERS") {

  // ... auto increment primary key
  def id = column[Long]("ORDER_ID", O.AutoInc, O.PrimaryKey)

  // ... total amount in a session cart
  def total = column[Double]("TOTAL")
  
  // ... status of order (in_cart, submitted, payed, processed)
  def status = column[String]("STATUS")

  // ... projection table for each type and parameters 
  def * = (id, total, status) <> (Order.tupled, Order.unapply _)
}


