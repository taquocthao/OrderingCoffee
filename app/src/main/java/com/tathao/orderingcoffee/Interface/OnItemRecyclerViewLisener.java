package com.tathao.orderingcoffee.Interface;

import android.view.View;

public interface OnItemRecyclerViewLisener {
     void onItemClick(View view, int position);
     void onNumberPickerValueChange(int value, int position);
     void onImageButtonDelete(View view, int positon);
}
