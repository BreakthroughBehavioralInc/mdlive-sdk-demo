# MDLive SDK
MDLIVE SDK Demo is an application intended to be a reference to show case MDLIVE SDK features, capabilities and integration.

## Integration
This section describes the required `*.gradle` files configurations for obtaining the MDLive SDK. 

Open your root project `build.gradle` file and update it with MDLive and AppBoy bintray dependencies.  

```groovy
buildscript {
    ...
    dependencies {
        ...
        classpath 'com.google.gms:google-services:3.1.0'
        ...
    }
}
...
allprojects {
    repositories {
        ...
        //MDLive
        maven {
            url 'https://breakthroughbehavioralinc.bintray.com/maven/'
        }
        //AppBoy
        maven {
            url "http://appboy.github.io/appboy-android-sdk/sdk"
        }
    }
    project.ext {
        // MDLive SDK library AAR
        MDL_android_sdk = "com.mdlive.mdla:android-sdk:4.0.4"
    }
}
```

**Note:** `MDL_android_sdk = "com.mdlive.mdla:android-sdk:4.0.4"` is used to define the version of the SDK to use, in this case we are using `4.0.4`. You will need to update this version for future features or bug fixes come with next versions.

Once root file is well configured, we need to configure our application `build.gradle` adding the respective dependencies:

 ```groovy
 android {
     ...
     defaultConfig {
         ...
         multiDexEnabled true
     }
     ...
     // https://developer.android.com/studio/build/configure-apk-splits.html
     splits {
         abi {
             enable true
             universalApk true
         }
     }
 }
 ```
 
 **Note:** `splits.abi.*` is used for allowing run the app in architectures different than `arm` like the `x86` emulators, you could remove this block but you will need to test your app in real devices with `arm` architectures.
 
## Configuration
This section describes the required code configuration to before using the MDLive SDK.

If you are not using a custom Application class for your app you will need to create one, let's say you named it `DemoApplication` and should look like the one in this repo.

Your `DemoApplication` class must inherit from `MultiDexApplication`.

```java
public class DemoApplication extends MultiDexApplication {
    ...
}
```

You will need to configure the MDLive SDK in the `onCreate` method by overriding it like this:
```java
@Override
public void onCreate() {
    super.onCreate();
    // Create a new configuration object.
    MdlConfiguration configuration = new MdlConfiguration();
    // You can override the default analytics engine by your own engine entending from AnalyticsEngine.
    configuration.addAnalyticsEngine(new ConsoleAnalyticsEngine());
    // Here you can configure specific things like:
    configuration.getApplicationConstantsBuilder()
            .debug(BuildConfig.DEBUG) // This is for letting the SDK know if the app is runnning in Debug mode or not.
            .isSessionTimeoutEnabled(false) // This parameter is true by default, so after 5 minutes the user screen will be looked and be asked by MDLive credentials. We could disable this for SDK usages.
            .isSSOsession(true) // Since SDK uses the single sign on method to access the dashboard we must set this parameter to true.
            .defaultFirebaseFilename("mdlive__firebase_defaults.json"); // Here we pass the google play service json file name that must be in the assests directory.
    // Initialize the MDLive SDK and make it ready for using it.
    MdlBootstrap.start(this, configuration);
}
```
**Note:** You must request `mdlive__firebase_defaults.json` file to MDLive SDK team by sending an email asking for this configuration file, otherwise you won't be able to use the SDK.

| Name  | Email | Rol |
| ------------- | ------------- | ------------- |
| Blake Schwendiman  | bschwendiman@mdlive.com  | Director of MDLIVE Mobile  |
| Emilio Negron  | enegron@mdlive.com  | Senior Android Developer  |
| Terry Turner  | tturner@mdlive.com  | Senior Android Developer  |
| Eduardo Pool  | epool@nearsoft.com  | Senior Android Developer  |

## MDLive Sign In usage
In order to access the MDLive dashboard you must to create a `MdlSSODetail` object with the required MDLive user information to access the MDLive dashboard.
```java
Calendar birthdateCalendar = GregorianCalendar.getInstance();
birthdateCalendar.set(1917, 0, 1);

MdlSSODetail ssoDetail = MdlSSODetail.builder()
        .ou("BCBSTEST")
        .firstName("Test")
        .lastName("Patient")
        .gender(FwfSSOGender.MALE)
        .birthdate(birthdateCalendar.getTime())
        .subscriberId("TEST12345")
        .memberId("")
        .phone("555-555-5555")
        .email("ahadida@mdlive.com")
        .address1("address1")
        .address2("address2")
        .city("Sunrise")
        .state(FwfState.FL)
        .zipCode("33303")
        .relationship(FwfSSORelationship.SELF)
        .build();
```

