package models

/**
 *  Coupon Model
 */

case class Coupon(id: Long, 
                  code: String, 
                  value: Double,
                  is_redeemed: Boolean)

