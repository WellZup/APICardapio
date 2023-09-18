package com.cardapio.restaurant.domain.dto;

import com.cardapio.restaurant.domain.entity.Food;


public record FoodResponseDTO(Long id, String Title, String image, Integer price){
    public FoodResponseDTO(Food food) {
        this(food.getId(), food.getTitle(), food.getImage(), food.getPrice());
    }
}
