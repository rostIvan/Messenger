package trickyquestion.messenger.application;


import android.app.Application;

import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MessengerApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initRealm();
        initStetho();
    }

    @Override
    public void onTerminate() {
        Realm.getDefaultInstance().close();
        super.onTerminate();
    }

    private void initRealm() {
        Realm.init(this);
        final RealmConfiguration config = new RealmConfiguration.Builder()
                .name("myrealm.realm")
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
    }

    private void initStetho() {
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                        .build());
    }

}
