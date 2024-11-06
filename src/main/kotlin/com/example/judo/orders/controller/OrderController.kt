package com.example.judo.orders.controller

import com.example.judo.common.dto.BaseResponse
import com.example.judo.common.dto.CustomUser
import com.example.judo.orders.dto.OrderRequest
import com.example.judo.orders.dto.PaymentHistoryResponse
import com.example.judo.orders.service.OrderService
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/orders")
class OrderController(
    private val orderService: OrderService,
    ) {
    @PostMapping("/create")
    fun createOrder(@RequestBody orderRequest: OrderRequest): BaseResponse<Unit> {
        val userId = (SecurityContextHolder.getContext().authentication.principal as CustomUser).userId
            ?: return BaseResponse(message = "유저를 찾을 수 없습니다")

        val result = orderService.createOrder(userId, orderRequest)
        return BaseResponse(message = result) // 생성된 주문 반환
    }

    @GetMapping("/history")
    fun getOrderHistory(): BaseResponse<List<PaymentHistoryResponse>> {
        val userId = (SecurityContextHolder.getContext().authentication.principal as CustomUser).userId
            ?: return BaseResponse(message = "유저를 찾을 수 없습니다.")

        val result = orderService.getPaymentHistoryByMember(userId)
        return BaseResponse(data = result)
    }
}