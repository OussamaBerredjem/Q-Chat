package com.example.myapplication.Decoration;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class Decoration extends RecyclerView.ItemDecoration {

    private final int mPadding;

    public Decoration(int padding) {
        mPadding = padding;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.top = mPadding;
        outRect.bottom = mPadding;
        outRect.left = mPadding;
        outRect.right = mPadding;
    }
}
