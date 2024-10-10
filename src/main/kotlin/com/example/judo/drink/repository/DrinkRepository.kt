package com.example.judo.drink.repository

import com.example.judo.drink.entity.Drink
import org.springframework.data.jpa.repository.JpaRepository

interface DrinkRepository : JpaRepository<Drink, Long>
