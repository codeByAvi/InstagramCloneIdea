package com.skill.aboutmy.utils;


import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/**
 * A custom scroll listener for RecyclerView that listens to items currently displayed items on scroll and notifies if
 * recyclerview reached to the end of the list data.
 *
 * This scroll listener supports all 3 types of LayoutManagers of ReclerView
 */
public abstract class PageScrollListener extends RecyclerView.OnScrollListener {

    RecyclerView.LayoutManager layoutManager;
    public boolean isOnFirstPage = false;

    public PageScrollListener(RecyclerView.LayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();
        int firstVisibleItemPosition = 0;
        if (layoutManager instanceof StaggeredGridLayoutManager)
        {
            int[] firstVisibleItems =  ((StaggeredGridLayoutManager)layoutManager).findFirstVisibleItemPositions(null);
            firstVisibleItemPosition = firstVisibleItems[0];
        } else if (layoutManager instanceof GridLayoutManager ) {
            firstVisibleItemPosition =  ((GridLayoutManager)layoutManager).findFirstVisibleItemPosition();
        } else if (layoutManager instanceof LinearLayoutManager ) {
            firstVisibleItemPosition =  ((LinearLayoutManager)layoutManager).findFirstVisibleItemPosition();
        }
//        int pastVisibleItems =0;
//        if(firstVisibleItems != null && firstVisibleItems.length > 0) {
//            pastVisibleItems = firstVisibleItems[0];
//        }


        if (!isLoading() && !isLastPage()) {
            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                    && firstVisibleItemPosition >= 0
                    /*&& totalItemCount >= getTotalPageCount()*/) {
                loadNextPage();
            }
        }
        isOnFirstPage = firstVisibleItemPosition == 0;

    }

    protected abstract void loadNextPage();

    public abstract boolean isLastPage();

    public abstract boolean isLoading();

}