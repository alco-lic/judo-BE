package com.example.judo.common.authority

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtTokenProvider: JwtTokenProvider,
    @Value("\${cors.allowed.origins}") private val allowedOrigins: List<String> // List<String>으로 설정
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .httpBasic { it.disable() }
            .csrf { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests {
                it.requestMatchers(
                    "/api/member/signup",
                    "/api/member/login",
                    "/api/auth/refresh",
                    "/api/drink/**"
                ).permitAll()
                    .requestMatchers(
                        "/api/member/**",
                        "/api/memo/**",
                        "/api/drink/{drinkId}/reviews", // 리뷰 작성
                        "/api/wishlist/**",
                        "/api/cart/**",
                        "/api/orders/**",
                        "/api/recommendations/**",
                        "/api/drink-feature/**"
                    ).hasRole("MEMBER")
            }
            .exceptionHandling { it.authenticationEntryPoint(customAuthenticationEntryPoint()) }
            .addFilterBefore(
                JwtAuthenticationFilter(jwtTokenProvider),
                UsernamePasswordAuthenticationFilter::class.java
            )
            .cors { it.configurationSource(corsConfigurationSource()) }

        return http.build()
    }

    @Bean
    fun customAuthenticationEntryPoint(): AuthenticationEntryPoint {
        return CustomAuthenticationEntryPoint()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = allowedOrigins // application.yml에서 읽어온 값을 설정
        configuration.allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS")
        configuration.allowedHeaders = listOf("*")
        configuration.allowCredentials = true

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
}
