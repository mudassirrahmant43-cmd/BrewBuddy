package com.brewbuddy.app.data;

import com.brewbuddy.app.R;
import com.brewbuddy.app.model.Category;
import com.brewbuddy.app.model.CoffeeItem;

import java.util.ArrayList;
import java.util.List;

public class CoffeeRepository {

    private static List<CoffeeItem> coffeeItems;

    public static List<CoffeeItem> getAllCoffeeItems() {
        if (coffeeItems == null) {
            coffeeItems = new ArrayList<>();
            coffeeItems.add(new CoffeeItem(
                    "1",
                    "Classic Espresso",
                    "Rich, concentrated dark roast espresso with a dense, golden crema layer.",
                    3.50,
                    Category.HOT,
                    R.drawable.ic_coffee_espresso,
                    true,
                    false
            ));
            coffeeItems.add(new CoffeeItem(
                    "2",
                    "Caffè Americano",
                    "Smooth espresso shots diluted with hot water for a clean, bold coffee experience.",
                    3.75,
                    Category.HOT,
                    R.drawable.ic_coffee_americano,
                    false,
                    false
            ));
            coffeeItems.add(new CoffeeItem(
                    "3",
                    "Velvet Cappuccino",
                    "Equal parts dark espresso, steamed milk, and airy, thick milk foam dusted with cocoa.",
                    4.50,
                    Category.HOT,
                    R.drawable.ic_coffee_cappuccino,
                    true,
                    false
            ));
            coffeeItems.add(new CoffeeItem(
                    "4",
                    "Caffè Latte",
                    "Silky steamed milk poured gently over double espresso with delicate microfoam art.",
                    4.75,
                    Category.HOT,
                    R.drawable.ic_coffee_latte,
                    true,
                    false
            ));
            coffeeItems.add(new CoffeeItem(
                    "5",
                    "Dark Chocolate Mocha",
                    "Rich espresso combined with premium Belgian chocolate, steamed milk, and whipped cream.",
                    5.25,
                    Category.SPECIALTY,
                    R.drawable.ic_coffee_mocha,
                    false,
                    false
            ));
            coffeeItems.add(new CoffeeItem(
                    "6",
                    "Caramel Cloud Latte",
                    "Sweet buttery caramel syrup blended with espresso, silky milk, and caramel drizzle.",
                    5.50,
                    Category.SPECIALTY,
                    R.drawable.ic_coffee_caramel_latte,
                    true,
                    false
            ));
            coffeeItems.add(new CoffeeItem(
                    "7",
                    "Iced Coffee",
                    "Freshly brewed specialty roast chilled over ice cubes with custom milk option.",
                    4.25,
                    Category.ICED,
                    R.drawable.ic_coffee_iced,
                    false,
                    false
            ));
            coffeeItems.add(new CoffeeItem(
                    "8",
                    "Artisanal Cold Brew",
                    "Slow-steeped for 18 hours for maximum smoothness and low acidity with chocolate notes.",
                    5.00,
                    Category.ICED,
                    R.drawable.ic_coffee_cold_brew,
                    true,
                    true
            ));
        }
        return coffeeItems;
    }

    public static List<CoffeeItem> getPopularItems() {
        List<CoffeeItem> popular = new ArrayList<>();
        for (CoffeeItem item : getAllCoffeeItems()) {
            if (item.isPopular()) {
                popular.add(item);
            }
        }
        return popular;
    }

    public static CoffeeItem getFeaturedItem() {
        for (CoffeeItem item : getAllCoffeeItems()) {
            if (item.isFeatured()) {
                return item;
            }
        }
        return getAllCoffeeItems().get(0);
    }

    public static List<CoffeeItem> getByCategory(Category category) {
        if (category == Category.ALL) {
            return getAllCoffeeItems();
        }
        List<CoffeeItem> filtered = new ArrayList<>();
        for (CoffeeItem item : getAllCoffeeItems()) {
            if (item.getCategory() == category) {
                filtered.add(item);
            }
        }
        return filtered;
    }
}
