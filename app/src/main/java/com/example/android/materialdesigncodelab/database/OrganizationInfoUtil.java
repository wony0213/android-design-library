package com.example.android.materialdesigncodelab.database;

import android.content.Context;

import com.example.android.materialdesigncodelab.ChildHelplineApp;
import com.example.android.materialdesigncodelab.config.ChildHelplineConfig;
import com.example.android.materialdesigncodelab.model.DaoSession;
import com.example.android.materialdesigncodelab.model.OrganizationInfo;
import com.example.android.materialdesigncodelab.model.OrganizationInfoDao;
import com.example.android.materialdesigncodelab.model.UserConfigDao;

import java.util.List;

/**
 * Created by wony on 2018/3/8.
 */

public class OrganizationInfoUtil {
    public static List<OrganizationInfo> getOrganizationInfos(Context context, String countryName) {
        DaoSession daoSession = ((ChildHelplineApp)context.getApplicationContext()).getDaoSession();
        OrganizationInfoDao organizationInfoDao = daoSession.getOrganizationInfoDao();
        List<OrganizationInfo> organizationInfos = organizationInfoDao.queryBuilder().where(OrganizationInfoDao.Properties.CountryName.eq(countryName)).build().list();
        return organizationInfos;
    }
}
