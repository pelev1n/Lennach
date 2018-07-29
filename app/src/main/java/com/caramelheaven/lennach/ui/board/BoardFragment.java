package com.caramelheaven.lennach.ui.board;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.caramelheaven.lennach.R;
import com.caramelheaven.lennach.datasource.database.entity.iThread;
import com.caramelheaven.lennach.ui.board.presenter.BoardPresenter;
import com.caramelheaven.lennach.ui.board.presenter.BoardView;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

/**
 * Created by CaramelHeaven on 27.07.2018
 */
public class BoardFragment extends MvpAppCompatFragment implements BoardView {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    private RecyclerView.LayoutManager layoutManager;
    private BoardAdapter adapter;

    @InjectPresenter
    BoardPresenter presenter;

    public static BoardFragment newInstance() {

        Bundle args = new Bundle();

        BoardFragment fragment = new BoardFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_board, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        progressBar = view.findViewById(R.id.progressBar);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new BoardAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);
        Timber.d("savedInstanceState: " + savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        recyclerView = null;
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void hideRefreshing() {

    }

    @Override
    public void showRetryView(String cause) {

    }

    @Override
    public void hideRetryView() {

    }

    @Override
    public void showError() {

    }

    @Override
    public void refteshItems(List<iThread> iThreads) {

    }

    @Override
    public void showItems(List<iThread> iThreads) {
        Timber.d("ITAK: " + iThreads.size());
        for (iThread iThread : iThreads) {
            Timber.d("check: " + iThread.toString());
        }
        adapter.updateAdapter(iThreads);
    }
}