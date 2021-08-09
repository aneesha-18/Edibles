package com.licious.app.food.repository;

import com.licious.app.food.model.Molecules;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MoleculeRepo extends JpaRepository<Molecules,Integer> {
    // get molecule by molecule name
    public Optional<Molecules> findOneByName(String name);
}
