package com.example.judo.recommendation.service

import com.example.judo.cart.repository.CartRepository
import com.example.judo.common.exception.InvalidInputException
import com.example.judo.drink.entity.Drink
import com.example.judo.drink.repository.DrinkRepository
import com.example.judo.member.repository.MemberRepository
import com.example.judo.orders.repository.OrdersRepository
import com.example.judo.wishlist.repository.WishlistRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class RecommendationService(
    private val wishlistRepository: WishlistRepository,
    private val cartRepository: CartRepository,
    private val ordersRepository: OrdersRepository,
    private val drinkRepository: DrinkRepository,
    private val memberRepository: MemberRepository,
) {

    // 찜한 상품에 대한 유사 상품 추천
    fun getRecommendationsFromWishlist(userId: Long): List<Drink> {
        val findMember = memberRepository.findByIdOrNull(userId)
            ?: throw InvalidInputException("회원 정보가 존재하지 않습니다.")
        val wishlist = wishlistRepository.findAllByMember(findMember)

        // 유사 상품 추천: 같은 카테고리나 맛 프로파일을 가진 상품을 추천
        val recommendedDrinks = mutableSetOf<Drink>()
        wishlist.forEach { wishlistItem ->
            val drink = wishlistItem.drink
            val similarDrinks = drinkRepository.findByTypeOrTasteProfile(drink.type, drink.tasteProfile)
            recommendedDrinks.addAll(similarDrinks)
        }
        return recommendedDrinks.toList()
    }

    // 장바구니에 담은 상품을 바탕으로 관련 상품 추천
    fun getRecommendationsFromCart(userId: Long): List<Drink> {
        val cartItems = cartRepository.findByMemberId(userId)
            ?: throw InvalidInputException("회원 정보가 존재하지 않습니다.")
        // 유사 상품 추천: 장바구니에 담긴 상품과 비슷한 상품 추천
        val recommendedDrinks = mutableSetOf<Drink>()
        cartItems?.forEach { cartItem ->
            val drink = cartItem.drink
            val similarDrinks = drinkRepository.findByTypeOrTasteProfile(drink.type, drink.tasteProfile)
            recommendedDrinks.addAll(similarDrinks)
        }
        return recommendedDrinks.toList()
    }

    // 구매 내역을 바탕으로 비슷한 상품 추천
    fun getRecommendationsFromPaymentHistory(memberId: Long): List<Drink> {
        val findMember = memberRepository.findByIdOrNull(memberId)
            ?: throw InvalidInputException("회원 정보가 존재하지 않습니다.")
        val orders = ordersRepository.findAllByMember(findMember)

        // 유사 상품 추천: 구매한 상품과 비슷한 상품 추천
        val recommendedDrinks = mutableSetOf<Drink>()
        orders.forEach { ordersItem ->
            ordersItem.items.forEach{ ordersItem ->
                val similarDrinks = drinkRepository.findByTypeOrTasteProfile(ordersItem.drink.type, ordersItem.drink.tasteProfile)
            recommendedDrinks.addAll(similarDrinks)
            }
        }

        return recommendedDrinks.toList()
    }

    // 통합 추천 로직: 찜한 상품, 장바구니, 구매 내역을 모두 반영하여 추천
    fun getAllRecommendations(memberId: Long): List<Drink> {
        val wishlistRecommendations = getRecommendationsFromWishlist(memberId)
        val cartRecommendations = getRecommendationsFromCart(memberId)
        val paymentHistoryRecommendations = getRecommendationsFromPaymentHistory(memberId)

        // 중복 제거 후 최종 추천 상품 리스트 반환
        return (wishlistRecommendations + cartRecommendations + paymentHistoryRecommendations).distinct()
    }
}