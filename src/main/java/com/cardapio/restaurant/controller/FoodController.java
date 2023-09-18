package com.cardapio.restaurant.controller;

import com.cardapio.restaurant.domain.dto.FoodRequestDTO;
import com.cardapio.restaurant.domain.dto.FoodResponseDTO;
import com.cardapio.restaurant.domain.entity.Food;
import com.cardapio.restaurant.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("food")
public class FoodController {

    @Autowired
    private FoodRepository repository;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping
    public void saveFood(@RequestBody FoodRequestDTO data){
        Food foodData = new Food(data);
        repository.save(foodData);
        return;
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    public List<FoodResponseDTO> getAll(){

        List<FoodResponseDTO> foodList = repository.findAll().stream().map(FoodResponseDTO::new).toList();
        return foodList;
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/{id}")
    public ResponseEntity<FoodResponseDTO> getFoodById(@PathVariable Long id) {
        Optional<Food> foodOptional = repository.findById(id);
        if (foodOptional.isPresent()) {
            FoodResponseDTO foodResponseDTO = new FoodResponseDTO(foodOptional.get());
            return ResponseEntity.ok(foodResponseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateFood(@PathVariable Long id, @RequestBody FoodRequestDTO foodRequestDTO) {
        Optional<Food> foodOptional = repository.findById(id);
        if (foodOptional.isPresent()) {
            Food food = foodOptional.get();
            food.setTitle(foodRequestDTO.Title());
            food.setImage(foodRequestDTO.image());
            food.setPrice(foodRequestDTO.price());
            repository.save(food);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFood(@PathVariable Long id) {
        Optional<Food> foodOptional = repository.findById(id);
        if (foodOptional.isPresent()) {
            repository.delete(foodOptional.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }





}
