package com.hussam.matricball.helper;

import android.view.View;

import com.hussam.matricball.modelsList.myAdsModel;

public interface MyAdsOnclicklinstener {

    void onItemClick(myAdsModel item);

    void delViewOnClick(View v, int position);

    void editViewOnClick(View v, int position);

}
