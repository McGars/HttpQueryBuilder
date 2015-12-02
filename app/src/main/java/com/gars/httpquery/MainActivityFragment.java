package com.gars.httpquery;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gars.querybuilder.BaseQuery;
import com.gars.querybuilder.StatusResult;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Query query = new Query(getActivity());

        query.init("http://url")
             .getResult(new BaseQuery.OnQuerySuccessListener<MyResult>() {
                 @Override
                 public void onQuerySuccess(MyResult res) {

                 }
             });

    }
}
