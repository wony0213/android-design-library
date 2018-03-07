package com.example.android.materialdesigncodelab.model;

import android.support.annotation.Nullable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by wony on 2018/3/6.
 */

@Entity
public class OrganizationInfo {

    @Id
    private Long id;

    @NotNull
    private String countryName;

    @Index(unique = true)
    private String organizationName;

    @NotNull
    private String organizationDes;

    @NotNull
    private String phoneNumber;

    @NotNull
    private Boolean isFavorate;

    @Generated(hash = 1222935522)
    public OrganizationInfo(Long id, @NotNull String countryName,
            String organizationName, @NotNull String organizationDes,
            @NotNull String phoneNumber, @NotNull Boolean isFavorate) {
        this.id = id;
        this.countryName = countryName;
        this.organizationName = organizationName;
        this.organizationDes = organizationDes;
        this.phoneNumber = phoneNumber;
        this.isFavorate = isFavorate;
    }

    @Generated(hash = 1067398893)
    public OrganizationInfo() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountryName() {
        return this.countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getOrganizationName() {
        return this.organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getOrganizationDes() {
        return this.organizationDes;
    }

    public void setOrganizationDes(String organizationDes) {
        this.organizationDes = organizationDes;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Boolean getIsFavorate() {
        return this.isFavorate;
    }

    public void setIsFavorate(Boolean isFavorate) {
        this.isFavorate = isFavorate;
    }
}
