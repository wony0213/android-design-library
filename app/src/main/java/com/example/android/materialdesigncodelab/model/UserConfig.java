package com.example.android.materialdesigncodelab.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by wony on 2018/3/8.
 */

@Entity
public class UserConfig {

    @Id
    private Long id;

    @NotNull
    private String userName;

    @NotNull
    private String currentLocation;

    @NotNull
    private String language;

    @Generated(hash = 1576094114)
    public UserConfig(Long id, @NotNull String userName,
            @NotNull String currentLocation, @NotNull String language) {
        this.id = id;
        this.userName = userName;
        this.currentLocation = currentLocation;
        this.language = language;
    }

    @Generated(hash = 523434660)
    public UserConfig() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
