package com.licious.app.food.repository;

import com.licious.app.food.model.Ingredients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IngredientRepo extends JpaRepository<Ingredients,Integer> {
    public Optional<Ingredients> findOneByName(String name);
}
