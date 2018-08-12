package com.caramelheaven.lennach.ui.main.navigation.presenter;

import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.caramelheaven.lennach.datasource.database.entity.helpers.PostFileThread;
import com.caramelheaven.lennach.ui.base.BaseView;

import java.util.List;

public interface BoardNavigationView extends BaseView {

    @StateStrategyType(value = SingleStateStrategy.class)
    void showItems(List<PostFileThread> postsInThreads);
}
