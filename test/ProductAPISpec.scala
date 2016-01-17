package test

import play.api.test._
import play.api.test.Helpers._
import play.api.libs.json.Json
import org.specs2.mutable._

/**
 * Test database spec for products
 */
class ProductAPISpec extends Specification {

  "Product API" should {

    "Create Product Service" in new WithApplication {

      /**
       * Scenario : Insert new Data
       * format : (Id, Name, Price, Quantity)
       */
      val data = Json.obj(
        "id" -> 1,
        "name" -> "Product-01",
        "price" -> 100000,
        "stock" -> 15
      )
      
      // ... create fakerequest
      val postRequest = FakeRequest(
        method = "POST",
        uri = "/product",
        headers = FakeHeaders(
          Seq("Content-type" -> Seq("application/json"))
        ),
        body = data
      )
      
      // ... make request
      val Some(result) = route(postRequest)
      
      // ... check status
      status(result) must equalTo(OK)

      // ... get inserted new data
      val getDataInserted = route(FakeRequest(GET, "/products")).get
      
      // ... check status
      status(getDataInserted) must equalTo(OK)
      
      // ... check product name
      contentAsString(getDataInserted) must contain("Product-01")
    }
    
    "Update Existing Product Service" in new WithApplication {

      /**
       * Scenario : Insert new Data
       * format : (Id, Name, Price, Quantity)
       */
      val data = Json.obj(
        "id" -> 1,
        "name" -> "Product-01",
        "price" -> 100000,
        "stock" -> 15
      )
      
      // ... create fakerequest
      val postRequest = FakeRequest(
        method = "POST",
        uri = "/product",
        headers = FakeHeaders(
          Seq("Content-type" -> Seq("application/json"))
        ),
        body = data
      )
      
      // ... make request
      val Some(result) = route(postRequest)
      
      // ... check status
      status(result) must equalTo(OK)

      /**
       * Scenario : Update existing data
       */
      val dataUpdate = Json.obj(
        "id" -> 1,
        "name" -> "Product-01 new update",
        "price" -> 150000,
        "stock" -> 10
      )
      
      // ... create fakerequest
      val postUpdate = FakeRequest(
        method = "PUT",
        uri = "/product/1",
        headers = FakeHeaders(
          Seq("Content-type" -> Seq("application/json"))
        ),
        body = dataUpdate
      )
      
      // ... make request
      val Some(getUpdate) = route(postUpdate)
      
      // ... check status
      status(getUpdate) must equalTo(OK)

      // ... get updated new data
      val getDataProductUpdate = route(FakeRequest(GET, "/product/1")).get
      
      // ... check status
      status(getDataProductUpdate) must equalTo(OK)
      
      // ... check new product name
      contentAsString(getDataProductUpdate) must contain("Product-01 new update")
    }
    
    "Delete Existing Product Service" in new WithApplication {

      /**
       * Scenario : Insert new Data
       * format : (Id, Name, Price, Quantity)
       */
      val data = Json.obj(
        "id" -> 1,
        "name" -> "Product-01",
        "price" -> 100000,
        "stock" -> 15
      )
      
      // ... create fakerequest
      val postRequest = FakeRequest(
        method = "POST",
        uri = "/product",
        headers = FakeHeaders(
          Seq("Content-type" -> Seq("application/json"))
        ),
        body = data
      )
      
      // ... make request
      val Some(result) = route(postRequest)
      
      // ... check status
      status(result) must equalTo(OK)

      /**
       * Scenario : Delete existing data
       */
      // ... make request
      val deleteData = route(FakeRequest(DELETE, "/product/1")).get
      
      // ... check status
      status(deleteData) must equalTo(OK)
    }
  }
}
