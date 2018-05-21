package com.berry.blue.reds_teach.settings;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.berry.blue.reds_teach.R;
import com.berry.blue.reds_teach.RedDB;
import com.berry.blue.reds_teach.fires.Category;
import com.berry.blue.reds_teach.fires.Constants;
import com.berry.blue.reds_teach.interfaces.activities.Main;

import java.util.List;

public class SettingsFragment extends Fragment implements SettingsI.Fragment {
    EditText eteNumber;
    Spinner spinCategories;
    Button butAddCategory;
    Button butDeleteCategory;
    Button butSaveCount;

    Main view;
    SettingsController controller;

    Constants constants;
    List<String> categories;
    List<Category> categoryList;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.settings_view_frag, container, false);
        eteNumber = rootView.findViewById(R.id.ete_number_tests);
        spinCategories = rootView.findViewById(R.id.spin_categories);
        butAddCategory = rootView.findViewById(R.id.but_add_category);
        butDeleteCategory = rootView.findViewById(R.id.but_delete_category);
        butSaveCount = rootView.findViewById(R.id.but_save_count);

        butAddCategory.setOnClickListener((view) -> this.onAddCategoryClick());
        butDeleteCategory.setOnClickListener((view) -> this.onDeleteCategoriesClick());
        butSaveCount.setOnClickListener((view) -> this.saveCount());
        spinCategories.setOnItemSelectedListener(this.getOnItemSelectedListener());

        controller = new SettingsController(this);
        controller.setConstantsValues();
        return rootView;
    }

    @Override
    public void onConstantDataObtained(Constants constants) {
        this.constants = constants;
        eteNumber.setEnabled(true);
        eteNumber.setText(String.valueOf(constants.tries));
    }

    @Override
    public void onCategoriesDataObtained(List<String> categories, List<Category> categoryList) {
        this.categories = categories;
        this.categoryList = categoryList;
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, categories);
        spinCategories.setAdapter(adapter);
        spinCategories.setSelection(categories.indexOf(constants.category));
    }

    @Override
    public void showMessage(String message) {
        this.view.showMessage(message);
    }

    public SettingsFragment setView(Main view) {
        this.view = view;
        return this;
    }

    private AdapterView.OnItemSelectedListener getOnItemSelectedListener() {
        return new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                RedDB.instance().getReference("constants/category").setValue(categories.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                view.showMessage("On nothing selected was executed");
            }
        };
    }

    private void saveCount() {
        RedDB.instance().getReference("constants/tries").setValue(Integer.parseInt(eteNumber.getText().toString()));
    }

    private void onAddCategoryClick (){
        DialogFragment dialog = new AddCategoryDialog();
        view.showDialog(dialog);
    }

    private void onDeleteCategoriesClick() {
        DeleteCategoryDialog dialog = new DeleteCategoryDialog();
        dialog.initialize(this.categoryList);
        view.showDialog(dialog);
    }
}
