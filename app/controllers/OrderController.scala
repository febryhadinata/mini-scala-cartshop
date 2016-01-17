package controllers

import models._
import tables._
import play.api.mvc._
import play.api.libs.json.Json._
import play.api.libs.json.Json
import play.api.db.slick._
import play.api.db.slick.Config.driver.simple._
import play.api.Play.current

/**
 * Class of Order controller, inherits from Base Controller
 */

object OrderController extends Controller {

  // ... initialize object
  val orders = TableQuery[OrdersTable]
  implicit val orderFormat = Json.format[Order]

  // ... get all of orders
  def getAll = DBAction { 
    implicit f =>
      Ok(toJson(orders.list))
  }

  // ... find an order by specific Id
  def findById(id: Long) = DBAction { 
    implicit f =>
      val order = orders.filter(_.id === id).firstOption
      Ok(toJson(order))
  }

  // ... create new order
  def create = DBAction(parse.json) { 
    implicit f =>
      f.request.body.validate[Order].map { 
        order =>
          orders.insert(order)
          Ok(toJson(order))
      }.getOrElse(BadRequest("Invalid JSON format"))
  }

  // ... update existing order
  def update(id: Long) = DBAction(parse.json) { 
    implicit f =>
      f.request.body.validate[Order].map { 
        order =>
          val is_order = orders.filter(_.id === id)
          if (is_order.exists.run) {
            orders.update(order)
            Ok(toJson(order))
          } else {
            BadRequest(Json.obj("status" -> "KO", "message" -> "Can't found specific order"))
          }
      }.getOrElse(BadRequest("Invalid JSON format"))
  }
  
  // ... delete specific order
  def delete(id: Long) = DBAction { 
    implicit f =>
      val order = orders.filter(_.id === id)
      if (order.exists.run) {
        order.delete
        Ok(Json.obj("status" -> "OK", "message" -> "Order was sucessfully deleted"))
      } else {
        BadRequest(Json.obj("status" -> "KO", "message" -> "Can't found specific order"))
      }
  }
  
  // ... add new coupon on existing order
  def addCoupon(id: Long) = DBAction(parse.json) { 
    implicit f =>
      f.request.body.validate[Order].map { 
        order =>
          val is_order = orders.filter(_.id === id)
          if (is_order.exists.run) {
            orders.update(order)
            Ok(toJson(order))
          } else {
            BadRequest(Json.obj("status" -> "KO", "message" -> "Can't found specific order"))
          }
      }.getOrElse(BadRequest("Invalid JSON format"))
  }
}
