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
 * Class of Coupon controller, inherits from Base Controller
 */

object CouponController extends Controller {

  // ... initialize object
  val coupons = TableQuery[CouponsTable]
  implicit val couponFormat = Json.format[Coupon]

  // ... get all of coupons
  def getAll = DBAction { 
    implicit f =>
      Ok(toJson(coupons.list))
  }

  // ... find an coupon by specific Id
  def findById(id: Long) = DBAction { 
    implicit f =>
      val coupon = coupons.filter(_.id === id).firstOption
      Ok(toJson(coupon))
  }

  // ... create new coupon
  def create = DBAction(parse.json) { 
    implicit f =>
      f.request.body.validate[Coupon].map { 
        coupon =>
          coupons.insert(coupon)
          Ok(toJson(coupon))
      }.getOrElse(BadRequest("Invalid JSON format"))
  }

  // ... update existing coupon
  def update(id: Long) = DBAction(parse.json) { 
    implicit f =>
      f.request.body.validate[Coupon].map { 
        coupon =>
          val is_coupon = coupons.filter(_.id === id)
          if (is_coupon.exists.run) {
            coupons.update(coupon)
            Ok(toJson(coupon))
          } else {
            BadRequest(Json.obj("status" -> "KO", "message" -> "Can't found specific coupon"))
          }
      }.getOrElse(BadRequest("Invalid JSON format"))
  }
  
  // ... delete specific coupon
  def delete(id: Long) = DBAction { 
    implicit f =>
      val coupon = coupons.filter(_.id === id)
      if (coupon.exists.run) {
        coupon.delete
        Ok(Json.obj("status" -> "OK", "message" -> "Coupon was sucessfully deleted"))
      } else {
        BadRequest(Json.obj("status" -> "KO", "message" -> "Can't found specific coupon"))
      }
  }
}
