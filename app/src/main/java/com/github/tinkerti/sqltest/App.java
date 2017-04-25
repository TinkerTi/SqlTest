package com.github.tinkerti.sqltest;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.dumpapp.DumperPlugin;
import com.facebook.stetho.inspector.database.DefaultDatabaseConnectionProvider;
import com.facebook.stetho.inspector.protocol.ChromeDevtoolsDomain;
import com.github.tinkerti.sqltest.stetho.TMDatabaseDriver;
import com.github.tinkerti.sqltest.stetho.TMDatabaseFilesProvider;
import com.github.tinkerti.sqltest.stetho.TMDbFilesDumperPlugin;

/**
 * Created by tiankui on 4/25/17.
 */

public class App extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initialize(new Stetho.Initializer(this) {
            @Override
            protected Iterable<DumperPlugin> getDumperPlugins() {
                return new Stetho.DefaultDumperPluginsBuilder(App.this)
                        .provide(new TMDbFilesDumperPlugin(App.this, new TMDatabaseFilesProvider(App.this)))
                        .finish();
            }

            @Override
            protected Iterable<ChromeDevtoolsDomain> getInspectorModules() {
                Stetho.DefaultInspectorModulesBuilder defaultInspectorModulesBuilder = new Stetho.DefaultInspectorModulesBuilder(App.this);
                defaultInspectorModulesBuilder.provideDatabaseDriver(new TMDatabaseDriver(App.this, new TMDatabaseFilesProvider(App.this), new DefaultDatabaseConnectionProvider()));
                return defaultInspectorModulesBuilder.finish();
            }
        });
    }
}
