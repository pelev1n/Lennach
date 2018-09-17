package com.caramelheaven.lennach.ui.thread.presenter;

import com.arellomobile.mvp.MvpPresenter;
import com.caramelheaven.lennach.Lennach;
import com.caramelheaven.lennach.datasource.network.ApiService;
import com.caramelheaven.lennach.ui.thread.CaptchaDialogFragment;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

public class MessagePresenter extends MvpPresenter<CaptchaDialogView> {
    private CompositeDisposable disposable;
    private CaptchaDialogFragment view;

    @Inject
    ApiService apiService;

    public MessagePresenter(CaptchaDialogFragment view) {
        disposable = new CompositeDisposable();
        this.view = view;
        Lennach.getComponent().injectMessagePresenter(this);
    }

    public void getCaptchaId(String boardNumber, String threadNumber) {
        disposable.add(apiService.getCaptcha("2chaptcha", boardNumber, threadNumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(captcha -> {
                    view.setCaptchaId(captcha.getId());
                }));
    }

    public void postMessage(Map<String, RequestBody> options) {
        disposable.add(apiService.sendPostInThread(options)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(postInThread -> {
                    System.out.println(postInThread.getStatus());
                    if (postInThread.getStatus() != null) {
                        view.correctCaptcha();
                    } else {
                        switch (postInThread.getError()) {
                            case -4:
                                view.errorMessage("Постинг запрещен");
                                break;
                            case -5:
                                view.errorMessage("Кача неликвидна");
                                break;

                            case -20:
                                view.errorMessage("Вы ничего не запостили");
                                break;
                            case -8:
                                view.errorMessage("Вы постите слишком быстро.");
                                break;
                        }
                    }
                }));
    }


}
