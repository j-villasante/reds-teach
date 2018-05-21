package com.berry.blue.reds_teach.settings;

import com.berry.blue.reds_teach.RedDB;
import com.berry.blue.reds_teach.fires.Category;
import com.berry.blue.reds_teach.fires.Constants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SettingsController {
    SettingsI.Fragment view;

    SettingsController(SettingsI.Fragment view) {
        this.view = view;
    }

    public void setConstantsValues() {
        RedDB.instance().getReference("constants").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Constants constants = dataSnapshot.getValue(Constants.class);
                view.onConstantDataObtained(constants);
                setCategories();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                view.showMessage(databaseError.getMessage());
            }
        });
    }
    
    private void setCategories() {
        RedDB.instance().getReference("categories").orderByValue()
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> categories = new ArrayList<>();
                List<Category> categoryList = new ArrayList<>();
                for (DataSnapshot categorySnap: dataSnapshot.getChildren()) {
                    String category = categorySnap.getValue(String.class);
                    if (category != null){
                        categories.add(category);
                        categoryList.add(new Category(category, categorySnap.getKey()));
                    }
                }
                view.onCategoriesDataObtained(categories, categoryList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                view.showMessage(databaseError.getMessage());
            }
        });
    }
}
