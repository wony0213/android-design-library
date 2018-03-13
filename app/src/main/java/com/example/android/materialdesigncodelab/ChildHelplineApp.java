package com.example.android.materialdesigncodelab;

import android.app.Application;

import com.example.android.materialdesigncodelab.config.ChildHelplineConfig;
import com.example.android.materialdesigncodelab.model.DaoMaster;
import com.example.android.materialdesigncodelab.model.DaoSession;
import com.example.android.materialdesigncodelab.model.OrganizationInfo;
import com.example.android.materialdesigncodelab.model.OrganizationInfoDao;
import com.example.android.materialdesigncodelab.model.UserConfig;
import com.example.android.materialdesigncodelab.model.UserConfigDao;

import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.internal.DaoConfig;


/**
 * Created by wony on 2018/3/6.
 */

public class ChildHelplineApp extends Application {
    /**
     * A flag to show how easily you can switch from standard SQLite to the encrypted SQLCipher.
     */
    public static final boolean ENCRYPTED = false;

    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, ENCRYPTED ? "notes-db-encrypted" : "notes-db");
        Database db = ENCRYPTED ? helper.getEncryptedWritableDb("super-secret") : helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();

        initDataset(daoSession);
    }

    private void initDataset(DaoSession daoSession) {
        // 初始化UserConfig表,以及OrganizationInfo表
        UserConfigDao userConfigDao = daoSession.getUserConfigDao();
        OrganizationInfoDao organizationInfoDao = daoSession.getOrganizationInfoDao();
        if(userConfigDao.queryBuilder().where(UserConfigDao.Properties.UserName.eq(ChildHelplineConfig.DEFAULT_USER_NAME)).build().list().size() == 0) {
            // UserConfig表初始化
            UserConfig userConfig = new UserConfig(null, ChildHelplineConfig.DEFAULT_USER_NAME, getResources().getString(R.string.china), "English");
            userConfigDao.insert(userConfig);

            // OrganizationInfo表初始化
            OrganizationInfo organizationInfo = new OrganizationInfo(null, "China", "移动", "中国移动", "10086", false);
            organizationInfoDao.insert(organizationInfo);
            organizationInfo = new OrganizationInfo(null, "China", "联通", "中国联通", "10010", true);
            organizationInfoDao.insert(organizationInfo);


            organizationInfo = new OrganizationInfo(null, "United States", "2nd Floor", "for New Jersey's youth", "10086", false);
            organizationInfoDao.insert(organizationInfo);
            organizationInfo = new OrganizationInfo(null, "United States", "Boys Town National Hotline", "Boys Town National Hotline", "10086", false);
            organizationInfoDao.insert(organizationInfo);
            organizationInfo = new OrganizationInfo(null, "United States", "California Youth Crisis Line", "for California‘s youth", "10086", false);
            organizationInfoDao.insert(organizationInfo);
            organizationInfo = new OrganizationInfo(null, "United States", "Child Abuse Hotline", "precention and treatment of child abuse", "10086", false);
            organizationInfoDao.insert(organizationInfo);
            organizationInfo = new OrganizationInfo(null, "United States", "Crisis Text Line", "any type of crisis", "10086", false);
            organizationInfoDao.insert(organizationInfo);
            organizationInfo = new OrganizationInfo(null, "United States", "Muslim American Youth Supportline - MAYS", "Muslim American Youth Supportline", "10086", false);
            organizationInfoDao.insert(organizationInfo);
            organizationInfo = new OrganizationInfo(null, "United States", "National Runaway Safeline", "Runaway helpline", "10086", false);
            organizationInfoDao.insert(organizationInfo);
            organizationInfo = new OrganizationInfo(null, "United States", "Polaris", "Human Trafficking", "10086", false);
            organizationInfoDao.insert(organizationInfo);
            organizationInfo = new OrganizationInfo(null, "United States", "Stop It Now!", "sexual abuse", "10086", false);
            organizationInfoDao.insert(organizationInfo);

            organizationInfo = new OrganizationInfo(null, "Poland", "Poland", "Poland", "10086", false);
            organizationInfoDao.insert(organizationInfo);
            organizationInfo = new OrganizationInfo(null, "Luxembourg", "Luxembourg", "Luxembourg", "10086", false);
            organizationInfoDao.insert(organizationInfo);



        }
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
