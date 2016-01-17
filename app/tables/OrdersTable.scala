package tables

import models.{Order, Coupon}
import play.api.db.slick.Config.driver.simple._
import scala.slick.lifted.ForeignKeyQuery

// ... Orders table contains 3 columnns
class OrdersTable(tag: Tag) extends Table[Order](tag, "ORDERS") {

  // ... auto increment primary key
  def id = column[Long]("ORDER_ID", O.AutoInc, O.PrimaryKey)

  // ... foreign key of coupon
  def couponId = column[Long]("COUPON_ID")

  // ... total amount in a session cart
  def total = column[Double]("TOTAL")
  
  // ... status of order (in_cart, submitted, payed, processed)
  def status = column[String]("STATUS")

  // ... projection table for each type and parameters 
  def * = (id, couponId, total, status) <> (Order.tupled, Order.unapply _)
  
  // ... foreign key relation assignment 
  def coupon: ForeignKeyQuery[CouponsTable, Coupon] =
    foreignKey("COUPON_FK", couponId, TableQuery[CouponsTable])(_.id)
}


