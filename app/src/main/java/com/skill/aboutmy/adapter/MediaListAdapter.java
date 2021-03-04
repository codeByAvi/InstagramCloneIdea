package com.skill.aboutmy.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.skill.aboutmy.R;
import com.skill.aboutmy.callbacks.OnMediaItemCallbackListener;
import com.skill.aboutmy.databinding.ListLoadBinding;
import com.skill.aboutmy.databinding.MediaListItemBinding;
import com.skill.aboutmy.models.MediaItem;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MediaListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "AboutMy.MediaListAdapter";
    public static final String MEDIA_ITEM_TRANSITION_KEY = "mediaImageTransition";
    private static final int MEDIA_ITEM = 1;
    private static final int LOADING_ITEM = 2;

    private List<MediaItem> mediaItemList;
    private boolean isLoadingAdded;
    private OnMediaItemCallbackListener onMediaItemCallbackListener;
    private SimpleDateFormat fromDateFormat,toDateFormat;



    public MediaListAdapter(OnMediaItemCallbackListener onMediaItemCallbackListener) {
        this.mediaItemList = new ArrayList<>();
        this.onMediaItemCallbackListener = onMediaItemCallbackListener;
        fromDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        toDateFormat =  new SimpleDateFormat("MMM dd, yyyy");
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case LOADING_ITEM:
                return new LoadingItemViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_load, parent, false));
            case MEDIA_ITEM:
            default:
                return new MediaViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.media_list_item, parent, false),onMediaItemCallbackListener);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case MEDIA_ITEM:
                MediaViewHolder mediaViewHolder = (MediaViewHolder) holder;
                MediaItem mediaItem = mediaItemList.get(position);
                mediaViewHolder.itemViewBinding.setMediaItem(mediaItem);
                mediaViewHolder.itemViewBinding.setFromDateFormat(fromDateFormat);
                mediaViewHolder.itemViewBinding.setToDateFormat(toDateFormat);
                mediaViewHolder.itemViewBinding.imgForMediaThumbnail.setTransitionName(MEDIA_ITEM_TRANSITION_KEY+position);
                mediaViewHolder.itemViewBinding.executePendingBindings();

                break;
            case LOADING_ITEM:
                break;
        }

    }

    @Override
    public int getItemCount() {
        return mediaItemList.size();
    }

    public void addMediaItemList(List<MediaItem> mediaItemList) {
        Log.d(TAG,"addMediaItemList"+mediaItemList.size());
        this.mediaItemList.addAll(mediaItemList);
        if (this.mediaItemList.size() == mediaItemList.size())
            notifyDataSetChanged();
        else {
            Log.d(TAG,this.mediaItemList.size() +" "+ (this.mediaItemList.size() - mediaItemList.size()) +" "+mediaItemList.size());
            notifyItemRangeInserted(this.mediaItemList.size() - mediaItemList.size(), mediaItemList.size());
        }
    }

    public List<MediaItem> getMediaItemList() {
        return mediaItemList;
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    public void addLoadingFooter() {
        isLoadingAdded = true;
        mediaItemList.add(new MediaItem());
        notifyItemInserted(mediaItemList.size()-1);
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = mediaItemList.size() - 1;
        MediaItem result = getItem(position);

        if (result != null && result.id == null) {
            mediaItemList.remove(position);
            notifyItemRemoved(position);
        }
    }

    private MediaItem getItem(int position) {
        if (!mediaItemList.isEmpty()) return mediaItemList.get(position);
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == mediaItemList.size() - 1 && isLoadingAdded) ? LOADING_ITEM : MEDIA_ITEM;
    }
}

 class MediaViewHolder extends RecyclerView.ViewHolder {

    MediaListItemBinding itemViewBinding;
    public MediaViewHolder(@NonNull MediaListItemBinding itemViewBinding, OnMediaItemCallbackListener onMediaItemCallbackListener) {
        super(itemViewBinding.getRoot());
        this.itemViewBinding = itemViewBinding;
        this.itemViewBinding.imgForItemMenu.setOnClickListener(v -> {
            onMediaItemCallbackListener.onItemMenuClick(getAdapterPosition());
        });
        this.itemViewBinding.imgForMediaThumbnail.setOnClickListener(v->{
            onMediaItemCallbackListener.onMediaClicked(getAdapterPosition(),this.itemViewBinding.imgForMediaThumbnail);
        });
    }
}

class LoadingItemViewHolder extends RecyclerView.ViewHolder {

    ListLoadBinding itemViewBinding;
    public LoadingItemViewHolder(@NonNull ListLoadBinding itemViewBinding) {
        super(itemViewBinding.getRoot());
        this.itemViewBinding = itemViewBinding;
    }
}
