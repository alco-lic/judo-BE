package com.example.judo.orders.service

import com.example.judo.cart.repository.CartRepository
import com.example.judo.drink.repository.DrinkRepository
import com.example.judo.orders.dto.OrderRequest
import com.example.judo.orders.entity.OrderItem
import com.example.judo.orders.entity.Orders
import com.example.judo.orders.entity.PaymentHistory
import com.example.judo.orders.repository.OrdersRepository
import com.example.judo.orders.repository.PaymentHistoryRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class OrderService(
    private val cartRepository: CartRepository,
    private val ordersRepository: OrdersRepository,
    private val paymentHistoryRepository: PaymentHistoryRepository,
    ){
    @Transactional
    fun createOrder(memberId: Long, paymentInfo: OrderRequest): Orders {
        // 장바구니 항목 조회
        val cartItems = cartRepository.findByMemberId(memberId)
            ?: throw RuntimeException("장바구니가 비어있거나 존재하지 않습니다.")

        // OrderItem 엔티티 생성
        val orderItems = cartItems.map { cartItem ->
            OrderItem(
                order = null,  // Order가 저장된 후에 참조 설정
                drink = cartItem.drink,
                quantity = cartItem.quantity,
                price = cartItem.drink.price
            )
        }

        // Orders 엔티티 생성
        val order = Orders(
            items = orderItems.toSet(),
            totalAmount = paymentInfo.totalAmount,
            paymentStatus = "성공"
        )

        // 주문 저장
        val savedOrder = ordersRepository.save(order)

        // 주문 항목과 결제 내역 생성 및 저장
        orderItems.forEach { orderItem ->
            orderItem.order = savedOrder

            // PaymentHistory 엔티티 생성
            val paymentHistory = PaymentHistory(
                order = savedOrder,
                product = orderItem.drink,
                amount = orderItem.price,
                paymentStatus = "성공",
                paymentId = paymentInfo.paymentId,
                transactionType = paymentInfo.transactionType,
                txId = paymentInfo.txId,
                totalAmount = paymentInfo.totalAmount,
                address = paymentInfo.address
            )
            paymentHistoryRepository.save(paymentHistory)
        }

        // 장바구니 품목 삭제
        cartRepository.deleteByMemberId(memberId)

        return savedOrder
    }
}
