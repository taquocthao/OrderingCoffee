package com.tathao.orderingcoffee.presenter.CategoryFoodPresenter;

import com.tathao.orderingcoffee.model.CategoryFoodModel.CategoryFoodModel;
import com.tathao.orderingcoffee.model.CategoryFoodModel.OnCategoryFoodLisener;
import com.tathao.orderingcoffee.model.entity.FoodCategory;
import com.tathao.orderingcoffee.view.Fragment.CategoryFoodPage.OnCategoryFoodViewLisener;

import java.util.List;

public class CategoryFoodPresenter implements OnCategoryFoodLisener {

    private CategoryFoodModel categoryFoodModel;
    private OnCategoryFoodViewLisener onCategoryFoodViewLisener;

    public CategoryFoodPresenter(OnCategoryFoodViewLisener onCategoryFoodViewLisener){
        categoryFoodModel = new CategoryFoodModel(this);
        this.onCategoryFoodViewLisener = onCategoryFoodViewLisener;
    }

    public void getListCategoryFood(String json){
        categoryFoodModel.getListCategory(json);
    }

    @Override
    public void onGetListCategorySuccess(List<FoodCategory> foodCategories) {
        onCategoryFoodViewLisener.onGetListCategorySuccess(foodCategories);
    }

    @Override
    public void onGetListCategoryFailure() {
        onCategoryFoodViewLisener.onGetListCategoryFailure();
    }
}
