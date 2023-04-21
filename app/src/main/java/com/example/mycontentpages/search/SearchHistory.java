package com.example.mycontentpages.search;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SearchHistory {
    private static final String PREFS_NAME = "search_history";
    private static final String KEY_SEARCH_HISTORY = "search_history";
    private SharedPreferences mSharedPreferences;
    private Gson mGson;

    public SearchHistory(Context context) {
        mSharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        mGson = new Gson();
    }

    public List<String> getSearchHistory() {
        String searchHistoryJson = mSharedPreferences.getString(KEY_SEARCH_HISTORY, "");
        Type type = new TypeToken<List<String>>(){}.getType();
        return mGson.fromJson(searchHistoryJson, type);
    }

    public void addSearchTerm(String searchTerm) {
        List<String> searchHistory = getSearchHistory();
        if (searchHistory == null) {
            searchHistory = new ArrayList<>();
        }
        if (!searchHistory.contains(searchTerm)) {
            searchHistory.add(searchTerm);
            String searchHistoryJson = mGson.toJson(searchHistory);
            mSharedPreferences.edit().putString(KEY_SEARCH_HISTORY, searchHistoryJson).apply();
        }
    }

    public void clearSearchHistory() {
        mSharedPreferences.edit().remove(KEY_SEARCH_HISTORY).apply();
    }
}