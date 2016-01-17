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
 * Class of Product controller, inherits from Base Controller
 */
 
object ProductController extends Controller {

  // ... initialize object
  val products = TableQuery[ProductsTable]
  implicit val productFormat = Json.format[Product]

  // ... get all of products
  def getAll = DBAction { 
    implicit f =>
      Ok(toJson(products.list))
  }

  // ... find a product by specific Id
  def findById(id: Long) = DBAction { 
    implicit f =>
      val product = products.filter(_.id === id).firstOption
      Ok(toJson(product))
  }

  // ... create new product
  def create = DBAction(parse.json) { 
    implicit f =>
      f.request.body.validate[Product].map { 
        product =>
          products.insert(product)
          Ok(toJson(product))
      }.getOrElse(BadRequest("Invalid JSON format"))
  }

  // ... update existing product
  def update(id: Long) = DBAction(parse.json) { 
    implicit f =>
      f.request.body.validate[Product].map { 
        product =>
          val is_product = products.filter(_.id === id)
          if (is_product.exists.run) {
            products.update(product)
            Ok(toJson(product))
          } else {
            BadRequest(Json.obj("status" -> "KO", "message" -> "Can't found specific product"))
          }
      }.getOrElse(BadRequest("Invalid JSON format"))
  }

  // ... delete specific product
  def delete(id: Long) = DBAction { 
    implicit f =>
      val product = products.filter(_.id === id)
      if (product.exists.run) {
        product.delete
        Ok(Json.obj("status" -> "OK", "message" -> "Product was sucessfully deleted"))
      } else {
        BadRequest(Json.obj("status" -> "KO", "message" -> "Can't found specific product"))
      }
  }
}
