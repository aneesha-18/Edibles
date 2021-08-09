package com.licious.app.food.repository;

import com.licious.app.food.model.Compositions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompositionRepo extends JpaRepository<Compositions, Integer> {
    // get composition by compostion name.
    public Optional<Compositions> findOneByName(String name);
}
