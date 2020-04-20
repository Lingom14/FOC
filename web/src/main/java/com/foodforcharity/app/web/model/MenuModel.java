package com.foodforcharity.app.web.model;

import java.util.List;
import java.util.Set;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import com.foodforcharity.app.domain.constant.Allergen;
import com.foodforcharity.app.domain.constant.Cuisine;
import com.foodforcharity.app.domain.constant.MealType;
import com.foodforcharity.app.domain.constant.SpiceLevel;

import lombok.Data;

@Data
public class MenuModel extends Request{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @NotNull(message = "Food name cannot be empty")
    private String foodName;
    
    @NotNull(message = "Description text cannot be empty")
    private String descriptionText;
    
    @NotNull(message = "Price cannot be empty")
    @Digits(integer = Integer.MAX_VALUE, fraction = 2, message = "Price should be digitis and 2 decimals only")
    private int originalPrice;
    
    @NotNull(message = "Meal for number of people cannot be empty")
    @Digits(integer = Integer.MAX_VALUE, fraction = 0, message = "The number should be digits")
    private int mealForNPeople;
    
    @NotNull(message = "Quantity available cannot be empty")
    @Digits(integer = Integer.MAX_VALUE, fraction = 0, message = "The number should be digits")
    private int quantityAvailable;
    
    @NotNull(message = "Spice level cannot be empty")
    @Digits(integer = Integer.MAX_VALUE, fraction = 0, message = "The number should be digits")
    private List<SpiceLevel> spiceLevel;
    
    @NotNull(message = "Meal type cannot be empty")
    private List<MealType> mealTypes;
    
    @NotNull(message = "Cuisine cannot be empty")
    private List<Cuisine> cuisines;
    
    @NotNull(message = "Allergen cannot be empty")
    private Set<Allergen> allergen;

}