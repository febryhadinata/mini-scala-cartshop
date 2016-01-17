package models

/**
 *  Order Model
 */

case class Order(id: Long, 
                 total: Double, 
                 status: String)