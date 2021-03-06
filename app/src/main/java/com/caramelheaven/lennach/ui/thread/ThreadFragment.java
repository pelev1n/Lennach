package com.caramelheaven.lennach.ui.thread;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.caramelheaven.lennach.R;
import com.caramelheaven.lennach.datasource.model.Post;
import com.caramelheaven.lennach.ui.base.BaseFragment;
import com.caramelheaven.lennach.ui.thread.presenter.ThreadPresenter;
import com.caramelheaven.lennach.ui.thread.presenter.ThreadView;
import com.caramelheaven.lennach.utils.view.TopSheetBehavior;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class ThreadFragment extends MvpAppCompatFragment implements ThreadView, BaseFragment, SendMessageListener {

    private RecyclerView rvContaner;
    private ProgressBar progressBar;
    private TopSheetBehavior topSheetBehavior;
    private LinearLayout llContainer;
    private EditText etMessage;
    private CoordinatorLayout coordinatorLayout;
    private ImageButton btnSend, btnCatalog;
    private TextView tvCounter;
    private String threadNumber;

    private ThreadAdapter adapter;
    private StringBuilder cacheAnswer;

    public static ThreadFragment newInstance(String boardName, String idThread) {
        Bundle args = new Bundle();
        args.putString("BOARD_NAME", boardName);
        args.putString("THREAD_ID", idThread);
        ThreadFragment fragment = new ThreadFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @InjectPresenter
    ThreadPresenter presenter;

    @ProvidePresenter
    ThreadPresenter provideThreadPresenter() {
        threadNumber = getArguments().getString("THREAD_ID");
        return new ThreadPresenter(getArguments()
                .getString("BOARD_NAME"), threadNumber);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_thread, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        threadNumber = getArguments().getString("THREAD_ID");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvContaner = view.findViewById(R.id.recyclerView);
        progressBar = view.findViewById(R.id.progressBar);
        llContainer = view.findViewById(R.id.ll_container);
        topSheetBehavior = TopSheetBehavior.from(llContainer);
        etMessage = llContainer.findViewById(R.id.et_message);
        coordinatorLayout = view.findViewById(R.id.coordinatorLayout);
        topSheetBehavior.setState(TopSheetBehavior.STATE_HIDDEN);
        btnSend = view.findViewById(R.id.btn_send);
        btnCatalog = view.findViewById(R.id.btn_catalog);
        tvCounter = view.findViewById(R.id.tv_counter);

        cacheAnswer = new StringBuilder();


        provideRecyclerAndAdapter();
        provideScrollBehavior();
        provideTopSheet();
        provideEtMessageListeners();
        provideButtons();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        rvContaner = null;
        progressBar = null;
        topSheetBehavior = null;
        llContainer = null;
        etMessage = null;
        btnSend = null;
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
    public void refteshItems(List<Post> posts) {

    }

    @Override
    public void showItems(List<Post> posts) {
        adapter.updateAdapter(posts);
    }

    @Override
    public void provideRecyclerAndAdapter() {
        rvContaner.setHasFixedSize(true);
        rvContaner.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        adapter = new ThreadAdapter(new ArrayList<>());
        rvContaner.setAdapter(adapter);

        adapter.setImageOnItemClickListener((view, position) -> {
//            SliderImageDialogFragment dialogFragment = SliderImageDialogFragment.newInstance(position, mappingFiles(adapter.getItems()));
//            dialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog_FullScreen);
//            dialogFragment.show(getActivity()
//                    .getSupportFragmentManager()
//                    .beginTransaction(), null);
        });

        adapter.setItemTouchCallback(post -> {
            etMessage.requestFocus();
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
            topSheetBehavior.setState(TopSheetBehavior.STATE_EXPANDED);
            String answerToPost = "";
            if (cacheAnswer.length() != 0) {
                cacheAnswer.setLength(0);
                cacheAnswer.append(etMessage.getText().toString()).append("\n").append(answerToPost);
            } else {
                cacheAnswer.append(answerToPost);
            }
            etMessage.setText("");
            etMessage.setText(cacheAnswer.toString());
            etMessage.setSelection(cacheAnswer.toString().length());
        });
    }

    private void provideScrollBehavior() {
        rvContaner.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                InputMethodManager imm = (InputMethodManager)
                        getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(etMessage.getWindowToken(), 0);

                switch (newState) {
                    case RecyclerView.SCROLL_STATE_DRAGGING:
                        if (topSheetBehavior.getState() != TopSheetBehavior.STATE_HIDDEN) {
                            topSheetBehavior.setState(TopSheetBehavior.STATE_COLLAPSED);
                        }
                        break;
                    case RecyclerView.SCROLL_STATE_IDLE:
                        Timber.d("юзер отпустил пальчик и лист не скролица");
                        break;
                    case RecyclerView.SCROLL_STATE_SETTLING:
                        Timber.d("SCROLL_STATE_SETTING, eto konez lista");
                        break;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        coordinatorLayout.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                Rect r = new Rect();
                //r will be populated with the coordinates of your view that area still visible.
                coordinatorLayout.getWindowVisibleDisplayFrame(r);
                int screenHeight = coordinatorLayout.getRootView().getHeight();
                int heightDiff = coordinatorLayout.getRootView().getHeight() - r.top;
                int bottomDiff = coordinatorLayout.getRootView().getHeight() - r.bottom;
                Timber.d("bottomDif: " + bottomDiff);
                Timber.d("screenHeight: " + screenHeight);
                Timber.d("heightDiff: " + heightDiff);

                if (heightDiff > screenHeight * 0.15) {
                    Timber.d("OKAY");
                } else {
                    Timber.d("screenHeight: " + screenHeight);
                    Timber.d("heightDiff: " + heightDiff);
                    topSheetBehavior.setState(TopSheetBehavior.STATE_COLLAPSED);
                    /*if (topSheetBehavior.getState() != TopSheetBehavior.STATE_HIDDEN) {
                        Timber.d("topSheetBehavior> " + topSheetBehavior.getState());
                        topSheetBehavior.setState(TopSheetBehavior.STATE_COLLAPSED);
                    }*/
                }
            }
        });
    }

    private void provideTopSheet() {
        topSheetBehavior.setTopSheetCallback(new TopSheetBehavior.TopSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                Timber.d("newState: " + newState);
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset, @Nullable Boolean isOpening) {
                Timber.d("calling");
                Timber.d("slideOffset: " + slideOffset);
            }
        });

    }

    private void provideEtMessageListeners() {
        etMessage.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Timber.d("hasFocus: " + hasFocus);
            }
        });

        etMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //   tvCounter.setText(String.valueOf(start) + "/2000");
                Timber.d("beforeTextChanged: " + s.toString() + " start: " + start + " count: " + count + " after: " + after);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tvCounter.setText(s.toString().length() + "/2000");
            }

            @Override
            public void afterTextChanged(Editable s) {
                Timber.d("afterTextChanged: " + s.toString());
            }
        });
    }

    @Override
    public void updateThread() {
        etMessage.setText("");
        etMessage.clearFocus();
        topSheetBehavior.setState(TopSheetBehavior.STATE_HIDDEN);
        presenter.loadPosts();
    }

    private void provideButtons() {
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // hide keyboard
                InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(getActivity().INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);

                String msg = etMessage.getText().toString();

//                CaptchaDialogFragment captchaDialog = CaptchaDialogFragment.newInstance(threadNumber, msg);
//                captchaDialog.setTargetFragment(ThreadFragment.this, 1337);
//                captchaDialog.show(getFragmentManager(), "dialog");

            }
        });

        btnCatalog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "get Catalog", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
