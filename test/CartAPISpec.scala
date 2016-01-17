package test

import play.api.test._
import play.api.test.Helpers._
import play.api.libs.json.Json
import org.specs2.mutable._

/**
 * Test database spec for cart
 */
class CartAPISpec extends Specification {
  
  "Cart API" should {

    "Create, Update, and Delete Order Service" in new WithApplication {

      /**
       * Scenario : Insert new Data of Product
       * format : (Id, Name, Price, Quantity))
       */
      val product = Json.obj(
        "id" -> 1,
        "name" -> "Product-01",
        "price" -> 150000,
        "stock" -> 15
      )
      
      // ... create fake request
      val postProduct = FakeRequest(
        method = "POST",
        uri = "/product",
        headers = FakeHeaders(
          Seq("Content-type" -> Seq("application/json"))
        ),
        body = product
      )
      
      // ... make request
      val Some(resultProduct) = route(postProduct)
      
      // ... check status
      status(resultProduct) must equalTo(OK)

      /**
       * Scenario : Insert new Data of Order
       * format : (Id, Name, Price, Quantity))
       */
      val order = Json.obj(
        "id" -> 1,
        "total" -> 150000,
        "status" -> "InCart"
      )
      
      // ... create fake request
      val postOrder = FakeRequest(
        method = "POST",
        uri = "/order",
        headers = FakeHeaders(
          Seq("Content-type" -> Seq("application/json"))
        ),
        body = order
      )
      
      // ... make request
      val Some(resultOrder) = route(postOrder)
      
      // ... check status
      status(resultOrder) must equalTo(OK)
      
      /**
       * Scenario : Insert new Data of Cart
       * format : (orderId, productId, quantity))
       */
      val cart = Json.obj(
        "orderId" -> 1,
        "productId" -> 1,
        "quantity" -> 10
      )
      
      // ... create fake request
      val postCart = FakeRequest(
        method = "POST",
        uri = "/order/1/addProduct",
        headers = FakeHeaders(
          Seq("Content-type" -> Seq("application/json"))
        ),
        body = cart
      )
      
      // ... make request
      val Some(resultCart) = route(postCart)
      
      // ... check status
      status(resultCart) must equalTo(OK)
      
      
      /**
       * Scenario : Update quantity existing Data
       */
      val dataUpdate = Json.obj(
        "orderId" -> 1,
        "productId" -> 1,
        "quantity" -> 15
      )
      
      // ... create fake request /order/:orderId/updateProduct/:productId
      val postUpdate = FakeRequest(
        method = "PUT",
        uri = "/order/1/updateProduct/1",
        headers = FakeHeaders(
          Seq("Content-type" -> Seq("application/json"))
        ),
        body = dataUpdate
      )
      
      // ... make request
      val Some(getUpdate) = route(postUpdate)
      
      // ... check status
      status(getUpdate) must equalTo(OK)

      // ... get existing data /order/:orderId/products/:productId
      val getDataUpdate = route(FakeRequest(GET, "/order/1/products/1")).get
      
      // ... check status
      status(getDataUpdate) must equalTo(OK)
      
      // ... check updated quantity
      contentAsString(getDataUpdate) must contain("15")


      /**
       * Scenario : Delete existing Data
       */
      
      // ... make request .. /cart/:cartId/deleteItem/:itemId
      val deleteData = route(FakeRequest(DELETE, "/order/1/deleteProduct/1")).get
      
      // ... check status
      status(deleteData) must equalTo(OK)
    }
  }
}