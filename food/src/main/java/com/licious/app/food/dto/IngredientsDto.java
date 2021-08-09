package com.licious.app.food.dto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class IngredientsDto {
    String ingredientName;
    float strength;
    String unit;


    public String getIngredientName() {
        return ingredientName;
    }
}
