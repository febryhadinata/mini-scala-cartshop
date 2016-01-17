package tables

import models.Coupon
import play.api.db.slick.Config.driver.simple._

// ... Coupons table contains 4 columnns
class CouponsTable(tag: Tag) extends Table[Coupon](tag, "COUPONS") {

  // ... auto increment primary key
  def id = column[Long]("COUPON_ID", O.AutoInc, O.PrimaryKey)

  // ... authentic code by string
  def code = column[String]("CODE")
  
  // ... value of coupon
  def value = column[Double]("VALUE")

  // ... status of coupon
  def is_redeemed = column[Boolean]("IS_REDEEMED")

  // ... projection table for each type and parameters 
  def * = (id, code, value, is_redeemed) <> (Coupon.tupled, Coupon.unapply _)
}
