package models

/**
 *  Order Model
 */

case class Order(id: Long, 
                 couponId: Long,
                 total: Double, 
                 status: String)