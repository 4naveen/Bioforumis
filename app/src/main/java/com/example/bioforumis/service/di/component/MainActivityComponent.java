package com.example.bioforumis.service.di.component;

import android.content.Context;

import com.example.bioforumis.service.di.module.AdapterModule;
import com.example.bioforumis.service.di.qualifier.ActivityContext;
import com.example.bioforumis.service.di.scopes.ActivityScope;
import com.example.bioforumis.view.ui.MainActivity;

import dagger.Component;

@ActivityScope
@Component(modules = AdapterModule.class, dependencies = ApplicationComponent.class)
public interface MainActivityComponent {

    @ActivityContext
    Context getContext();


    void injectMainActivity(MainActivity mainActivity);
}
