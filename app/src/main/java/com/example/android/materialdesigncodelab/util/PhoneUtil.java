package com.example.android.materialdesigncodelab.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by wony on 2018/3/6.
 */

public class PhoneUtil {
    @SuppressLint("MissingPermission")
    public static void callNumber(Context context, String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + phoneNumber);
        intent.setData(data);
        context.startActivity(intent);
    }

    public static void dialNumber(Context context, String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNumber);
        intent.setData(data);
        context.startActivity(intent);

    }
}
