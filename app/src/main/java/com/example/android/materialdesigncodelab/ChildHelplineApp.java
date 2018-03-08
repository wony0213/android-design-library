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
            OrganizationInfo organizationInfo1 = new OrganizationInfo(null, "China", "移动", "中国移动", "10086", false);
            organizationInfoDao.insert(organizationInfo1);
            OrganizationInfo organizationInfo2 = new OrganizationInfo(null, "China", "联通", "中国联通", "10010", true);
            organizationInfoDao.insert(organizationInfo2);
            OrganizationInfo organizationInfo3 = new OrganizationInfo(null, "United States", "警察", "美国警察机构", "119", false);
            organizationInfoDao.insert(organizationInfo3);
        }
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
