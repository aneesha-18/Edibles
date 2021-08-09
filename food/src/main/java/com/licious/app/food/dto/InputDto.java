package com.licious.app.food.dto;


import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class InputDto {
    /* This DTO is a request body that has list of ingredient details and rex_required of
     *  the related molecule.
     *  This DTO is a request body and given as input from postman-client.
     * */
    private List<IngredientsDto> ingredients;
    private boolean rx_required;
}
