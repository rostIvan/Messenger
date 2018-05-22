package trickyquestion.messenger.robolectric.support;

import org.junit.runners.model.InitializationError;
import org.robolectric.RobolectricTestRunner;

import trickyquestion.messenger.BuildConfig;

public class SupportRobolectricGradleTestRunner extends RobolectricTestRunner {

    public SupportRobolectricGradleTestRunner(Class<?> testClass) throws InitializationError {
        super(testClass);
        String buildVariant = (BuildConfig.FLAVOR.isEmpty() ? "" : BuildConfig.FLAVOR+ "/") + BuildConfig.BUILD_TYPE;
        String intermediatesPath = BuildConfig.class.getResource("").toString().replace("file:", "");
        intermediatesPath = intermediatesPath.substring(0, intermediatesPath.indexOf("/classes"));

        System.setProperty("android.package", BuildConfig.APPLICATION_ID);
        System.setProperty("android.manifest", intermediatesPath + "/manifests/full/" + buildVariant + "/AndroidManifest.xml");
        System.setProperty("android.resources", intermediatesPath + "/res/" + buildVariant);
        System.setProperty("android.assets", intermediatesPath + "/assets/" + buildVariant);
    }

}
