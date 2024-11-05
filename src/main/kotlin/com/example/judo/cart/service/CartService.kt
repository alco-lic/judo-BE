package com.example.judo.cart.service

import com.example.judo.cart.dto.CartDto
import com.example.judo.cart.entity.Cart
import com.example.judo.cart.repository.CartRepository
import com.example.judo.common.exception.InvalidInputException
import com.example.judo.drink.repository.DrinkRepository
import com.example.judo.member.repository.MemberRepository
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class CartService(
    private val cartRepository: CartRepository,
    private val memberRepository: MemberRepository,
    private val drinkRepository: DrinkRepository,
) {
    @Transactional
    fun toggleCart(userId: Long?, cartDto: CartDto): String {
        val findMember = memberRepository.findByIdOrNull(userId)
            ?: throw InvalidInputException("멤버를 찾을 수 없습니다.")
        val findDrink = drinkRepository.findByIdOrNull(cartDto.drinkId)
            ?: throw InvalidInputException("상품을 찾을 수 없습니다.")
        val findCart = cartRepository.findOneByMemberAndDrink(findMember, findDrink)

        if (findCart.isNotEmpty()) {
            return "이미 장바구니에 존재하는 상품입니다."
        } else { // 찜한 목록에 없는 경우
            // 존재하지 않으면 추가
            val findDrink = drinkRepository.findByIdOrNull(cartDto.drinkId)
                ?: throw InvalidInputException("상품을 찾을 수 없습니다.")
            val cart = Cart(
                member = findMember,
                drink = findDrink,
                quantity = 1,
            )
            cartRepository.save(cart)
            return "장바구니 목록 저장 완료"
        }
    }

    fun getCart(userId: Long?): List<CartDto> {
        val findMember = memberRepository.findByIdOrNull(userId)
            ?: throw InvalidInputException("멤버를 찾을 수 없습니다.")
        val findCart = cartRepository.findAllByMember(findMember)
        return findCart.map { it.toDto() }
    }
}