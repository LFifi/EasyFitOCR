package com.example.testcloudfirebase.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.testcloudfirebase.Model.Product;
import com.example.testcloudfirebase.TabFragments.AddProductFragment;
import com.example.testcloudfirebase.TabFragments.SearchProductFragment;

import java.util.ArrayList;

public class PagerAdapter extends FragmentPagerAdapter {
    private int numOfTabs;
    private ArrayList<Product> products;
    String idDay,idMeal,title;

    public PagerAdapter(FragmentManager fm, int numOfTabs, ArrayList<Product> products,String idDay,String idMeal,String title){
        super(fm);
        this.numOfTabs = numOfTabs;
        this.products = products;
        this.idDay = idDay;
        this.idMeal = idMeal;
        this.title = title;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch ( position){
            case 0:
                return new SearchProductFragment(products,idDay,idMeal,title);
            case 1:
                return new AddProductFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
