package com.tathao.orderingcoffee.model.CategoryFoodModel;

import com.tathao.orderingcoffee.model.entity.FoodCategory;

import java.util.List;

public interface OnCategoryFoodLisener {
    void onGetListCategorySuccess(List<FoodCategory> foodCategories);
    void onGetListCategoryFailure();
}
