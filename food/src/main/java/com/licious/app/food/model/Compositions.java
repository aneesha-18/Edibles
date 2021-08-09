package com.licious.app.food.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="compositions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Compositions {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;

    @OneToMany(mappedBy = "compositions")
    @JsonIgnore(true)
    private List<CompositionIngredients> compositionIngredientsList;
}
