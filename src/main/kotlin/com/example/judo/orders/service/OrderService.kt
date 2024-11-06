package com.example.judo.orders.service

import com.example.judo.cart.repository.CartRepository
import com.example.judo.drink.repository.DrinkRepository
import com.example.judo.member.repository.MemberRepository
import com.example.judo.orders.dto.OrderRequest
import com.example.judo.orders.dto.PaymentHistoryResponse
import com.example.judo.orders.entity.OrderItem
import com.example.judo.orders.entity.Orders
import com.example.judo.orders.entity.PaymentHistory
import com.example.judo.orders.repository.OrdersRepository
import com.example.judo.orders.repository.PaymentHistoryRepository
import jakarta.transaction.Transactional
import lombok.extern.slf4j.Slf4j
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
@Slf4j
class OrderService(
    private val cartRepository: CartRepository,
    private val ordersRepository: OrdersRepository,
    private val paymentHistoryRepository: PaymentHistoryRepository,
    private val memberRepository: MemberRepository,
    ){
    val logger: Logger = LoggerFactory.getLogger(OrderService::class.java)

    @Transactional
    fun createOrder(userId: Long, paymentInfo: OrderRequest): String {
        // 장바구니 항목 조회
        val cartItems = cartRepository.findByMemberId(userId)
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

        val findMember = memberRepository.findByIdOrNull(userId)
            ?: throw RuntimeException("유저가 존재하지 않습니다..")

        // Orders 엔티티 생성
        val order = Orders(
            items = orderItems.toSet(),
            totalAmount = paymentInfo.totalAmount,
            paymentStatus = "성공",
            member = findMember,
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
        cartRepository.deleteByMemberId(userId)

        return "주문이 완료되었습니다"
    }

    fun getPaymentHistoryByMember(userId: Long): List<PaymentHistoryResponse> {
        val findMember = memberRepository.findByIdOrNull(userId)
            ?: throw RuntimeException("유저를 찾을 수 없습니다.")

        // 멤버의 모든 주문 가져오기
        val orders = ordersRepository.findAllByMember(findMember)

        logger.info(orders.toString())

        // 각 주문에 대한 PaymentHistory 목록을 가져와서 응답으로 변환
        val paymentHistories = orders.flatMap { order ->
            paymentHistoryRepository.findAllByOrder(order)
        }

        logger.info(paymentHistories.toString())

        return paymentHistories.map { it.toDto() }
    }
}
