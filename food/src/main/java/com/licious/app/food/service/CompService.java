package com.licious.app.food.service;


import com.licious.app.food.dto.CompositionDto;
import com.licious.app.food.dto.IngredientsDto;
import com.licious.app.food.dto.InputDto;
import com.licious.app.food.model.*;
import com.licious.app.food.repository.*;
import com.licious.app.food.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompService {

    @Autowired
    private CompositionIngredientsRepo compositionIngredientRepository;
    @Autowired
    private IngredientRepo ingredientsRepository;
    @Autowired
    private MoleculeRepo moleculesRepository;
    @Autowired
    private MoleculeIngredientsRepo moleculeIngredientRepository;
    @Autowired
    private CompositionRepo compositionsRepository;





    public CompositionDto getCompositionDetailsByCompositionId(int compositionId){

        CompositionDto compositionDto=new CompositionDto();

        String compositionName=compositionsRepository.findById(compositionId).get().getName();

        List<Tuple> ingredientsTuple=compositionIngredientRepository.findAllIngredientByCompostionId(compositionId);

        List<IngredientsDto> ingredientsDtoList=ingredientsTuple.stream()
                .map(t->new IngredientsDto(t.get(0,String.class),t.get(1,Float.class),t.get(2,String.class))).collect(Collectors.toList());

        List<Integer> ingredientIds=ingredientsTuple.stream().map(t->t.get(3,Integer.class)).collect(Collectors.toList());


        Molecules molecules=findMoleculeWithGivenIngredients(ingredientIds);

        compositionDto.setCompositionName(compositionName);
        compositionDto.setIngredientsDtoList(ingredientsDtoList);
        compositionDto.setMoleculeName(molecules.getName());
        compositionDto.setRx_required(molecules.getRx_required());

        return compositionDto;
    }






    public Molecules findMoleculeWithGivenIngredients(List<Integer> ingredientIds){

        List<Molecules> moleculeList=moleculesRepository.findAll();

        for(Molecules m:moleculeList){

            //finding corresponding ingredientList
            List<Integer> moleculeIngredientIds=moleculeIngredientRepository.findAllMoleculeIngredientsByMoleculeId(m.getId());
            System.out.println(m.getId());
            System.out.println(moleculeIngredientIds);

            if(moleculeIngredientIds.containsAll(ingredientIds)&&ingredientIds.containsAll(moleculeIngredientIds)){
                return moleculesRepository.getById(m.getId());
            }
        }
        return null;
    }






/* Finding compositions when ingredient name, strength and unit are given */
    public List<Compositions> getAllCompositionsFilteredByIngredientDetails(String ingredientName, float strength,String unit){

        int ingredientId=ingredientsRepository.findOneByName(ingredientName).get().getId();
        List<Integer> compositionIds=compositionIngredientRepository.
                findAllByIngredientStrengthUnit(ingredientId,strength,unit);
        List<Compositions> compositionList=new ArrayList<>();

        for(Integer c: compositionIds){
            Compositions composition=compositionsRepository.findById(c).get();
            compositionList.add(composition);
        }
        return compositionList;
    }





    /* get all compositions that contains given ingredient with given dosage and molecule with given rx_required value */
    public List<Compositions> getAllCompositionFilteredByIngredientMoleculeDetails(String ingredientName, float strength,String unit, boolean rex_required){
        int ingredientId=ingredientsRepository.findOneByName(ingredientName).get().getId();
        List<Integer> compositionIds=compositionIngredientRepository.findAllCompositionsByIngredientMoleculeDetails(ingredientId,strength,unit,rex_required);

        List<Compositions> compositionList=new ArrayList<>();
        for(Integer compositionId: compositionIds){
            Compositions composition=compositionsRepository.findById(compositionId).get();
            compositionList.add(composition);
        }
        return compositionList;
    }







    // adding new compositions to the database.
    public String addCompositonMoleculeDetails(InputDto inputDTO, String compositionName){
        System.out.println(inputDTO);
        List<String> existingIngredientNames=getIngredientNames();
        List<String> existingMoleculeNames=getMoleculeNames();
        List<String> existingCompositionNames=getCompositeNames();
        Boolean rx_required=inputDTO.isRx_required();

        List<IngredientsDto> ingredientDetailsList=inputDTO.getIngredients();
        for(IngredientsDto ingredient: ingredientDetailsList){
            String ingredientName=ingredient.getIngredientName();
            float strength=ingredient.getStrength();
            String unit=ingredient.getUnit();
            if(existingIngredientNames.indexOf(ingredientName)==-1){
                return "ingredient: "+ingredientName+" does not exist. Insertion of this composition failure";
               /*
               Ingredient ingredient1=new Ingredient();
               ingredient1.setName(ingredientName);
               ingredientsRepository.save(ingredient1);
                */
            }

        }
        List<String> ingredientNameList=ingredientDetailsList.stream().map(i->i.getIngredientName()).collect(Collectors.toList());

        String moleculeName= CommonUtils.createMoleculeName(ingredientNameList);

        if(existingMoleculeNames.indexOf(moleculeName)==-1){
            Molecules molecule=new Molecules();
            molecule.setName(moleculeName);
            molecule.setRx_required(rx_required);

            moleculesRepository.save(molecule);
        }

        Molecules molecule=moleculesRepository.findOneByName(moleculeName).get();
        molecule.setRx_required(rx_required);
        moleculesRepository.save(molecule);
        System.out.println(rx_required);

        int moleculeId=molecule.getId();
        if(existingCompositionNames.indexOf(compositionName)>0) {
            return "composition already exists";
        }
        else {
            Compositions composition=new Compositions();
            composition.setName(compositionName);
            compositionsRepository.save(composition);
        }
        Compositions composition=compositionsRepository.findOneByName(compositionName).get();
        int compositionId=composition.getId();
        for(IngredientsDto ingredientDetail:ingredientDetailsList){
            Ingredients ingredient=ingredientsRepository.findOneByName(ingredientDetail.getIngredientName()).get();

            composition=null;
            composition=compositionsRepository.findById(compositionId).get();

            CompositionIngredients compositionIngredient=new CompositionIngredients();
            compositionIngredient.setCompositions(composition);
            compositionIngredient.setIngredients(ingredient);
            compositionIngredient.setStrength(ingredientDetail.getStrength());
            compositionIngredient.setUnit(ingredientDetail.getUnit());
            compositionIngredientRepository.save(compositionIngredient);

            MoleculeIngredients moleculeIngredient=new MoleculeIngredients();
            moleculeIngredient.setMolecules(molecule);
            moleculeIngredient.setIngredients(ingredient);
            moleculeIngredientRepository.save(moleculeIngredient);

        }
        return "success";
    }




    // get a list of names of all the ingredients
    public List<String> getIngredientNames(){
        return ingredientsRepository.findAll().stream().map(t->t.getName()).collect(Collectors.toList());
    }

    // get a list of names of all the molecules.
    public List<String> getMoleculeNames(){
        return moleculesRepository.findAll().stream().map(t->t.getName()).collect(Collectors.toList());
    }

    // get a list of names of all the compositons.
    public List<String> getCompositeNames(){
        return compositionsRepository.findAll().stream().map(t->t.getName()).collect(Collectors.toList());
    }


}
