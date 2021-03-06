/*
 * Copyright (C) 2015 The Android Open Source Project
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

package com.example.android.materialdesigncodelab;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.android.materialdesigncodelab.database.OrganizationInfoUtil;
import com.example.android.materialdesigncodelab.database.UserConfigUtil;
import com.example.android.materialdesigncodelab.model.OrganizationInfo;
import com.example.android.materialdesigncodelab.util.PhoneUtil;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * Provides UI for the view with List.
 */
@RuntimePermissions
public class ListContentFragment extends Fragment {

    private static final String TAG = "ListContentFragment";

    private List<OrganizationInfo> mOrganizationInfoList;
    private ContentAdapter mAdapter;

    private String currentLocation;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.e(TAG, "onCreate()");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e(TAG, "onCreateView()");
        View view = inflater.inflate(R.layout.fragment_list_content, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.my_recycler_view);
        initData();
        mAdapter = new ContentAdapter(recyclerView.getContext(), mOrganizationInfoList);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //设置点击事件监听
//        adapter.setOnItemClickListener(new ContentAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//
//            }
//
//            @Override
//            public void onItemLongClick(View view, int position) {
//
//            }
//        });

        //设置拨号监听
        mAdapter.setOnDialButtonClickListener(new ContentAdapter.OnDialButtonClickListener() {
            @Override
            public void dial(View view, int postion, String phoneNumber) {
                ListContentFragmentPermissionsDispatcher.callNumberWithPermissionCheck(ListContentFragment.this, phoneNumber);
            }
        });

        //Banner设置
        Banner banner = (Banner) view.findViewById(R.id.my_banner);
        List<Integer> imageResList = new ArrayList<>();
        imageResList.add(R.drawable.a);
        imageResList.add(R.drawable.b);
        imageResList.add(R.drawable.c);
        List<String> imageTitleList =  new ArrayList<>();
        imageTitleList.add("a");
        imageTitleList.add("b");
        imageTitleList.add("c");

        //设置内置样式，共有六种可以点入方法内逐一体验使用。
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        //设置图片加载器，图片加载器在下方
        banner.setImageLoader(new MyGlideImageLoader());
        //设置图片网址或地址的集合
        banner.setImages(imageResList);
        //设置轮播的动画效果，内含多种特效，可点入方法内查找后内逐一体验
        banner.setBannerAnimation(Transformer.Default);
        //设置轮播图的标题集合
        banner.setBannerTitles(imageTitleList);
        //设置轮播间隔时间
        banner.setDelayTime(3000);
        //设置是否为自动轮播，默认是“是”。
        banner.isAutoPlay(true);
        //设置指示器的位置，小点点，左中右。
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //启动
        banner.start();

        return view;
    }

    private void initData() {
        // 初始化数据
        currentLocation = UserConfigUtil.getCurrentLocation(getContext());
        mOrganizationInfoList = OrganizationInfoUtil.getOrganizationInfos(getContext(), currentLocation);
    }

    private class MyGlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load(path).into(imageView);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e(TAG, "onStart()");

        String newLocation = UserConfigUtil.getCurrentLocation(getContext());
        if (!currentLocation.equals(newLocation)) {
            currentLocation = newLocation;
            mOrganizationInfoList = OrganizationInfoUtil.getOrganizationInfos(getContext(), currentLocation);
            mAdapter.updateDataAndView(mOrganizationInfoList);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        //        public ImageView avator;
        public TextView name;
        public TextView description;

        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_list, parent, false));
            name = (TextView) itemView.findViewById(R.id.list_title);
            description = (TextView) itemView.findViewById(R.id.list_desc);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Context context = v.getContext();
