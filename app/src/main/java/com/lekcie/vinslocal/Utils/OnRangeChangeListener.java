package com.lekcie.vinslocal.Utils;

public interface OnRangeChangeListener {
    void onRangeChanged(RangeBar bar, int startIndex,int endIndex, boolean fromUser);
    void onStartTrackingTouch(RangeBar bar, int startIndex,int endIndex);
    void onStopTrackingTouch(RangeBar bar, int startIndex,int endIndex);
}