package com.tathao.orderingcoffee.model.CategoryFoodModel;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import com.tathao.orderingcoffee.model.entity.FoodCategory;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CategoryFoodModel {
    private OnCategoryFoodLisener onCategoryFoodLisener;
    public CategoryFoodModel(OnCategoryFoodLisener onCategoryFoodLisener) {
        this.onCategoryFoodLisener = onCategoryFoodLisener;
    }

    public void getListCategory(String json) {
        List<FoodCategory> foodCategoryLis = new ArrayList<>();
        try {
            if (!json.isEmpty()) {
                Moshi moshi = new Moshi.Builder().build();
                Type type = Types.newParameterizedType(List.class, FoodCategory.class);
                JsonAdapter<List<FoodCategory>> jsonAdapter = moshi.adapter(type);
                foodCategoryLis = jsonAdapter.fromJson(json);
                onCategoryFoodLisener.onGetListCategorySuccess(foodCategoryLis);
            }
            else {
                onCategoryFoodLisener.onGetListCategoryFailure();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
