package com.songtaeyang.mobilestock;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment1 extends Fragment {
    Toolbar toolbar;
    Fragment1 fragment1;
    Fragment2 fragment2;
    Fragment3 fragment3;
    TabLayout tabs;

    public MainFragment1() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_main_fragment1, container, false);
        // Inflate the layout for this fragment

        // 툴바 설정
        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        // 프래그먼트(화면) 설정
        fragment1 = new Fragment1();
        fragment2 = new Fragment2();
        fragment3 = new Fragment3();
        ((AppCompatActivity) getActivity()).getSupportFragmentManager().beginTransaction().add(R.id.container, fragment1).commit();

        // 탭 설정 & 탭 선택 시 fragment 설정.
        tabs = (TabLayout) rootView.findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("오늘의 증시"));
        tabs.addTab(tabs.newTab().setText("전체 종목"));
        tabs.addTab(tabs.newTab().setText("관심 종목"));

        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition(); // 탭의 인덱스값을 받아옴.

                Fragment selected = null;
                if(position == 0) {
                    selected = fragment1;
                } else if (position == 1) {
                    selected = fragment2;
                } else if (position == 2) {
                    selected = fragment3;
                }
                // 탭 선택 시 container를 선택된 fragment로 교체해줌.
                ((AppCompatActivity) getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.container, selected).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return rootView;
    }

}
