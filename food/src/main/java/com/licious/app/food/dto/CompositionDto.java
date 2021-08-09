package com.licious.app.food.dto;

import com.licious.app.food.dto.IngredientsDto;
import lombok.*;

import javax.persistence.Entity;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CompositionDto {
    String compositionName;
    List<IngredientsDto> ingredientsDtoList;
    String moleculeName;
    boolean rx_required;
}
