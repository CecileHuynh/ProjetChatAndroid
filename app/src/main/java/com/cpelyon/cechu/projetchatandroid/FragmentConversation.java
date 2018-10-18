package com.cpelyon.cechu.projetchatandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class FragmentConversation extends Fragment {
    View view;
    private Button button;
    public FragmentConversation(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.conversations_fragment,container,false);
        button=getView().findViewById(R.id.button);
        button.setOnClickListener((View.OnClickListener) this);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
  //      setContentView(R.layout.activity_home_page_user);
    }

    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), chatPage.class);
        startActivity(intent);
    }
}
