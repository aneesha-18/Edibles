package com.licious.app.food.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="composition_ingredients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CompositionIngredients {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String unit;
    private float strength;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="composition_id",referencedColumnName = "id")
    private Compositions compositions;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="ingredient_id",referencedColumnName = "id")
    private Ingredients ingredients;


}
