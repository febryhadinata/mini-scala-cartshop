package test

import play.api.test._
import play.api.test.Helpers._
import play.api.libs.json.Json
import org.specs2.mutable._

/**
 * Test database spec for orders
 */
class OrderAPISpec extends Specification {

  "Order API" should {

    "Create Order Service" in new WithApplication {

      /**
       * Scenario : Insert new Data
       * format : (Id, Total, Status)
       */
      val data = Json.obj(
        "id" -> 1,
        "total" -> 150000,
        "status" -> "Shipped"
      )
      
      // ... create fake request
      val postRequest = FakeRequest(
        method = "POST",
        uri = "/order",
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
      val getOrderDataInserted = route(FakeRequest(GET, "/orders")).get
      
      // ... check status
      status(getOrderDataInserted) must equalTo(OK)
      
      // ... check header content-type
      contentType(getOrderDataInserted) must beSome.which(_ == "application/json")
      
      // ... check status
      contentAsString(getOrderDataInserted) must contain("1")
      
      /**
       * Scenario : Update existing data
       */
      val dataUpdate = Json.obj(
        "id" -> 1,
        "total" -> 150000,
        "status" -> "Delivered"
      )
      
      // ... create fake request
      val postUpdate = FakeRequest(
        method = "PUT",
        uri = "/order/1",
        headers = FakeHeaders(
          Seq("Content-type" -> Seq("application/json"))
        ),
        body = dataUpdate
      )
      
      // ... make requests
      val Some(getUpdate) = route(postUpdate)
      
      // ... check status
      status(getUpdate) must equalTo(OK)

      // ... get updated new data
      val getDataUpdate = route(FakeRequest(GET, "/order/1")).get
      
      // ... check status
      status(getDataUpdate) must equalTo(OK)
      
      // ... check new updated status
      contentAsString(getDataUpdate) must contain("Delivered")

      /**
       * Scenario : Delete existing data
       */
      // ... make request
      val deleteData = route(FakeRequest(DELETE, "/order/1")).get
      
      // ... check status
      status(deleteData) must equalTo(OK)
    }
    
    "Update Existing Order Service" in new WithApplication {

      /**
       * Scenario : Insert new Data
       * format : (Id, Total, Status)
       */
      val data = Json.obj(
        "id" -> 1,
        "total" -> 150000,
        "status" -> "Shipped"
      )
      
      // ... create fake request
      val postRequest = FakeRequest(
        method = "POST",
        uri = "/order",
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
        "total" -> 150000,
        "status" -> "Delivered"
      )
      
      // ... create fake request
      val postUpdate = FakeRequest(
        method = "PUT",
        uri = "/order/1",
        headers = FakeHeaders(
          Seq("Content-type" -> Seq("application/json"))
        ),
        body = dataUpdate
      )
      
      // ... make requests
      val Some(getUpdate) = route(postUpdate)
      
      // ... check status
      status(getUpdate) must equalTo(OK)

      // ... get updated new data
      val getDataUpdate = route(FakeRequest(GET, "/order/1")).get
      
      // ... check status
      status(getDataUpdate) must equalTo(OK)
      
      // ... check new updated status
      contentAsString(getDataUpdate) must contain("Delivered")
    }
    
    "Delete Existing Order Service" in new WithApplication {

      /**
       * Scenario : Insert new Data
       * format : (Id, Total, Status)
       */
      val data = Json.obj(
        "id" -> 1,
        "total" -> 150000,
        "status" -> "Shipped"
      )
      
      // ... create fake request
      val postRequest = FakeRequest(
        method = "POST",
        uri = "/order",
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
      val deleteData = route(FakeRequest(DELETE, "/order/1")).get
      
      // ... check status
      status(deleteData) must equalTo(OK)
    }
  }
}
