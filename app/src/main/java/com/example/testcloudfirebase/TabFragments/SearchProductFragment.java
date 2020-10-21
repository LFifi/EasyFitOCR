package com.example.testcloudfirebase.TabFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.testcloudfirebase.Adapter.ProductsListRecycleViewAdapter;
import com.example.testcloudfirebase.AddingProductActivity;
import com.example.testcloudfirebase.Model.Product;
import com.example.testcloudfirebase.R;
import com.example.testcloudfirebase.ViewHolder.OnItemClickListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchProductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchProductFragment extends Fragment {

    ArrayList<Product> products;
    String idDay,idMeal,title;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    //
    private String mParam1;
    private String mParam2;

    public SearchProductFragment(ArrayList<Product> products, String idDay, String idMeal, String title) {
        // Required empty public constructor
        this.products = products;
        this.idDay = idDay;
        this.idMeal = idMeal;
        this.title = title;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchProductFragment newInstance(String param1, String param2, ArrayList<Product> products, String idDay, String idMeal, String title) {
        SearchProductFragment fragment = new SearchProductFragment(products,idDay,idMeal,title);
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_search, container, false);

        RecyclerView recyclerView =  view.findViewById(R.id.recycleViewListProd);
        ProductsListRecycleViewAdapter adapter= new ProductsListRecycleViewAdapter(products, new OnItemClickListener() {
            @Override
            public void onAddClick(String idDoc) {

            }

            @Override
            public void onDeleteClick(String idDay, String idDoc, String idProd) {

            }

            @Override
            public void OnAddProd(String idProduct) {
                try {
                    Intent k = new Intent(getActivity(), AddingProductActivity.class);
                    Bundle dataBundle = new Bundle();
                    dataBundle.putString("idDay",idDay);
                    dataBundle.putString("idMeal",idMeal);
                    dataBundle.putString("idProduct", idProduct);
                    dataBundle.putString("title",title);
                    Log.e("wysyłam:",idProduct);
                    k.putExtras(dataBundle);
                    startActivity(k);




                } catch(Exception e) {
                    e.printStackTrace();
                }

            }
        });
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);



        return  view;
    }
}