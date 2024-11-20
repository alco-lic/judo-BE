package com.example.judo.recommendation.service

import com.example.judo.drink.entity.Drink
import com.example.judo.drink.repository.DrinkRepository
import org.springframework.stereotype.Service
import kotlin.math.sqrt

@Service
class DrinkFeatureService(
    private val drinkRepository: DrinkRepository,
) {
    // TasteProfile에 대한 키워드-값 매핑
    private val tasteMapping = mapOf(
        "Smooth" to 1.0,
        "Balanced" to 0.8,
        "Light" to 0.7,
        "Botanical" to 0.9,
        "Peaty" to 0.8,
        "Clean" to 0.75,
        "Sweet" to 1.0,
        "Crisp" to 0.85,
        "Smoky" to 0.9,
        "Fruity" to 1.0,
        "Rich" to 1.2,
        "Herbal" to 0.7,
        "Nutty" to 0.6,
        "Spiced" to 0.8,
        "Bubbly" to 0.6,
        "Bold" to 1.1
    )

    fun getTasteProfileValue(tasteProfile: String?): Double {
        val unknownKeywords = mutableListOf<String>()
        val averageValue = tasteProfile?.split(" ")
            ?.map {
                tasteMapping[it.trim()] ?: run {
                    unknownKeywords.add(it.trim())
                    0.0
                }
            }
            ?.average() ?: 0.0

        return averageValue
    }

    // KNN을 활용한 추천
    fun getKnnRecommendations(userVector: DoubleArray, k: Int, similarityThreshold: Double = 0.5): List<Drink> {
        val allDrinks = drinkRepository.findAll() // 모든 음료 조회
        val drinkDistances: List<Pair<Drink, Double>> = allDrinks.map { drink ->
            val drinkVector = vectorizeDrink(drink) // DoubleArray 반환
            val distance = cosineSimilarity(userVector, drinkVector) // Double 반환
            drink to distance
        }

        // 유사도가 임계값 이상인 음료만 반환
        return drinkDistances
            .filter { it.second >= similarityThreshold }
            .sortedByDescending { it.second } // 유사도가 높은 순으로 정렬
            .take(k) // 상위 k개 선택
            .map { it.first } // 음료 반환
    }

    fun vectorizeUserTasteProfile(userTasteProfile: String): DoubleArray {
        return doubleArrayOf(
            0.0, // type에 대한 기본값
            0.0, // abv에 대한 기본값
            0.0, // price에 대한 기본값
            getTasteProfileValue(userTasteProfile) // TasteProfile 값
        )
    }

    fun vectorizeDrink(drink: Drink): DoubleArray {
        return doubleArrayOf(
            drink.type?.ordinal?.toDouble() ?: 0.0, // 주류 종류
            drink.abv, // 알코올 도수
            drink.price, // 가격
            getTasteProfileValue(drink.tasteProfile) // 맛 프로파일의 수치화 값
        )
    }

    // 두 벡터 간의 코사인 유사도를 계산
    fun cosineSimilarity(vector1: DoubleArray, vector2: DoubleArray): Double {
        // 두 벡터의 내적 계산
        val dotProduct = vector1.zip(vector2).sumOf { it.first * it.second }

        // 각 벡터의 크기(벡터 노름) 계산
        val magnitude1 = sqrt(vector1.sumOf { it * it })
        val magnitude2 = sqrt(vector2.sumOf { it * it })

        // 크기가 0인 경우 유사도를 0으로 설정
        return if (magnitude1 == 0.0 || magnitude2 == 0.0) {
            0.0
        } else {
            dotProduct / (magnitude1 * magnitude2)
        }
    }

    // KNN 메서드: 과정 데이터 추가
    fun getKnnProcessData(userVector: DoubleArray, k: Int): Map<String, Any> {
        val allDrinks = drinkRepository.findAll()
        val drinkDistances: List<Pair<Drink, Double>> = allDrinks.map { drink ->
            val drinkVector = vectorizeDrink(drink)
            val distance = cosineSimilarity(userVector, drinkVector)
            drink to distance
        }

        val sortedDrinks = drinkDistances.sortedByDescending { it.second }

        return mapOf(
            "userVector" to userVector,
            "drinkDistances" to sortedDrinks.map {
                mapOf(
                    "drink" to it.first.name, // 음료 이름
                    "similarity" to it.second // 유사도 점수
                )
            },
            "topKRecommendations" to sortedDrinks.take(k).map {
                mapOf(
                    "id" to it.first.id,
                    "name" to it.first.name,
                    "type" to it.first.type,
                    "abv" to it.first.abv, // 알코올 도수
                    "description" to it.first.description,
                    "similarity" to it.second
                )
            }
        )
    }
}
