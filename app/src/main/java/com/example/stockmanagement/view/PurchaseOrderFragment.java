package com.example.stockmanagement.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.stockmanagement.AddOrderScreen;
import com.example.stockmanagement.R;
import com.example.stockmanagement.constant.OrderStatusEnum;
import com.example.stockmanagement.constant.OrderTypeEnum;
import com.example.stockmanagement.dao.DBHelper;
import com.example.stockmanagement.dao.OrderRepository;
import com.example.stockmanagement.dto.OrderDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PurchaseOrderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PurchaseOrderFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ListView listView;

    private OrderRepository orderRepo;
    private Context context;
    private Map<Integer, Integer> positionToOrderIdMap = new HashMap<>();

    public PurchaseOrderFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PurchaseOrderFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PurchaseOrderFragment newInstance(String param1, String param2) {
        PurchaseOrderFragment fragment = new PurchaseOrderFragment();
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
        orderRepo = new OrderRepository(new DBHelper(this.context));
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_purchase_order, container, false);
        this.listView = view.findViewById(R.id.purchase_order_list);
        loadList();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Integer orderId = positionToOrderIdMap.get(position);
                Intent editOrderIntent = new Intent(context, AddOrderScreen.class);
                editOrderIntent.putExtra("orderId", orderId);
                startActivity(editOrderIntent);
            }
        });
        return view;
    }

    private void loadList(){

        List<OrderDto> orderDtos = orderRepo.getOrderByType(OrderTypeEnum.PURCHASE);
        List<String> orders = new ArrayList<>(orderDtos.size());
        this.positionToOrderIdMap.clear();
        int index = 0;
        for(OrderDto orderDto : orderDtos) {
            orders.add(orderDto.getOrderCode()+"\n Ordered : "+orderDto.getOrderedQuantity());
            this.positionToOrderIdMap.put(index, orderDto.getId());
            index++;
        }
        this.listView.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.activity_list_item, android.R.id.text1, orders));
    }
}