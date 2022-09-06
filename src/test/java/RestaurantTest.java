import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {
    Restaurant restaurant;

    @BeforeEach
    public void setUpRestaurantDetails() {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
        restaurant.addToMenu("Dumplings", 100);
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        LocalTime betweenTime = LocalTime.parse("11:30:00");
        Restaurant mockedRestaurant = Mockito.spy(restaurant);
        Mockito.when(mockedRestaurant.getCurrentTime()).thenReturn(betweenTime);
        boolean result = mockedRestaurant.isRestaurantOpen();
        Assertions.assertTrue(result);
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        LocalTime outsideTime = LocalTime.parse("23:30:00");
        Restaurant mockedRestaurant = Mockito.spy(restaurant);
        Mockito.when(mockedRestaurant.getCurrentTime()).thenReturn(outsideTime);
        boolean result = mockedRestaurant.isRestaurantOpen();
        Assertions.assertFalse(result);

    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws ItemNotFoundException {
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        assertThrows(ItemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    @Test
    public void given_menu_calculate_the_total_cost_of_selected_menu_items() {
        Integer expectedTotal = restaurant.getMenu().stream().filter(item -> item.getName().equals("Dumplings") || item.getName().equals("Sweet corn soup"))
                .map(Item::getPrice).reduce(Integer::sum).get();
        List<String> selectedItems = restaurant.getMenu().stream().filter(item -> item.getName().equals("Dumplings") || item.getName().equals("Sweet corn soup"))
                .map(Item::getName).collect(Collectors.toList());
        Integer actualValue = restaurant.calculateTotalCost(selectedItems);
        assertEquals(expectedTotal,actualValue);
    }

    @Test
    public void given_menu_and_no_items_selected_return_0_on_calling_calculate_the_total_cost_of_items() {
        Integer expectedTotal = 0;
        Integer actualValue = restaurant.calculateTotalCost(new ArrayList<>());
        assertEquals(expectedTotal,actualValue);
    }
}