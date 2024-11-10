package com.example.judo.wishlist.service

import com.example.judo.common.exception.InvalidInputException
import com.example.judo.drink.repository.DrinkRepository
import com.example.judo.member.repository.MemberRepository
import com.example.judo.wishlist.dto.WishlistDto
import com.example.judo.wishlist.entity.Wishlist
import com.example.judo.wishlist.repository.WishlistRepository
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class WishlistService(
    private val wishlistRepository: WishlistRepository,
    private val memberRepository: MemberRepository,
    private val drinkRepository: DrinkRepository,
) {
    @Transactional
    fun toggleWishlist(userId: Long?, wishlistDto: WishlistDto): String {
        val findMember = memberRepository.findByIdOrNull(userId)
            ?: throw InvalidInputException("멤버를 찾을 수 없습니다.")
        val findDrink = drinkRepository.findByIdOrNull(wishlistDto.drink.id)
            ?: throw InvalidInputException("상품을 찾을 수 없습니다.")
        val findWishlist = wishlistRepository.findOneByMemberAndDrink(findMember, findDrink)

        if (findWishlist.isNotEmpty()) { // 이미 찜한 목록에 존재하는 경우
            wishlistRepository.delete(findWishlist.first())
            return "찜한 목록 수정 완료"
        } else { // 찜한 목록에 없는 경우
            // 존재하지 않으면 추가
            val findDrink = drinkRepository.findByIdOrNull(wishlistDto.drink.id)
                ?: throw InvalidInputException("상품을 찾을 수 없습니다.")
            val wishlist = Wishlist(
                member = findMember,
                drink = findDrink,
            )
            wishlistRepository.save(wishlist)
            return "찜한 목록 저장 완료"
        }
    }

    fun getWishlist(userId: Long?): List<WishlistDto> {
        val findMember = memberRepository.findByIdOrNull(userId)
            ?: throw InvalidInputException("멤버를 찾을 수 없습니다.")
        val findWishlist = wishlistRepository.findAllByMember(findMember)
        return findWishlist.map { it.toDto() }
    }

}