//                    Intent intent = new Intent(context, DetailActivity.class);
//                    intent.putExtra(DetailActivity.EXTRA_POSITION, getAdapterPosition());
//                    context.startActivity(intent);
//                }
//            });
        }
    }

    /**
     * Adapter to display recycler view.
     */
    public static class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {

        private List<OrganizationInfo> mOrganizationInfos;

        // Set numbers of List in RecyclerView.
        private static final int LENGTH = 18;

        private ContentAdapter.OnItemClickListener onItemClickListener;
        private ContentAdapter.OnDialButtonClickListener onDialButtonClickListener;

        /**
         * 设置回调监听
         *
         * @param listener
         */
        public void setOnItemClickListener(ContentAdapter.OnItemClickListener listener) {
            this.onItemClickListener = listener;
        }

        public void setOnDialButtonClickListener(ContentAdapter.OnDialButtonClickListener dialListener) {
            this.onDialButtonClickListener = dialListener;
        }

        public ContentAdapter(Context context, List<OrganizationInfo> organizationInfos) {
            this.mOrganizationInfos = organizationInfos;
        }

        public void updateDataAndView(List<OrganizationInfo> organizationInfos) {
            this.mOrganizationInfos = organizationInfos;
            this.notifyDataSetChanged();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.name.setText(mOrganizationInfos.get(position).getOrganizationName());
            holder.description.setText(mOrganizationInfos.get(position).getOrganizationDes());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        int pos = holder.getLayoutPosition();
                        onItemClickListener.onItemClick(holder.itemView, pos);
                    }
                    if (onDialButtonClickListener != null) {
                        int pos = holder.getLayoutPosition();
                        onDialButtonClickListener.dial(holder.itemView, pos, (String) holder.description.getText());
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mOrganizationInfos.size();
        }

        public interface OnItemClickListener {
            void onItemClick(View view, int position);

            void onItemLongClick(View view, int position);
        }

        public interface OnDialButtonClickListener {
            void dial(View view, int postion, String phoneNumber);
        }
    }

    /*
     * 实现打电话功能，及动态权限处理
     */

    @SuppressLint("NeedOnRequestPermissionsResult")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        ListContentFragmentPermissionsDispatcher.onRequestPermissionsResult(ListContentFragment.this, requestCode, grantResults);
    }


    @NeedsPermission(Manifest.permission.CALL_PHONE)
    void callNumber(String phoneNumber) {
        // NOTE: Perform action that requires the permission.
        // If this is run by PermissionsDispatcher, the permission will have been granted
        PhoneUtil.callNumber(getContext(), phoneNumber);
    }

    @OnPermissionDenied(Manifest.permission.CALL_PHONE)
    void onCallPermissionDenied() {
        // NOTE: Deal with a denied permission, e.g. by showing specific UI
        // or disabling certain functionality
        Toast.makeText(getContext(), "用户拒绝拨打电话权限申请", Toast.LENGTH_SHORT).show();
    }

    @OnShowRationale(Manifest.permission.CALL_PHONE)
    void showRationaleForCallPermission(PermissionRequest request) {
        // NOTE: Show a rationale to explain why the permission is needed, e.g. with a dialog.
        // Call proceed() or cancel() on the provided PermissionRequest to continue or abort
        showRationaleDialog("申请电话权限", request);
    }

    @OnNeverAskAgain(Manifest.permission.CALL_PHONE)
    void onCallPermissionNeverAskAgain() {
        showGotoAppSettingsDialog("您已永久拒绝拨打电话权限，请到应用设置页面手动设置");
    }

    // 展示Rationale通用方法
    private void showRationaleDialog(String message, final PermissionRequest request) {
        new AlertDialog.Builder(getContext())
                .setPositiveButton("同意", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        request.proceed();
                    }
                })
                .setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        request.cancel();
                    }
                })
                .setCancelable(false)
                .setMessage(message)
                .show();
    }

    //跳转到设置页面通用方法
    private void showGotoAppSettingsDialog(String message) {
        new AlertDialog.Builder(getContext())
                .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + BuildConfig.APPLICATION_ID)));
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.i("Test", "用户取消");
                    }
                })
                .setCancelable(false)
                .setMessage(message)
                .show();
    }


}
