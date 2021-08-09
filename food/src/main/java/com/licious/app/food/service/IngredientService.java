package com.licious.app.food.service;

import com.licious.app.food.model.Ingredients;
import com.licious.app.food.repository.IngredientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {

    @Autowired
    private IngredientRepo ingredientRepo;

    // get the ingredient by Id
    public Ingredients getIngredientById(int id){

        return ingredientRepo.getById(id);
    }

    // get the ingredient by Name
    public Ingredients getIngredientByName(String name){

        return ingredientRepo.findOneByName(name).get();
    }

    // adding ingredient to database
    public void addIngredient(Ingredients ingredients){

        ingredientRepo.save(ingredients);
    }

    // getting list of all the ingredients.
    public List<Ingredients> getIngredientList(){

        return ingredientRepo.findAll();
    }





}
