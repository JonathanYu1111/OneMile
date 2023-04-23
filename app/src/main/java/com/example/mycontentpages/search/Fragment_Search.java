package com.example.mycontentpages.search;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.mycontentpages.MainActivity;
import com.example.mycontentpages.R;
import com.example.mycontentpages.attractionInfo.Attraction;
import com.example.mycontentpages.attractionInfo.AttractionDetailsActivity;
import com.example.mycontentpages.home.Fragment_Home;

import java.util.ArrayList;
import java.util.List;


public class Fragment_Search extends Fragment {

//    View rootView;
//    List<String> recentSearch=new ArrayList<>();
//
//    List<Attraction> searchResult=new ArrayList<>();
//
//    private EditText mSearchEditText;
//    private Button mSearchButton;
//    private SearchHistory mSearchHistory;
//
//    private String[] mStrs = {"aaa", "bbb", "ccc", "airsaid"};
//    private SearchView mSearchView;
//    private ListView mListView;
//
//    public Fragment_Search() {
//        // Required empty public constructor
//    }
//
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
////        setContentView(R.layout.activity_main);
////        mSearchView = (SearchView) findViewById(R.id.searchView);
////        mListView = (ListView) findViewById(R.id.listView);
////        mListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mStrs));
////        mListView.setTextFilterEnabled(true);
//
//
//
//
//    }
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        if (rootView == null) {
//            rootView = inflater.inflate(R.layout.fragment_search, container, false);
//        }
//
//        mSearchEditText = rootView.findViewById(R.id.search_edittext);
//        mSearchButton = rootView.findViewById(R.id.search_button);
//
//        initView();
//        return rootView;
////        mSearchButton.setOnClickListener(new View.OnClickListener() {
////            @Override
//////            public void onClick(View view) {
//////                Intent intent = null;
//////               intent = new Intent(Fragment_Search.this, MainActivity.class);
//////                startActivity(intent);
//////            }
////        });
//
//    }
//
//    private void initView() {
//
//    }
//
//    private void showSearchList(List<String> searchList) {
//        RecyclerView recyclerView = getView().findViewById(R.id.recycler_view);
//        SearchAdapter adapter = new SearchAdapter(searchList);
//        recyclerView.setAdapter(adapter);
//    }


}