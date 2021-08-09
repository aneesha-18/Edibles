package com.licious.app.food.repository;

import com.licious.app.food.model.MoleculeIngredients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;

@Repository
public interface MoleculeIngredientsRepo extends JpaRepository<MoleculeIngredients,Integer> {
    @Query(value = "select ingredient_id from molecule_ingredients where molecule_id=?1",nativeQuery = true)
    public List<Integer> findAllMoleculeIngredientsByMoleculeId(int molecule_id);
}
