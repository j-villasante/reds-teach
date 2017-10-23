package com.berry.blue.reds_teach.results;

import com.berry.blue.reds_teach.fires.Game;

import java.util.List;

public interface Results {
    interface Fragment {
        void onDataObtained(List<Game> games);
        void showMessage(String errorMessage);
        void showDetail(String key);
    }

    interface AdapterView{
        void getDetail(String key);
    }
}
