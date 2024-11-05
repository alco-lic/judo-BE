package com.example.judo.orders.dto

data class OrderRequest(
    val paymentId: String, // 결제 ID
    val transactionType: String, // 거래 타입
    val txId: String, // 거래 ID
    val totalAmount: Double, // 총 금액
    val address: String, // 배송 주소
    val items: List<OrderItemRequest> // 주문 항목 리스트
)

// 주문 항목 데이터 클래스 추가
data class OrderItemRequest(
    val id: Long, // 주문 항목 ID
    val drinkId: Long, // 음료 ID
    val quantity: Int,
    val name: String, // 음료 이름
    val price: Double // 가격
)
