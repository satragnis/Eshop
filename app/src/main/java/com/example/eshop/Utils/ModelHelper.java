package com.example.eshop.Utils;

import com.example.eshop.Model.Category;
import com.example.eshop.Model.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ModelHelper {

    /**
     * Since each category has a sub cat, and subcat  id is the same as cat id, there is a traversal required to reach the lowest cat id with no sub cat
     * @param products
     * @param categories
     * @param position
     * @return
     */
    public static List<Product> getProductFromCategoryByPosition(List<Product> products, List<Category> categories, int position) {
        List<Integer> childCats = categories.get(position).getChildCategories();
        products.clear();
        products.addAll(categories.get(position).getProducts());

        if(childCats.size()>0){
            int count = childCats.size()-1;
//            for(int child:childCats){
                do {
                    int child = childCats.get(count);
                    for (Category category : categories) {
                        if (category.getId() == child){
                            if (category.getProducts().size() > 0) {
                                products.addAll(category.getProducts());
                                break;
                            } else {
                                if (category.getChildCategories().size() > 0) {
                                    if (!childCats.containsAll(category.getChildCategories())) {
                                        childCats.addAll(category.getChildCategories());
                                        count = count + category.getChildCategories().size();
                                    }
                                }
                            }
                    }
                    }
                    count--;
                }while(count>=0);
//            }
        }
        return products;
    }

//    private static List<Integer> checkAddForDuplicate(List<Integer> childCats, Category category) {
//        List<Integer> newChildCats = category.getChildCategories();
//        List<Integer> childCatsReturn =  new ArrayList<>();
//        childCatsReturn.addAll(childCats);
//        for (Integer integerNew:newChildCats
//             ) {
//            for (Integer old:childCats
//                 ) {
//                if(integerNew.intValue() != old.intValue()){
//                    childCatsReturn.add(integerNew);
//                }
//            }
//        }
//        return childCatsReturn;
//    }


    public static List<Product> getAllProductFromCategories(List<Product> products, List<Category> categories) {
        List<Integer> childCats= new ArrayList<>();
        products.clear();
        for (Category category:categories
             ) {
            products.addAll(category.getProducts());
            childCats.addAll(category.getChildCategories());
            if(childCats.size()>0){
                for(int child:childCats){
                    for(Category category2:categories) {
                        if (category2.getId() == child) {
                            products.addAll(category.getProducts());
                            break;
                        }
                    }
                }
            }
        }
        return products;
    }

    public static HashMap<Integer,Product> getAllProductHashMapFromCategories(List<Product> products, List<Category> categories) {
     HashMap<Integer,Product> integerProductHashMap = new HashMap<>();
        products = getAllProductFromCategories(products, categories);
        for (Product product:products
             ) {
            integerProductHashMap.put(product.getId(),product);
        }
        return integerProductHashMap;
    }

}
