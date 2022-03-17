package com.microsoft.maui.glide;

import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.microsoft.maui.ImageLoaderCallback;

public class MauiCustomTarget extends CustomTarget<Drawable>
{
    private final ImageLoaderCallback callback;
    private final RequestManager requestManager;


    public MauiCustomTarget(ImageLoaderCallback callback, RequestManager requestManager)
    {
        this.callback = callback;
        this.requestManager = requestManager;
    }

    @Override
    public void onLoadFailed(@Nullable Drawable errorDrawable) {
        super.onLoadFailed(errorDrawable);

        callback.onComplete(false, errorDrawable, new Runnable() {
            @Override
            public void run() {
                requestManager.clear(MauiCustomTarget.this);
            }
        });
    }

    @Override
    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
        callback.onComplete(true, resource, new Runnable() {
            @Override
            public void run() {
                requestManager.clear(MauiCustomTarget.this);
            }
        });
    }

    @Override
    public void onLoadCleared(@Nullable Drawable placeholder) {

    }
}

