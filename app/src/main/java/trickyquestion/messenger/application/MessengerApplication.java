package trickyquestion.messenger.application;


import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import trickyquestion.messenger.p2p_protocol.P2ProtocolService;
import trickyquestion.messenger.p2p_protocol.ProtocolClientSide;
import trickyquestion.messenger.util.Constants;

public class MessengerApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initRealm();
        initStetho();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Realm.getDefaultInstance().close();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Realm.getDefaultInstance().close();
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
