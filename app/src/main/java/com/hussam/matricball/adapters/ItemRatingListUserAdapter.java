package com.hussam.matricball.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import com.hussam.matricball.Blog.adapter.ItemBlogCommentReplyAdapter;
import com.hussam.matricball.R;
import com.hussam.matricball.helper.BlogCommentOnclicklinstener;
import com.hussam.matricball.modelsList.blogCommentsModel;
import com.hussam.matricball.utills.Network.RestService;
import com.hussam.matricball.utills.SettingsMain;
import com.hussam.matricball.utills.UrlController;

public class ItemRatingListUserAdapter extends RecyclerView.Adapter<ItemRatingListUserAdapter.CustomViewHolder> {

    public static List<blogCommentsModel> feedItemList;
    private Context mContext;
    private BlogCommentOnclicklinstener oNItemClickListener;
    RestService restService;
    SettingsMain settingsMain;


    public ItemRatingListUserAdapter(Context context, List<blogCommentsModel> feedItemList) {
        this.feedItemList = feedItemList;
        this.mContext = context;
    }

    @Override
    public ItemRatingListUserAdapter.CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_rating, null);

        return new ItemRatingListUserAdapter.CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemRatingListUserAdapter.CustomViewHolder customViewHolder, int i) {
        final blogCommentsModel feedItem = feedItemList.get(i);
        settingsMain = new SettingsMain(mContext);

        if (settingsMain.getAppOpen()) {
            restService = UrlController.createService(RestService.class);
        } else
            restService = UrlController.createService(RestService.class, settingsMain.getUserEmail(),
                    settingsMain.getUserPassword(), mContext);

        customViewHolder.name.setText(feedItemList.get(i).getName());
        customViewHolder.message.setText(feedItemList.get(i).getMessage());
        customViewHolder.date.setText(feedItemList.get(i).getDate());
        customViewHolder.reply.setText(feedItemList.get(i).getReply());
        customViewHolder.ratingBar.setRating(Float.parseFloat(feedItem.getRating()));


//        if (feedItemList != null && feedItemList.get(i).getSetReaaction()) {
//            customViewHolder.reaction.setVisibility(View.GONE);
//            customViewHolder.gifImageViewlike.setVisibility(View.GONE);
////            customViewHolder.gifImageViewHeart.setVisibility(View.GONE);
////            customViewHolder.gifImageViewWoW.setVisibility(View.GONE);
////            customViewHolder.gifImageViewAngry.setVisibility(View.GONE)
//        }
//        if (feedItemList != null&&feedItemList.get(i).equals("RatingFragment")) {
////            customViewHolder.reaction.setVisibility(feedItem.getSetReaaction() ? View.GONE : View.GONE);
//            customViewHolder.gifImageViewlike.setVisibility(View.GONE);
//            customViewHolder.gifImageViewHeart.setVisibility(View.GONE);
//            customViewHolder.gifImageViewWoW.setVisibility(View.GONE);
//            customViewHolder.gifImageViewAngry.setVisibility(View.GONE);
//        }

        customViewHolder.reply.setVisibility(feedItem.isCanReply() ? View.VISIBLE : View.GONE);

        if (!TextUtils.isEmpty(feedItem.getImage())) {
            Picasso.with(mContext).load(feedItem.getImage())
                    .error(R.drawable.placeholder)
                    .placeholder(R.drawable.placeholder)
                    .into(customViewHolder.imageView);
        }

//View.OnClickListener giflistener=new View.OnClickListener() {
// @Override
// public void onClick(View view) {
//
// }
//}
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                oNItemClickListener.onItemClick(feedItem);
            }
        };

        customViewHolder.reply.setOnClickListener(listener);

        if (feedItemList.get(i).getHasReplyList()) {
            ItemBlogCommentReplyAdapter itemSendRecMesageAdapter
                    = new ItemBlogCommentReplyAdapter(mContext, feedItemList.get(i).getListitemsiner());
            if (feedItemList.get(i).getListitemsiner().size() > 0)
                customViewHolder.recyclerView.setAdapter(itemSendRecMesageAdapter);
        }
    }

    @Override
    public int getItemCount() {
        return (null != feedItemList ? feedItemList.size() : 0);
    }

    public BlogCommentOnclicklinstener getOnItemClickListener() {
        return oNItemClickListener;
    }

    public void setOnItemClickListener(BlogCommentOnclicklinstener onItemClickListener) {
        this.oNItemClickListener = onItemClickListener;
    }


    class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name, message, date, reply, liketext, hearttext, wowtext, angryText;
        RatingBar ratingBar;
        RecyclerView recyclerView;
        RelativeLayout reaction;

        CustomViewHolder(View view) {
            super(view);
            reaction = view.findViewById(R.id.reactions);

            liketext = view.findViewById(R.id.textviewLike);
            hearttext = view.findViewById(R.id.textViewHeart);
            wowtext = view.findViewById(R.id.textViewWOW);
            angryText = view.findViewById(R.id.textViewAngry);
            this.imageView = view.findViewById(R.id.image_view);
            this.name = view.findViewById(R.id.text_viewName);
            this.ratingBar = view.findViewById(R.id.ratingBar);
            this.message = view.findViewById(R.id.prices);
            this.date = view.findViewById(R.id.loginTime);
            this.reply = view.findViewById(R.id.verified);
            LayerDrawable stars = (LayerDrawable) this.ratingBar.getProgressDrawable();
            stars.getDrawable(2).setColorFilter(Color.parseColor("#ffcc00"), PorterDuff.Mode.SRC_ATOP);

            this.recyclerView = view.findViewById(R.id.sublist);

            LinearLayoutManager MyLayoutManager = new LinearLayoutManager(mContext);
            MyLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(MyLayoutManager);
            recyclerView.setNestedScrollingEnabled(false);

        }
    }
}