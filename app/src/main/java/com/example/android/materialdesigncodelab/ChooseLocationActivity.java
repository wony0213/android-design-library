package com.example.android.materialdesigncodelab;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.materialdesigncodelab.database.UserConfigUtil;

import java.util.ArrayList;
import java.util.List;

public class ChooseLocationActivity extends AppCompatActivity {
    // Toolbar
    Toolbar mToolbar;

    // 所有位置信息的GridView，以及对应的Adapter
    ArrayAdapter<String> mAllCountrysAdapter;
    GridView mCurrentLocationGridView;
    TextView mEmptyView;

    // 当前位置的GridView，以及对应的Adapter。为了布局一致，虽然当前位置只有一个也用了GridView布局。
    ArrayAdapter<String> mCurrentLocationAdapter;
    GridView mAllCountyrsGridView;
    List<String> mCurrentLocationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_location);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        // 如果没有加这句，界面上就没有返回按钮
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // 当前位置GridView界面设置
        mCurrentLocationGridView = findViewById(R.id.grid_current_location);
        mCurrentLocationList = new ArrayList<>(1);
        String currentLocation = UserConfigUtil.getCurrentLocation(ChooseLocationActivity.this);
        mCurrentLocationList.add(currentLocation);
        mCurrentLocationAdapter = new ArrayAdapter<String>(ChooseLocationActivity.this, R.layout.item_gridview, R.id.text_country_name, mCurrentLocationList);
        mCurrentLocationGridView.setAdapter(mCurrentLocationAdapter);
        mCurrentLocationGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ChooseLocationActivity.this, parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
            }
        });

        /*
         * 所有位置GridView界面设置
         */
        mAllCountyrsGridView = (GridView) findViewById(R.id.grid_all_countrys);
        mEmptyView = (TextView) findViewById(R.id.empty_view);

        mAllCountrysAdapter = new ArrayAdapter<String>(ChooseLocationActivity.this, R.layout.item_gridview, R.id.text_country_name, getResources().getStringArray(R.array.location_array));
        mAllCountyrsGridView.setAdapter(mAllCountrysAdapter);

        mAllCountyrsGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // 更新UserConfig表
                String currentLocation = parent.getItemAtPosition(position).toString();
                UserConfigUtil.updateCurrentLocation(ChooseLocationActivity.this, currentLocation);

                // 更新mCurrentLocationGridView显示
                mCurrentLocationList.set(0, currentLocation);
                mCurrentLocationAdapter.notifyDataSetChanged();

                finish();
            }
        });

        mAllCountyrsGridView.setEmptyView(mEmptyView);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_choose_location_toolbar, menu);

        // 位置检索逻辑
        MenuItem mSearch = menu.findItem(R.id.action_search);

        SearchView mSearchView = (SearchView) mSearch.getActionView();
        mSearchView.setQueryHint("Search");
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAllCountrysAdapter.getFilter().filter(newText);
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}
