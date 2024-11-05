package com.example.judo.orders.repository

import com.example.judo.orders.entity.PaymentHistory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PaymentHistoryRepository : JpaRepository<PaymentHistory, Long> {
    // 추가적인 쿼리 메서드 필요 시 여기에 정의
}