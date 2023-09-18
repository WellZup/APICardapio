package com.cardapio.restaurant.controller;

import com.cardapio.restaurant.domain.dto.FoodRequestDTO;
import com.cardapio.restaurant.domain.dto.FoodResponseDTO;
import com.cardapio.restaurant.domain.entity.Food;
import com.cardapio.restaurant.repository.FoodRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "food", produces = {"application/json"})
@Tag(name = "food")
public class FoodController {

    @Autowired
    private FoodRepository repository;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Realiza o upload de novos itens no cardápio", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Upload de item realizado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a inserção de novo item"),
    })
    public void saveFood(@RequestBody FoodRequestDTO data){
        Food foodData = new Food(data);
        repository.save(foodData);
        return;
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    @Operation(summary = "Busca dados de items no banco de dados", method = "GET")

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
    })
    public List<FoodResponseDTO> getAll(){

        List<FoodResponseDTO> foodList = repository.findAll().stream().map(FoodResponseDTO::new).toList();
        return foodList;
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Busca dados de item por id", method = "GET")

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
    })
    public ResponseEntity<FoodResponseDTO> getFoodById(@PathVariable Long id) {
        Optional<Food> foodOptional = repository.findById(id);
        if (foodOptional.isPresent()) {
            FoodResponseDTO foodResponseDTO = new FoodResponseDTO(foodOptional.get());
            return ResponseEntity.ok(foodResponseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.PUT})
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Atualiza dados de itens por id", method = "PUT")
    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE)

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
    })
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
    @DeleteMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Deleta dados contidos no banco de dados por id", method = "DELETE")

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deletado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
    })
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
