package com.hussam.matricball.helper;

import android.view.View;

import com.hussam.matricball.modelsList.catSubCatlistModel;

public interface CatSubCatOnclicklinstener {
    void onItemClick(catSubCatlistModel item);

    void onItemTouch(catSubCatlistModel item);

    void addToFavClick(View v, String position);

}
