package com.example.android.materialdesigncodelab.database;

import android.app.Activity;
import android.content.Context;

import com.example.android.materialdesigncodelab.ChildHelplineApp;
import com.example.android.materialdesigncodelab.config.ChildHelplineConfig;
import com.example.android.materialdesigncodelab.model.DaoSession;
import com.example.android.materialdesigncodelab.model.UserConfig;
import com.example.android.materialdesigncodelab.model.UserConfigDao;

/**
 * Created by wony on 2018/3/8.
 */

public class UserConfigUtil {
    public static String getCurrentLocation(Context context) {
        DaoSession daoSession = ((ChildHelplineApp)context.getApplicationContext()).getDaoSession();
        UserConfigDao userConfigDao = daoSession.getUserConfigDao();
        String currentLocation = userConfigDao.queryBuilder().where(UserConfigDao.Properties.UserName.eq(ChildHelplineConfig.DEFAULT_USER_NAME)).build().unique().getCurrentLocation();
        return currentLocation;
    }
    public static void updateCurrentLocation(Context context, String newLocation) {
        DaoSession daoSession = ((ChildHelplineApp)context.getApplicationContext()).getDaoSession();
        UserConfigDao userConfigDao = daoSession.getUserConfigDao();
        UserConfig userConfig = userConfigDao.queryBuilder().where(UserConfigDao.Properties.UserName.eq(ChildHelplineConfig.DEFAULT_USER_NAME)).build().unique();
        userConfig.setCurrentLocation(newLocation);
        userConfigDao.update(userConfig);
    }
}
