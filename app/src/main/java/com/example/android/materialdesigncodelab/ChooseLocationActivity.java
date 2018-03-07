package com.example.android.materialdesigncodelab;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ChooseLocationActivity extends AppCompatActivity {
    Toolbar mToolbar;
    ArrayAdapter<String> mAllCountrysAdapter;
    GridView mCurrentLocationGridView;
    ArrayAdapter<String> mCurrentLocationAdapter;
    GridView mAllCountyrsGridView;
    TextView mEmptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_location);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*
         * 所有位置GridView界面设置
         */
        mAllCountyrsGridView = (GridView) findViewById(R.id.grid_all_countrys);
        mEmptyView = (TextView) findViewById(R.id.empty_view);

        mAllCountrysAdapter = new ArrayAdapter<String>(ChooseLocationActivity.this, R.layout.item_gridview, R.id.text_country_name, getResources().getStringArray(R.array.location_array));
        mAllCountyrsGridView.setAdapter(mAllCountrysAdapter);

        mAllCountyrsGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(ChooseLocationActivity.this, adapterView.getItemAtPosition(i).toString(), Toast.LENGTH_SHORT).show();
            }
        });

        mAllCountyrsGridView.setEmptyView(mEmptyView);

        /*
         * 当前位置GridView界面设置
         */
        mCurrentLocationGridView = findViewById(R.id.grid_current_location);
        List<String> currentLocation = new ArrayList<>();
        currentLocation.add("China");
        mCurrentLocationAdapter = new ArrayAdapter<String>(ChooseLocationActivity.this, R.layout.item_gridview, R.id.text_country_name, currentLocation);
        mCurrentLocationGridView.setAdapter(mCurrentLocationAdapter);
        mCurrentLocationGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ChooseLocationActivity.this, parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_choose_location_toolbar, menu);

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
