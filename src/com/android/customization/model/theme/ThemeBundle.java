/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.android.customization.model.theme;

import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;

import com.android.customization.model.CustomizationOption;
import com.android.wallpaper.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Theme component available in the system as a "persona" bundle.
 * Note that in this context a Theme is not related to Android's Styles, but it's rather an
 * abstraction representing a series of overlays to be applied to the system.
 */
public class ThemeBundle implements CustomizationOption {

    private final String mTitle;
    private final PreviewInfo mPreviewInfo;

    private ThemeBundle(String title, PreviewInfo previewInfo) {
        mTitle = title;
        mPreviewInfo = previewInfo;
    }

    @Override
    public String getTitle() {
        return mTitle;
    }

    @Override
    public void bindThumbnailTile(View view) {
        ((ImageView) view.findViewById(R.id.theme_option_color)).setImageTintList(
                ColorStateList.valueOf(mPreviewInfo.colorAccentLight));
        ((TextView) view.findViewById(R.id.theme_option_font)).setTypeface(
                mPreviewInfo.headlineFontFamily);
    }

    @Override
    public boolean isCurrentlySet() {
        return false;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.theme_option;
    }

    public PreviewInfo getPreviewInfo() {
        return mPreviewInfo;
    }

    public static class PreviewInfo {
        public final Typeface bodyFontFamily;
        public final Typeface headlineFontFamily;
        @ColorInt public final int colorAccentLight;
        @ColorInt public final int colorAccentDark;
        public final List<Drawable> icons;
        public final String shapePath;
        @DrawableRes public final int wallpaperDrawableRes;

        public PreviewInfo(Typeface bodyFontFamily, Typeface headlineFontFamily,
                int colorAccentLight,
                int colorAccentDark, List<Drawable> icons, String shapePath,
                int wallpaperDrawableRes) {
            this.bodyFontFamily = bodyFontFamily;
            this.headlineFontFamily = headlineFontFamily;
            this.colorAccentLight = colorAccentLight;
            this.colorAccentDark = colorAccentDark;
            this.icons = icons;
            this.shapePath = shapePath;
            this.wallpaperDrawableRes = wallpaperDrawableRes;
        }
    }

    public static class Builder {
        private String mTitle;
        private Typeface mBodyFontFamily;
        private Typeface mHeadlineFontFamily;
        @ColorInt private int mColorAccentLight;
        @ColorInt private int mColorAccentDark;
        private List<Drawable> mIcons = new ArrayList<>();
        private String mShapePath;
        @DrawableRes private int mWallpaperDrawableResId;

        private String mFontOverlayPackage;
        private String mColorsPackage;
        private List<String> mIconPackages = new ArrayList<>();
        private String mShapePackage;

        public ThemeBundle build() {
            return new ThemeBundle(mTitle,
                    new PreviewInfo(mBodyFontFamily, mHeadlineFontFamily, mColorAccentLight,
                            mColorAccentDark, mIcons, mShapePath, mWallpaperDrawableResId));
        }

        public Builder setTitle(String title) {
            mTitle = title;
            return this;
        }

        public Builder setFontOverlayPackage(String packageName) {
            mFontOverlayPackage = packageName;
            return this;
        }

        public Builder setBodyFontFamily(@Nullable Typeface bodyFontFamily) {
            mBodyFontFamily = bodyFontFamily;
            return this;
        }

        public Builder setHeadlineFontFamily(@Nullable Typeface headlineFontFamily) {
            mHeadlineFontFamily = headlineFontFamily;
            return this;
        }

        public Builder setColorPackage(String packageName) {
            mColorsPackage = packageName;
            return this;
        }

        public Builder setColorAccentLight(@ColorInt int colorAccentLight) {
            mColorAccentLight = colorAccentLight;
            return this;
        }

        public Builder setColorAccentDark(@ColorInt int colorAccentDark) {
            mColorAccentDark = colorAccentDark;
            return this;
        }
    }
}
