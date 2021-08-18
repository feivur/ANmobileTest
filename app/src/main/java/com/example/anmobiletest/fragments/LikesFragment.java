package com.example.anmobiletest.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.anmobiletest.R;

public class LikesFragment extends Fragment {

    //I в задании сказано, что интерфейс должен быть _похож_ на инстаграм,
    // но это же не значит, что надо добавлять неработающие элементы управления на событиях и в тулбаре, и даже целые пустые экраны
    // Нефункциональный мусор убрать

    public LikesFragment() {
    }

    public static LikesFragment newInstance() {
        return new LikesFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_likes, container, false);
    }
}