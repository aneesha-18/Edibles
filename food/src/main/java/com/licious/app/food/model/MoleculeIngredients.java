package com.licious.app.food.model;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="molecule_ingredients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MoleculeIngredients {
    @Id
    private int id;
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="molecule_id",referencedColumnName = "id")
    private Molecules molecules;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="ingredient_id",referencedColumnName = "id")
    private Ingredients ingredients;
}
