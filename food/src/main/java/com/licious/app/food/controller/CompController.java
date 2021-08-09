package com.licious.app.food.controller;

import com.licious.app.food.dto.CompositionDto;
import com.licious.app.food.dto.InputDto;
import com.licious.app.food.dto.InsertionDto;
import com.licious.app.food.model.Compositions;
import com.licious.app.food.service.CompService;
import com.licious.app.food.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CompController {
    @Autowired
    CompService compService;
/*
    @GetMapping("/comp/{compositionId}")
    public CompositionDto getCompostionDetailsByCompostionId(@RequestParam int compositionId){
        return compService.getCompositionDetailsByCompositionId(compositionId);
    }
*/
    @GetMapping("/comp/{compositionId}")
    public ResponseEntity <CompositionDto> getCompostionDetailsByCompostionId(@PathVariable(name = "compositionId") int compositionId){
        return ResponseEntity.ok(compService.getCompositionDetailsByCompositionId(compositionId));
    }

    @GetMapping("/compbying")
    public List<Compositions> getCompositionByIngredientDetails(@RequestParam String ingredientName, @RequestParam float strength,@RequestParam String unit){
        List<Compositions> compositionList=compService
                .getAllCompositionsFilteredByIngredientDetails(ingredientName,strength,unit);
        return compositionList;
    }



    /*
    This GET API takes ingredient name,strength,unit as parameter and returns the composition
    that  contains given ingredient in given dosage(strength) and molecule that has given rex_required value.
  */
    @GetMapping("/molecule")
    public List<Compositions> getCompositionByIngredientMoleculeDetails(@RequestParam String ingredientName,
                                                                       @RequestParam float strength,@RequestParam String unit
            ,@RequestParam boolean rex_required ){
        List<Compositions> compositionList=compService
                .getAllCompositionFilteredByIngredientMoleculeDetails(ingredientName,strength,unit,rex_required);
        return compositionList;
    }




    /*
      This POST API takes a list of input objects as request body parameter.
      The input object contains list of ingredients and rex_required value for a composition.
      Each input object will constitute to  one composition and one molecule.
      This POST API can insert more than one composition to the database.
      It is used to insert multiple different compositions.
     */
    @PostMapping(value = "/molecule",consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<InsertionDto> addCompositionMoleculeDetails(@RequestBody List<InputDto> inputRequestObjects){
        int flag=0;
        List<InsertionDto> responseArray=new ArrayList<>();

        for(InputDto inputDTO : inputRequestObjects){
            InsertionDto compositionInsertionStatus=new InsertionDto();

            String compositionName= CommonUtils.createCompositionName(inputDTO.getIngredients()) ;
            String msg=compService.addCompositonMoleculeDetails(inputDTO,compositionName);

            if(msg=="success") flag=1;
            compositionInsertionStatus.setCompositionName(compositionName);
            compositionInsertionStatus.setInsertionStatus(msg);
            responseArray.add(compositionInsertionStatus);

        }

        return responseArray;
    }

}