Once `MdlSSODetail` is correctly created we can proceed to try a sign in with this info with:
```java
MdlApplicationSupport.getAuthenticationCenter()
        .singleSignOn(ssoDetail)
        .map(new Func1<MdlUserSession, Intent>() {
            @Override
            public Intent call(MdlUserSession mdlUserSession) {
                return MdlApplicationSupport.getIntentFactory().ssoDashboard(MainActivity.this);
            }
        })
        .observeOn(AndroidSchedulers.mainThread()) // Switches to main thread.
        .subscribe(
                new Action1<Intent>() {
                    @Override
                    public void call(Intent intent) {
                        startActivity(intent);
                        showProgressBar(false);
                    }
                },
                new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e(MainActivity.class.getSimpleName(), throwable.toString());
                        showProgressBar(false);
                    }
                }
        );
```

## Run This Demo SDK
You can just clone this repo with:
```bash
git clone https://github.com/BreakthroughBehavioralInc/mdlive-sdk-demo.git
```
import it with Android Studio once cloned and just run the demosdk application.

## Styling
After running the DemoSDK application you will see a similar dashboard like this with default header and text:

<img src="/images/Screenshot_1504298299.png?raw=true" width="320">

You can update/change them overriding the next style attributes by creating your own theme extending from `rodeo__SSODashboardActivityStyle`. You will be able to:

- Dashboard action bar icon or title text. (Icon has priority over text)
- Update the text, text color, text appearance, background color of the header.
- Define a header layout to be used as header.
- Update the text, text color, text appearance, background color of the footer.
- Define a footer layout to be used as footer.


```xml
<style name="demo__SSODashboardActivityStyle" parent="rodeo__SSODashboardActivityStyle">
    <!-- mdl__sso_dashboard_title_icon has higher priority than mdl__sso_dashboard_title_text, this must be a drawable resource -->
    <item name="mdl__sso_dashboard_title_icon">@drawable/mdlive_logo_small</item>
    <!-- Text to use as title, this must be a string resource -->
    <item name="mdl__sso_dashboard_title_text">@string/mdl__app_name</item>

    <!-- Layout to be used as header, if we change this layout the next header styles will be ignored -->
    <item name="mdl__sso_dashboard_header_layout">@layout/mdl__sso_dashboard_header</item>
    <!-- Color to use as header background -->
    <item name="mdl__sso_dashboard_header_background_color">@color/fwf__near_white</item>
    <!-- Text to use as header, this must be a string resource -->
    <item name="mdl__sso_dashboard_header_text">@string/mdl__sso_dashboard_subtitle_text</item>
    <!-- Text appearance for the header text, this must be a style resource -->
    <item name="mdl__sso_dashboard_header_text_appearance">
        @style/Base.TextAppearance.AppCompat.Title
    </item>

    <!-- Layout to be used as footer, if we change this layout the next footer styles will be ignored -->
    <item name="mdl__sso_dashboard_footer_layout">@layout/mdl__sso_dashboard_footer</item>
    <!-- Color to use as footer background -->
    <item name="mdl__sso_dashboard_footer_background_color">@color/fwf__near_white</item>
    <!-- Text to use as footer, this must be a string resource -->
    <item name="mdl__sso_dashboard_footer_text">@string/mdl__sso_dashboard_footer_text</item>
    <!-- Text appearance for the footer text, this must be a style resource -->
    <item name="mdl__sso_dashboard_footer_text_appearance">
        @style/Base.TextAppearance.AppCompat.Medium
    </item>
</style>
```

And finally overriding/applying this new theme to `MdlSSODashboardActivity` in your `AndroidManifest.xml` file.
```xml
<activity
    android:name="com.mdlive.mdlcore.activity.ssodashboard.MdlSSODashboardActivity"
    android:theme="@style/demo__SSODashboardActivityStyle"
    tools:replace="android:theme" />
```

Then your changes in `demo__SSODashboardActivityStyle` will be reflected in the dashboard.