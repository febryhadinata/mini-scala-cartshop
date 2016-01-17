package test

import models._
import tables._
import play.api.test._
import play.api.test.Helpers._
import play.api.db.slick.DB
import play.api.db.slick.Config.driver.simple._
import org.specs2.mutable._

/**
 * Test database spec for all Objects
 */
class DatabaseSpec extends Specification {

  "DB" should {
    "Work as expected" in new WithApplication {

      // ... initialize instance
      val Products = TableQuery[ProductsTable]
      val Orders = TableQuery[OrdersTable]
      val Carts = TableQuery[CartsTable]
      // val Coupons = TableQuery[CouponsTable]
      
      DB.withSession { 
        implicit s: Session =>
          // ... test Product table
          // ... format : (Id, Name, Price, Quantity)
          val productList = Seq(
            Product(1, "Product-01", 100000, 50),
            Product(2, "Product-02", 100000, 20),
            Product(3, "Product-03", 125000, 25),
            Product(4, "Product-04", 175000, 55))

          // ... insert into val Products
          Products.insertAll(productList: _*)
          
          // ... check assertion equal 
          Products.list must equalTo(productList)


          // ... test Order table
          // ... format : (Id, Total, Status)
          val orderList = Seq(
            Order(1, 200000, "Payment Accepted"),
            Order(2, 150000, "Shipped"),
            Order(3, 325000, "Delivered"),
            Order(4, 1000000, "InCart"))

          // ... insert into val Orders
          Orders.insertAll(orderList: _*)
        
          // ... check assertion equal
          Orders.list must equalTo(orderList)

        
          // ... test Cart table 
          // ... format : (OrderId, ProductId, Quantity)
          val testCarts = Seq(
            Cart(1, 1, 5),
            Cart(1, 2, 3),
            Cart(2, 3, 5),
            Cart(3, 1, 5))

          // ... insert into val Carts
          Carts.insertAll(testCarts: _*)
          
          // ... check assertion equal
          Carts.list must equalTo(testCarts)
      }
    }

    "Select testing database by default for correct approach" in new WithApplication(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
      DB.withSession { implicit s: Session =>
        s.conn.getMetaData.getURL must startWith("jdbc:h2:mem:play-test")
      }
    }

    "Use default database settings when there's no other available options" in new WithApplication {
      DB.withSession { implicit s: Session =>
        s.conn.getMetaData.getURL must equalTo("jdbc:h2:mem:play")
      }
    }
  }
}
