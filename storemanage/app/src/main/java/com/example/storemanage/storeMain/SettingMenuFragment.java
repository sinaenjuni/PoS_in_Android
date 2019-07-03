package com.example.storemanage.storeMain;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.storemanage.R;
import com.example.storemanage.storeMain.settings.MenuSettingActivity;
import com.example.storemanage.storeMain.settings.StoreSettingActivity;
import com.example.storemanage.storeMain.settings.UserSettingActivity;

import java.util.ArrayList;
import java.util.List;


public class SettingMenuFragment extends Fragment {

    ListView listView;
    List<String> listItems;
    ArrayAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_setting, container, false);
        listItems = new ArrayList<>();

        listView = (ListView) view.findViewById(R.id.setting_fragment_listview);

        listItems.add("메뉴");
        listItems.add("매장");
        listItems.add("사용자");

        adapter = new ArrayAdapter(view.getContext(), android.R.layout.simple_list_item_1, listItems);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                       startActivity(new Intent(getActivity(), MenuSettingActivity.class));
                        break;


                    case 1:
                        startActivity(new Intent(getActivity(), StoreSettingActivity.class));
                        break;


                    case 2:
                        startActivity(new Intent(getActivity(), UserSettingActivity.class));
                        break;
                }

            }
        });

        return view;
    }


}