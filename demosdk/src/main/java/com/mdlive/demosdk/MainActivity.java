package com.mdlive.demosdk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.mdlive.mdlcore.application.MdlApplicationSupport;
import com.mdlive.mdlcore.fwfrodeo.fwf.enumz.FwfSSOGender;
import com.mdlive.mdlcore.fwfrodeo.fwf.enumz.FwfSSORelationship;
import com.mdlive.mdlcore.fwfrodeo.fwf.enumz.FwfState;
import com.mdlive.mdlcore.model.MdlSSODetail;
import com.mdlive.mdlcore.model.MdlUserSession;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/*
 * Copyright MDLive.  All rights reserved.
 */
public class MainActivity extends AppCompatActivity {

    private Button mSeeProviderButton;
    private ProgressBar mProgressBar;
    private Disposable mDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSeeProviderButton = (Button) findViewById(R.id.enter);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
    }

    public void enterSDK(View v) {
        showProgressBar(true);

        MdlSSODetail ssoDetail = MdlSSODetail.builder()
                .ou("HCSC-000088_IL")
                .firstName("HCSC")
                .lastName("SDKTest")
                .gender(FwfSSOGender.MALE)
                .birthdate("08-08-1988")
                .subscriberId("hcscsdktest001")
                .memberId("")
                .phone("555-555-5555")
                .email("test@test.com")
                .address1("123 Test SDK")
                .address2("")
                .city("Sunrise")
                .state(FwfState.FL)
                .zipCode("33325")
                .relationship(FwfSSORelationship.SELF)
                .build();

        mDisposable = MdlApplicationSupport.getAuthenticationCenter()
                .singleSignOn(ssoDetail)
                .map(new Function<MdlUserSession, Intent>() {
                    @Override
                    public Intent apply(MdlUserSession mdlUserSession) {
                        return MdlApplicationSupport.getIntentFactory().ssoDashboard(MainActivity.this);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Consumer<Intent>() {
                            @Override
                            public void accept(Intent intent) {
                                startActivity(intent);
                                showProgressBar(false);
                            }
                        },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) {
                                Log.e(MainActivity.class.getSimpleName(), throwable.toString());
                                showProgressBar(false);
                            }
                        }
                );
    }

    private void showProgressBar(boolean showProgressBar) {
        mSeeProviderButton.setVisibility(showProgressBar ? View.GONE : View.VISIBLE);
        mProgressBar.setVisibility(showProgressBar ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDisposable.dispose();
    }
}
