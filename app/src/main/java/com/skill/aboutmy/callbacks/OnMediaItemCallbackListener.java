package com.skill.aboutmy.callbacks;

import androidx.appcompat.widget.AppCompatImageView;

import com.skill.aboutmy.models.MediaItem;
import com.skill.aboutmy.models.MediaList;

public interface OnMediaItemCallbackListener {

    void onItemMenuClick(int position);

    void onMediaClicked(int position, AppCompatImageView imageView);
}
