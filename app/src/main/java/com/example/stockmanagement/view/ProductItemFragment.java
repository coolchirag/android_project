package com.example.stockmanagement.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.stockmanagement.AddItemScreen;
import com.example.stockmanagement.MainActivity;
import com.example.stockmanagement.R;
import com.example.stockmanagement.constant.ItemType;
import com.example.stockmanagement.dao.DBHelper;
import com.example.stockmanagement.dao.ItemRepository;
import com.example.stockmanagement.dto.ItemDto;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductItemFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ListView productListView;

    private Context context;
    private ItemRepository itemRepo;
    private ItemListAdapter adapter;
    private List<ItemDto> itemDtos;

    public ProductItemFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProductItemFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductItemFragment newInstance(String param1, String param2) {
        ProductItemFragment fragment = new ProductItemFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = getActivity();
        this.itemRepo = new ItemRepository(new DBHelper(this.context));
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_item, container, false);
        this.productListView = view.findViewById(R.id.product_item_list);
        SearchView searchview = view.findViewById(R.id.product_item_search);
        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener(){

            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                itemDtos.clear();
                itemDtos.addAll(itemRepo.getSearchItemsByType(ItemType.PRODUCT, newText));
                adapter.notifyDataSetChanged();
                return true;
            }
        });
        initItemList();
        return view;
    }

    private void initItemList() {
        this.itemDtos = itemRepo.getAllItemsByType(ItemType.PRODUCT);
        this.adapter = new ItemListAdapter(this.context, R.layout.item_list_layout, this.itemDtos);
        this.productListView.setAdapter(adapter);
        //adapter.getFilter().filter();


    }
}