package com.tathao.orderingcoffee.view.Fragment.CategoryFoodPage;

import com.tathao.orderingcoffee.model.entity.FoodCategory;

import java.util.List;

public interface OnCategoryFoodViewLisener {
    void onGetListCategorySuccess(List<FoodCategory> categoryList);
    void onGetListCategoryFailure();
}
