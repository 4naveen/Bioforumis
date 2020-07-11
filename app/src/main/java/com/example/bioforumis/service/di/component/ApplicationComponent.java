package com.example.bioforumis.service.di.component;

import android.content.Context;

import com.example.bioforumis.service.data.remote.ApiService;
import com.example.bioforumis.service.di.module.ContextModule;
import com.example.bioforumis.service.di.module.RetrofitModule;
import com.example.bioforumis.service.di.qualifier.ApplicationContext;
import com.example.bioforumis.service.di.scopes.ApplicationScope;
import com.example.bioforumis.view.MyApplication;

import dagger.Component;

@ApplicationScope
@Component(modules = {ContextModule.class, RetrofitModule.class})
public interface ApplicationComponent {

    public ApiService getApiService();

    @ApplicationContext
    public Context getContext();

    public void injectApplication(MyApplication myApplication);
}
