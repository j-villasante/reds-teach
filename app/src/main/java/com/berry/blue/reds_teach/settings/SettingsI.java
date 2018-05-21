package com.berry.blue.reds_teach.settings;

import com.berry.blue.reds_teach.fires.Category;
import com.berry.blue.reds_teach.fires.Constants;

import java.util.List;
import java.util.Map;

public interface SettingsI {
    interface Fragment{
        void onConstantDataObtained(Constants constants);
        void onCategoriesDataObtained(List<String> categories, List<Category> categoriesList);
        void showMessage(String message);
    }
}
