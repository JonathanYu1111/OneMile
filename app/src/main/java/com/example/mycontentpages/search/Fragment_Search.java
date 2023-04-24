package com.example.mycontentpages.search;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.mycontentpages.MainActivity;
import com.example.mycontentpages.R;
import com.example.mycontentpages.attractionInfo.Attraction;
import com.example.mycontentpages.attractionInfo.AttractionDetailsActivity;
import com.example.mycontentpages.home.Fragment_Home;

import java.util.ArrayList;
import java.util.List;


public class Fragment_Search extends Fragment implements View.OnClickListener{

    View rootView;

    private EditText mSearchEditText;
    private Button mSearchButton;
    private SharedPreferences preferences;

    private ListView mHistoryListView;
    private ArrayAdapter<String> mHistoryAdapter;


    public Fragment_Search() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_search, container, false);
            mSearchEditText = rootView.findViewById(R.id.editText_Search);
            mSearchButton = rootView.findViewById(R.id.btn_search);
            mHistoryListView = rootView.findViewById(R.id.listView_history);
            mSearchButton.setOnClickListener(this);

            preferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            reload();

            // setup history list view
            String[] historyArray = getHistory();
            mHistoryAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, historyArray);
            mHistoryListView.setAdapter(mHistoryAdapter);

            // setup history list view click listener
            mHistoryListView.setOnItemClickListener((parent, view, position, id) -> {
                String searchContent = mHistoryAdapter.getItem(position);
                mSearchEditText.setText(searchContent);
                mHistoryListView.setVisibility(View.GONE);
            });

            // setup search box text change listener
            mSearchEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    // show/hide history list view based on search box content
                    if (s.length() > 0) {
                        mHistoryListView.setVisibility(View.VISIBLE);
                        // filter history based on search box content
                        mHistoryAdapter.getFilter().filter(s.toString());
                    } else {
                        mHistoryListView.setVisibility(View.GONE);
                    }
                }
                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        }
        return rootView;
    }

    private String[] getHistory() {
        String historyString = preferences.getString("history", "");
        return historyString.split(",");
    }

    private void reload(){
        String test = preferences.getString("search",null);
        if(test != null) {
            mSearchEditText.setText(test);
            // save search to history
            String historyString = preferences.getString("history", "");
            if (!historyString.contains(test)) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("history", historyString + test + ",");
                editor.apply();
                // update history list view
                String[] historyArray = getHistory();
                mHistoryAdapter.clear();
                mHistoryAdapter.addAll(historyArray);
                mHistoryAdapter.notifyDataSetChanged();
            }
        }
    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//
//    }

    @Override
    public void onClick(View view) {
        // 处理按钮点击事件的逻辑
        String search_content = mSearchEditText.getText().toString();

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("search",search_content);
        editor.apply();

        // save search to history
        String historyString = preferences.getString("history", "");
        if (!historyString.contains(search_content)) {
            editor.putString("history", historyString + search_content + ",");
            editor.apply();
            // update history list view
            String[] historyArray = getHistory();
            mHistoryAdapter.clear();
            mHistoryAdapter.addAll(historyArray);
            mHistoryAdapter.notifyDataSetChanged();
        }
    }

}