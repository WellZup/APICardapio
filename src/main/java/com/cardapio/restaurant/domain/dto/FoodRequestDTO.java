package com.cardapio.restaurant.domain.dto;

import com.cardapio.restaurant.domain.entity.Food;

public record FoodRequestDTO (String Title, String image, Integer price){
    public FoodRequestDTO(Food food) {
        this(food.getTitle(), food.getImage(), food.getPrice());
    }
}
