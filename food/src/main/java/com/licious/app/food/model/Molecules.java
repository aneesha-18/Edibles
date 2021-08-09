package com.licious.app.food.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "molecules")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Molecules {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private Boolean rx_required;

    @OneToMany(mappedBy = "molecules")
    @JsonIgnore(true)
    private List<MoleculeIngredients> moleculeIngredientsList;
}
