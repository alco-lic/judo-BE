package com.example.judo.orders.repository

import com.example.judo.orders.entity.Orders
import com.example.judo.orders.entity.PaymentHistory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PaymentHistoryRepository : JpaRepository<PaymentHistory, Long> {
    fun findAllByOrder(order: Orders): List<PaymentHistory>
}