import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    private String name;
    private String location;
    private LocalTime openingTime;
    private LocalTime closingTime;
    private List<Item> menu = new ArrayList<>();

    public Restaurant(String name, String location, LocalTime openingTime, LocalTime closingTime) {
        this.name = name;
        this.location = location;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
    }

    public boolean isRestaurantOpen() {
        LocalTime currentTime = getCurrentTime();
        return currentTime.isAfter(openingTime) && currentTime.isBefore(closingTime);
    }

    public LocalTime getCurrentTime(){ return  LocalTime.now(); }

    public List<Item> getMenu() {
        return menu;
    }

    public Item findItemByName(String itemName) throws ItemNotFoundException {
        for(Item item: menu) {
            if(item.getName().equals(itemName))
                return item;
        }
        throw new ItemNotFoundException(itemName);
    }

    public void addToMenu(String name, int price) {
        Item newItem = new Item(name,price);
        menu.add(newItem);
    }
    
    public void removeFromMenu(String itemName) throws ItemNotFoundException {
        Item itemToBeRemoved = findItemByName(itemName);
        if (itemToBeRemoved == null)
            throw new ItemNotFoundException(itemName);
        menu.remove(itemToBeRemoved);
    }
    public Integer calculateTotalCost(List<String> menuItems) throws ItemNotFoundException {
        int result = 0;
        for (String menuItem : menuItems) {
            Item item = findItemByName(menuItem);
            result += item.getPrice();
        }
        return result;
    }
    public void displayDetails(){
        System.out.println("Restaurant:"+ name + "\n"
                +"Location:"+ location + "\n"
                +"Opening time:"+ openingTime +"\n"
                +"Closing time:"+ closingTime +"\n"
                +"Menu:"+"\n"+getMenu());

    }
    public String getName() {
        return name;
    }
}
