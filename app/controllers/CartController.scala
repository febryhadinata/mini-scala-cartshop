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
 * Class of Cart controller, inherits from Base Controller
 */
 
object CartController extends Controller {

  // ... initialize object
  val carts = TableQuery[CartsTable]
  implicit val cartFormat = Json.format[Cart]

  // ... get all of carts
  def getAll(orderId: Long) = DBAction { 
    implicit f =>
      val cart = carts.filter(_.orderId === orderId)
      Ok(toJson(cart.list))
  }

  // ... find a cart by specific order Id and product Id
  def findById(orderId: Long, productId: Long) = DBAction { 
    implicit f =>
      val cart = carts.filter(_.orderId === orderId).filter(_.productId === productId).firstOption
      Ok(toJson(cart))
  }

  // ... create new cart
  def create(orderId: Long) = DBAction(parse.json) { 
    implicit f =>
      f.request.body.validate[Cart].map { 
        cart =>
          carts.insert(cart)
          Ok(toJson(cart))
      }.getOrElse(BadRequest("Invalid JSON format"))
  }

  // ... update existing order
  def update(orderId: Long, productId: Long) = DBAction(parse.json) { 
    implicit f =>
      f.request.body.validate[Cart].map { 
        cart =>
          val is_cart = carts.filter(_.orderId === orderId)
          if (is_cart.exists.run) {
            carts.update(cart)
            Ok(toJson(cart))
          } else {
            BadRequest(Json.obj("status" -> "KO", "message" -> "Can't found specific cart"))
          }
      }.getOrElse(BadRequest("Invalid JSON format"))
  }

  // ... delete specific cart
  def delete(orderId: Long, productId: Long) = DBAction { 
    implicit f =>
      val cart = carts.filter(_.orderId === orderId)
      if (cart.exists.run) {
        cart.delete
        Ok(Json.obj("status" -> "OK", "message" -> "Cart was sucessfully deleted"))
      } else {
        BadRequest(Json.obj("status" -> "KO", "message" -> "Can't found specific cart"))
      }
  }
}
