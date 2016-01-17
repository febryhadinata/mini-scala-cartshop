package tables

import models.Product
import play.api.db.slick.Config.driver.simple._

// ... Products table contains 4 columnns
class ProductsTable(tag: Tag) extends Table[Product](tag, "PRODUCTS") {

  // ... auto increment primary key
  def id = column[Long]("PRODUCT_ID", O.AutoInc, O.PrimaryKey)

  // ... name of product
  def name  = column[String]("NAME")

  // ... price of product
  def price = column[Double]("PRICE")

  // ... stock of product
  def stock = column[Long]("STOCK")

  // ... projection table for each type and parameters 
  def * = (id, name, price, stock) <> (Product.tupled, Product.unapply _)
}