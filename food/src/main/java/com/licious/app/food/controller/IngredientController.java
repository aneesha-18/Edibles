package com.licious.app.food.controller;

import com.licious.app.food.model.Ingredients;
import com.licious.app.food.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class IngredientController {

    @Autowired
    IngredientService ingredientService;

    @PostMapping("/addingre/{name}")
    public ResponseEntity<String> addIngredient(@PathVariable(name = "name") String name){
        Ingredients ingredients=new Ingredients();
        ingredients.setName(name);
        ingredientService.addIngredient(ingredients);
        return ResponseEntity.ok("added");
    }

    @GetMapping("/ingredient/all")
    public List<Ingredients> getAllIngredients(){
        return ingredientService.getIngredientList();
    }
}
