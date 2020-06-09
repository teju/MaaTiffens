package com.maa.tiffens;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.maa.tiffens.etc.GPSTracker;
import com.maa.tiffens.etc.Helper;
import com.maa.tiffens.ui.adapter.PlacesAutoCompleteAdapter;

public class SearchActivity extends AppCompatActivity implements PlacesAutoCompleteAdapter.ClickListener{

    private PlacesAutoCompleteAdapter mAutoCompleteAdapter;
    private RecyclerView recyclerView;
    private LinearLayout current_loc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);

        Places.initialize(this, getResources().getString(R.string.api_key));

        recyclerView = (RecyclerView) findViewById(R.id.places_recycler_view);
        current_loc = (LinearLayout) findViewById(R.id.current_loc);
        ((EditText) findViewById(R.id.place_search)).addTextChangedListener(filterTextWatcher);

        mAutoCompleteAdapter = new PlacesAutoCompleteAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAutoCompleteAdapter.setClickListener(this);
        recyclerView.setAdapter(mAutoCompleteAdapter);
        mAutoCompleteAdapter.notifyDataSetChanged();
        current_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GPSTracker gpsTracker = new GPSTracker(SearchActivity.this);
                if(gpsTracker.canGetLocation()) {
                    Intent data = new Intent();

//---set the data to pass back---
                    data.putExtra("Lat",gpsTracker.getLatitude());
                    data.putExtra("Lng",gpsTracker.getLongitude());

                    setResult(RESULT_OK, data);
//---close the activity---
                    finish();
                }
            }
        });
    }

    private TextWatcher filterTextWatcher = new TextWatcher() {
        public void afterTextChanged(Editable s) {
            if (!s.toString().equals("")) {
                mAutoCompleteAdapter.getFilter().filter(s.toString());
                if (recyclerView.getVisibility() == View.GONE) {recyclerView.setVisibility(View.VISIBLE);}
            } else {
                if (recyclerView.getVisibility() == View.VISIBLE) {recyclerView.setVisibility(View.GONE);}
            }
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
        public void onTextChanged(CharSequence s, int start, int before, int count) { }
    };

    @Override
    public void click(Place place) {
        Intent data = new Intent();

//---set the data to pass back---
       data.putExtra("Lat",place.getLatLng().latitude);
        data.putExtra("Lng",place.getLatLng().longitude);
        //data.setData(Uri.parse(place.toString()));
        data.putExtra("Address",place.getAddress());

        setResult(RESULT_OK, data);
//---close the activity---
        finish();
    }
}