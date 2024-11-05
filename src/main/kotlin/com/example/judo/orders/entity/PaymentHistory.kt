package com.example.judo.orders.entity

import com.example.judo.drink.entity.Drink
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "payment_history")
class PaymentHistory(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "order_id")
    val order: Orders, // 주문 참조

    @ManyToOne
    @JoinColumn(name = "drink_id")
    val product: Drink, // 결제 상품

    val amount: Double, // 결제 금액
    val paymentStatus: String, // 결제 상태

    val paymentId: String, // 결제 ID
    val transactionType: String, // 거래 타입
    val txId: String, // 거래 ID
    val totalAmount: Double, // 총 금액 (수치형으로 변경)
    val address: String // 배송 주소
)
