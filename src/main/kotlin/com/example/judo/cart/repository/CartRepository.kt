package com.example.judo.cart.repository

import com.example.judo.cart.entity.Cart
import com.example.judo.drink.entity.Drink
import com.example.judo.member.entity.Member
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query

interface CartRepository : JpaRepository<Cart, Long> {
    fun findOneByMemberAndDrink(member: Member, drink: Drink): List<Cart>
    fun findAllByMember(member: Member): List<Cart>
    fun findByMemberId(memberId: Long): List<Cart>?

    @Modifying
    @Transactional
    @Query("DELETE FROM Cart c WHERE c.member.id = :memberId")
    fun deleteByMemberId(memberId: Long)
}
