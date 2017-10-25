package com.berry.blue.reds_teach.results;

import com.berry.blue.reds_teach.fires.Game;
import com.berry.blue.reds_teach.fires.Guess;

import java.util.List;

public interface Results {
    interface Fragment {
        void onDataObtained(List<Game> games);
        void showMessage(String errorMessage);
        void showDetail(Game game, List<Guess> guesses);
    }

    interface AdapterView{
        void getDetail(String key);
    }
}
