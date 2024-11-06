package com.example.judo.orders.repository

import com.example.judo.member.entity.Member
import com.example.judo.orders.entity.Orders
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrdersRepository : JpaRepository<Orders, Long> {
    fun findAllByMember(member: Member): List<Orders>
}