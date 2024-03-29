package com.example.kitchenpal.uploadFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.kitchenpal.R;
import com.example.kitchenpal.objects.Recipe;

import java.util.ArrayList;

public class AddIngredientFragment extends Fragment implements View.OnClickListener {

    LinearLayout layoutIngre;
    Button buttonAddIngre, buttonNext;
    UploadFragment parentFrag;

    ArrayList<String> ingreList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_add_ingredient, container, false);

        layoutIngre = root.findViewById(R.id.linearLayout_list_ingredients);
        buttonAddIngre = root.findViewById(R.id.buttonAddIngre);
        buttonNext = root.findViewById(R.id.btnGoToAddStep);
        parentFrag = (UploadFragment) getParentFragment();

        buttonAddIngre.setOnClickListener(this);
        buttonNext.setOnClickListener(this);
        addIngreView();
        return root;
    }

    protected ArrayList<String> getIngreList() {
        return ingreList;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonAddIngre:
                addIngreView();
                break;
            case R.id.btnGoToAddStep:
                if (checkIfValidAndRead()) {
                    parentFrag.setIngreFrag(this);
                    if (parentFrag.getStepFrag() == null) {
                        parentFrag.replaceFragment(new AddStepFragment());
                    } else {
                        parentFrag.replaceFragment(parentFrag.getStepFrag());
                    }

                }
                break;
        }
    }

    protected boolean checkIfValidAndRead() {
        ingreList.clear();
        boolean result = true;

        for (int i = 0; i < layoutIngre.getChildCount(); i++) {
            View v = layoutIngre.getChildAt(i);
            EditText etIngre = v.findViewById(R.id.etIngredient);
            String ingreName = etIngre.getText().toString();

            if (!ingreName.equals("")) {
                ingreList.add(ingreName);
            } else {
                result = false;
                break;
            }
        }

        if (ingreList.size() == 0) {
            result = false;
            Toast.makeText(getActivity(), "Add ingredient(s) first!", Toast.LENGTH_SHORT).show();
        }


        return result;
    }



    private void addIngreView() {
        View v = getLayoutInflater().inflate(R.layout.row_add_ingre,null, false);
        EditText etIngre = v.findViewById(R.id.etIngredient);
        ImageView imgRemoveIngre = v.findViewById(R.id.imageRemoveIngre);

        imgRemoveIngre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeIngreView(v);
            }
        });
        layoutIngre.addView(v);
    }



    private void removeIngreView(View view) {
        layoutIngre.removeView(view);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.ingreFrameLayout, fragment);
        ft.commit();
    }
}