package com.example.judo.orders.entity

import com.example.judo.drink.entity.Drink
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

/**
 * OrderItem 엔티티
 * 주문에 포함된 특정 음료 항목을 나타냅니다.
 * 주문 항목은 주문, 음료, 수량 및 가격 정보를 포함합니다.
 */

@Entity
@Table(name = "order_item")
class OrderItem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    var order: Orders? = null, // 주문 참조 (null 가능)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drink_id")
    val drink: Drink, // 주문한 음료

    val quantity: Int, // 수량
    val price: Double // 가격 (주문 시점의 가격)
)
