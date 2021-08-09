package com.licious.app.food.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ingredients")
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ingredients {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;

    @OneToMany(mappedBy = "ingredients")
    @JsonIgnore(true)
    private List<CompositionIngredients> compositionIngredientsList;

    @OneToMany(mappedBy = "ingredients")
    @JsonIgnore(true)
    private List<MoleculeIngredients> moleculeIngredientsList;


}
