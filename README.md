<p align="center">
  <img src="/images/mdlive_always_there.jpg?raw=true" width="226">
  <br>
  <a href="https://www.mdlive.com/"><img src="https://travis-ci.org/lord/slate.svg?branch=master" alt="Build Status"></a>
</p>

<p align="center">
<font color=#158AE0 size='5'>
MDLIVE will be the world's highest quality, largest provider of virtual health management and care, recognized for world class consumer and clinician experience.
</font>
</p>

1- MDLIVE Android SAV SDK Demo
----------------------------
Our MDLIVE Android SAV SDK Demo is a fully integrated application. 
It is shared as a reference to show case the MDLIVE SDK features, capabilities and integration.


2- Run This Android SAV SDK Demo
-----------------------------

1. Fork this repo: https://github.com/BreakthroughBehavioralInc/mdlive-sdk-demo.
2. Clone your own fork repo to your local environment.
3. Import the code with Android Studio.
4. add your GitHub credentials into "credentials" block, in priject gradle (credentials provided by us):
```
 credentials {
                username = System.getenv('GITHUB_USER')
                password = System.getenv('GITHUB_PERSONAL_ACCESS_TOKEN')
            }
``` 
5. Build and run the Demo application with Android Studio.


3- SAV SDK Styling
-----------------
After running the Demo application, you will see a similar dashboard like the following, which displays a default header and text:

<img src="/images/sdk_dashboard.png?raw=true" width="320">

You can make changes to specific Dashboard attributes by overriding the corresponding style attributes, with your own customized theme, extending from `rodeo__SSODashboardActivityStyle`. You will be able to update:

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

If successfully configured, your changes in `demo__SSODashboardActivityStyle` will be reflected in the dashboard.


4- Integrate MDLIVE SAV SDK with Your Own App
----------------------------------------------

## Gradle Build File Update

This section describes the required `*.gradle` files configurations for obtaining the MDLive SDK. 

Open your root project `build.gradle` file, and update it with MDLive and AppBoy bintray dependencies.  

```groovy
buildscript {
    ...
    dependencies {
        ...
        classpath 'com.google.gms:google-services:x.x.x'
        ...
    }
}
...
allprojects {
    repositories {
        google()
        jcenter()

        //Google
        maven {
            url "https://maven.google.com"
        }
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
        MDL_android_sdk = "com.mdlive.mdla:android-sdk:xxx_xxx_x.x.x@aar"

    }
}
```

**Note:** `MDL_android_sdk = "com.mdlive.mdla:android-sdk:xxx_xxx_x.x.x@aar"` is used to define the version of the SDK to use. 

Once your root `build.gradle` file has been successfully configured, you will need to configure your application `build.gradle` adding the respective dependencies:

 ```groovy
 android {
     ...
     defaultConfig {
         ...
         multiDexEnabled true
         ...
     }
     ...
 }
 ```
 
## AndroidManifest Update

This section describes the required code configuration before using the MDLive SDK.

In your `AndroidManifest.xml` file add this code by replacing `[PUT CIGNA PACKAGE ID HERE]` with your package/application id.

```xml
<manifest ...>
...
    <application
        android:name=".DemoApplication">
    ...
        <provider
            android:name="androidx.core.content.FileProvider"
            android:authorities="[PUT CIGNA PACKAGE ID HERE].fileprovider"
            android:exported="false"
            android:grantUriPermissions="true"
            tools:replace="android:authorities">
            <meta-data
                android:name="android.support.FILE_PROVIDER_PATHS"
                android:resource="@xml/file_paths" />
        </provider>
        ...
    </application>
    ...
</manifest>
```

If you are not using a custom Application class for your app, you will need to create one.  Let's say you named it `DemoApplication`.  Your class should look like the following:

Your `DemoApplication` class must inherit from `MultiDexApplication`.

```java
public class DemoApplication extends MultiDexApplication {
    ...
}
```

You will need to configure the MDLive SDK in the `onCreate` method, by overriding it like this:
```java
@Override
public void onCreate() {
    super.onCreate();
    // Create a new configuration object.
    MdlConfiguration configuration = new MdlConfiguration();
    
    // You can override the default analytics engine by adding your own engine, and entending from AnalyticsEngine.
    configuration.addAnalyticsEngine(new ConsoleAnalyticsEngine());
    
    // Here you can configure specific things like:
    configuration.getApplicationConstantsBuilder()
            .debug(BuildConfig.DEBUG) // This is for letting the SDK know if the app is runnning in Debug mode or not.
            .isSessionTimeoutEnabled(false) // This parameter is true by default, so after 5 minutes the user screen will be looked and be asked by MDLive credentials. We could disable this for SDK usages.
            .isSSOsession(true) // Since SDK uses the single sign on method to access the dashboard we must set this parameter to true.
            .defaultFirebaseFilename("mdlive__firebase_defaults.json"); // Here we pass the google play service json file name that must be in the assests directory.
            
    // Initialize the MDLive SDK and make it ready for use.
    MdlBootstrap.start(this, configuration);
}
```
**Note:** You must request `mdlive__firebase_defaults.json` file to MDLive SDK team by sending an email asking for this configuration file, otherwise you won't be able to use the SDK.

## User Authentication

In order to access the MDLive dashboard, you must to create a `MdlSSODetail` object with the required MDLive user information.
In this demo app, we have preconfigured one test account.
However, when you integrate our MDLIVE SAV SDK with your own app, you will need to pull the member information from your app to authenticate the user.

```java
Calendar birthdateCalendar = GregorianCalendar.getInstance();
birthdateCalendar.set(1917, 0, 1);

MdlSSODetail ssoDetail = MdlSSODetail.builder()
        .ou("cspire")
        .firstName("Cspire")
        .lastName("Demo")
        .gender(FwfSSOGender.MALE)
        .birthdate("18-08-1968")
        .subscriberId("")
        .memberId("")
        .phone("8888888888")
        .email("test@mdlive.com")
        .address1("1234 Test Address")
        .address2("")
        .city("Sunrise")
        .state(FwfState.FL)
        .zipCode("33325")
        .relationship(FwfSSORelationship.SELF)
        .build();
```

Once `MdlSSODetail` is correctly created, you can proceed to attempt a sign in with the following info:
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



