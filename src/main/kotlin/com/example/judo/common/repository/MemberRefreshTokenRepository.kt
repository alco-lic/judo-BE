package com.example.judo.common.repository

import org.springframework.data.jpa.repository.JpaRepository
import com.example.judo.common.entity.MemberRefreshToken
import com.example.judo.member.entity.Member

interface MemberRefreshTokenRepository : JpaRepository<MemberRefreshToken, Long> {
    fun findByMember(member: Member): MemberRefreshToken?
}
