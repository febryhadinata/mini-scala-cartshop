package tables

import play.api.db.slick.Config.driver.simple._
import scala.slick.lifted.ForeignKeyQuery
import models.{Order, Cart, Product}

/**
 * Carts table contains 3 columnns, 
 * 2 key is foreign key from Order and Product
 */
class CartsTable(tag: Tag) extends Table[Cart](tag, "CARTS") {

  // ... foreign key from Order Id, also act as Primary Key
  def orderId = column[Long]("ORDER_ID")

  // ... foreign key from Product 
  def productId = column[Long]("PRODUCT_ID")

  // ... total quantity
  def quantity = column[Int]("QUANTITY")

  // ... projection table for each type and parameters 
  def * = (orderId, productId, quantity) <> (Cart.tupled, Cart.unapply _)
  
  // ... foreign key relation assignment 
  def order: ForeignKeyQuery[OrdersTable, Order] =
    foreignKey("ORDER_FK", orderId, TableQuery[OrdersTable])(_.id)

  def product: ForeignKeyQuery[ProductsTable, Product] =
    foreignKey("PRODUCT_FK", productId, TableQuery[ProductsTable])(_.id)

}