package trickyquestion.messenger.java.p2p_protocol;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ServiceTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeoutException;

import trickyquestion.messenger.network.NetworkState;
import trickyquestion.messenger.p2p_protocol.P2PProtocolService;

import static org.junit.Assert.assertTrue;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class P2PNetworkWifiChecking {
    @Rule
    public ServiceTestRule P2PServiceTest;

    @Test
    public void StartingService() {
        try {
            P2PServiceTest = new ServiceTestRule();
            P2PProtocolService.LocalBinder bind = (P2PProtocolService.LocalBinder)P2PServiceTest.bindService
                    (new Intent(InstrumentationRegistry.getTargetContext(),
                            P2PProtocolService.class));
            bind.start();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void WifiStateEnable() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        try {
            P2PServiceTest = new ServiceTestRule();
            P2PProtocolService.LocalBinder bind = (P2PProtocolService.LocalBinder)P2PServiceTest.bindService
                    (new Intent(InstrumentationRegistry.getTargetContext(),
                            P2PProtocolService.class));
            bind.start();
            WifiManager wifiManager = (WifiManager)appContext.getSystemService(Context.WIFI_SERVICE);
            wifiManager.setWifiEnabled(true);
            while(trickyquestion.messenger.network.Network.getCurrentNetworkState()!= NetworkState.ACTIVE) Thread.sleep(2500);
            Thread.sleep(2500);
            assertTrue("Wifi enable and list not empty", !bind.getUsers().isEmpty());
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void WifiStateDisable() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        try {
            P2PServiceTest = new ServiceTestRule();
            P2PProtocolService.LocalBinder bind = (P2PProtocolService.LocalBinder)P2PServiceTest.bindService
                    (new Intent(InstrumentationRegistry.getTargetContext(),
                            P2PProtocolService.class));
            bind.start();
            Thread.sleep(10000);
            WifiManager wifiManager = (WifiManager)appContext.getSystemService(Context.WIFI_SERVICE);
            wifiManager.setWifiEnabled(false);
            Thread.sleep(10000);
            assertTrue("Wifi enable and list empty", bind.getUsers().isEmpty());
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void IsServiceStarted(){
        Context appContext = InstrumentationRegistry.getTargetContext();
        try {
            P2PServiceTest = new ServiceTestRule();
            P2PProtocolService.LocalBinder bind = (P2PProtocolService.LocalBinder)P2PServiceTest.bindService
                    (new Intent(InstrumentationRegistry.getTargetContext(),
                            P2PProtocolService.class));
            assertTrue("Service cant be started before call start", !bind.isStarted());
            bind.start();
            assertTrue("Service must be started after call start", bind.isStarted());
            bind.stop();
            assertTrue("Service cant be started after call stop", !bind.isStarted());
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
