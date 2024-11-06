package com.example.judo.orders.entity

import com.example.judo.member.entity.Member
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

/**
 * Orders 엔티티
 * 사용자 주문을 나타냅니다.
 * 주문은 여러 주문 항목을 포함하며, 총 결제 금액 및 결제 상태를 저장합니다.
 */

@Entity
@Table(name = "orders")
class Orders(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    val member: Member, // 주문한 회원 참조

    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL], orphanRemoval = true)
    val items: Set<OrderItem> = emptySet(), // 주문 항목

    val totalAmount: Double, // 총 결제 금액
    val paymentStatus: String // 결제 상태 (예: 성공, 실패)
)

