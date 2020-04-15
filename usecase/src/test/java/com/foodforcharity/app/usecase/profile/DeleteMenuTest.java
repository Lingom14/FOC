package com.foodforcharity.app.usecase.profile;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.foodforcharity.app.domain.constant.Allergen;
import com.foodforcharity.app.domain.constant.Cuisine;
import com.foodforcharity.app.domain.constant.DonorStatus;
import com.foodforcharity.app.domain.constant.Error;
import com.foodforcharity.app.domain.constant.MealType;
import com.foodforcharity.app.domain.constant.SpiceLevel;
import com.foodforcharity.app.domain.entity.Donor;
import com.foodforcharity.app.domain.entity.Food;
import com.foodforcharity.app.domain.reponse.Response;
import com.foodforcharity.app.domain.service.DonorService;
import com.foodforcharity.app.domain.service.FoodService;
import com.foodforcharity.app.mediator.CommandHandler;
import com.foodforcharity.app.usecase.profile.deletemenuitem.DeleteMenuItemCommand;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DeleteMenuTest {

    @Autowired
    CommandHandler<DeleteMenuItemCommand, Response<Void>> handler;

    @Autowired
    FoodService foodRepos;

    @Autowired
    DonorService donorRepos;

    Donor donor;

    Food food;

    @Before
    public void init() {

        final Optional<Food> dbFood = foodRepos.findById(Long.valueOf(1));

        if (dbFood.isPresent()) {
            food = dbFood.get();
            donor = food.getDonor();
        } else {

            Optional<Donor> dbDonor = donorRepos.findByUsername("donoremail@gmail.com");
            if (dbDonor.isEmpty()) {
                donor = new Donor();
                donor.setAddressDescription("DonorAddressDescription");
                donor.setCity("DonorCity");
                donor.setCountry("DonorCountry");
                donor.setDonorName("DonorName");
                donor.setEmail("donoremail@gmail.com");
                donor.setNumberOfRating(0);
                donor.setPassword("DonorPassword");
                donor.setPhoneNumber("DonorPhoneNumber");
                donor.setRating(0);
                donor.setDiscountApplied(0);
                donor.setUsername(donor.getEmail());
            } else {
                donor = dbDonor.get();
            }

            donor.setDonorStatus(DonorStatus.Active);

            //
            food = new Food();
            food.setFoodName("foodName");
            food.setDescriptionText("descriptionText");
            food.setCuisine(Cuisine.Belgravian);
            food.setMealType(MealType.Mixed);
            food.setPrice(200);
            food.setQuantityAvailable(23);
            food.setMealForNPeople(3);
            food.setSpiceLevel(SpiceLevel.MildSpice);

            // food.setMealTypes(MealType.RedMeat);
            final List<Allergen> aList = Arrays.asList(Allergen.Dairy);
            final Set<Allergen> iSet = new HashSet<Allergen>(aList);
            food.setDonor(donor);
            food.setAllergens(iSet);
            donor.addFood(food);
            donor = donorRepos.save(donor);
        }
    }

    @Test
    public void successTest() {
        DeleteMenuItemCommand command = new DeleteMenuItemCommand(donor.getId(), food.getId());

        Response<Void> response = handler.handle(command);
        assert (response.success());
    }

    @Test
    public void foodDoesNotExistTest() {
        assert (handler.handle(new DeleteMenuItemCommand(donor.getId(), Long.valueOf(100)))
                .getError() == Error.FoodDoesNotExist);
    }

    @Test
    public void foodsDonorMismatchTest() {
        assert (handler.handle(new DeleteMenuItemCommand(Long.valueOf(100), food.getId()))
                .getError() == Error.FoodsDonorMismatch);
    }

}