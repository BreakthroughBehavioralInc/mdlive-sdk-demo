package com.mdlive.demosdk;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import com.mdlive.mdlcore.application.MdlApplicationSupport;
import androidx.appcompat.app.AppCompatActivity;

import com.mdlive.models.enumz.fwf.FwfSSOGender;
import com.mdlive.models.enumz.fwf.FwfSSORelationship;
import com.mdlive.models.enumz.fwf.FwfState;
import com.mdlive.models.model.MdlSSODetail;
import com.mdlive.models.model.MdlUserSession;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/*
 * Copyright MDLive.  All rights reserved.
 */

public class MainActivity extends AppCompatActivity {

    private Button mSeeProviderButton;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSeeProviderButton = (Button) findViewById(R.id.enter);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
    }

    public void enterSDK(View v) {
        showProgressBar(true);

//        MdlSSODetail ssoDetail = MdlSSODetail.builder()
//                .ou("cspire")
//                .firstName("Cspire")
//                .lastName("Demo")
//                .gender(FwfSSOGender.MALE)
//                .birthdate("18-08-1968")
//                .subscriberId("")
//                .memberId("")
//                .phone("8888888888")
//                .email("test@mdlive.com")
//                .address1("1234 Test Address")
//                .address2("")
//                .city("Sunrise")
//                .state(FwfState.FL)
//                .zipCode("33325")
//                .relationship(FwfSSORelationship.SELF)
//                .build();


        /******     Staging Accounts       ******/

        /******     Subcriber Plan            ******/
//        MdlSSODetail ssoDetail = MdlSSODetail.builder()
//                .ou("cspire")
//                .firstName("freevisit")
//                .lastName("noaddfamily")
//                .gender(FwfSSOGender.MALE)
//                .birthdate("08-08-1988")
//                .subscriberId("")
//                .memberId("")
//                .phone("9178602754")
//                .email("spradeep@mdlive.com")
//                .address1("1234 Test Address")
//                .address2("")
//                .city("Sunrise")
//                .state(FwfState.FL)
//                .zipCode("33325")
//                .relationship(FwfSSORelationship.SELF)
//                .build();

//        /******     DTC Plan                    ******/
//        MdlSSODetail ssoDetail = MdlSSODetail.builder()
//                .ou("cspire")
//                .firstName("dtcvisit")
//                .lastName("yesaddfamily")
//                .gender(FwfSSOGender.MALE)
//                .birthdate("09-09-1990")
//                .subscriberId("")
//                .memberId("")
//                .phone("9178602754")
//                .email("spradeep@mdlive.com")
//                .address1("1234 Test Address")
//                .address2("")
//                .city("Sunrise")
//                .state(FwfState.FL)
//                .zipCode("33325")
//                .relationship(FwfSSORelationship.SELF)
//                .build();

        /******     CIGNA Test account                    ******/
        MdlSSODetail ssoDetail = MdlSSODetail.builder()
                .ou("cigna")
                .firstName("sdk")
                .lastName("demo")
                .gender(FwfSSOGender.MALE)
                .birthdate("01-01-1988")
                .subscriberId("sdktest0901")
                .memberId("cignasdk2019|1656205449.2447784|YjM2YTY1YWNiYjk1MjM5MmU2YTg1NjQ2MTAzYzQ1NGU4YTc3OGNmZWEwMDZmM2E3M2JhZjE5ZTc5NWE1MDBmNQ==")
                .phone("8888888888")
                .email("test@mdlive.com")
                .address1("1234 Test Address")
                .address2("")
                .city("Sunrise")
                .state(FwfState.FL)
                .zipCode("33325")
                .relationship(FwfSSORelationship.SELF)
                .build();


        /******     Production Accounts       ******/

//            /******     Subcriber Plan            ******/
//            MdlSSODetail ssoDetail = MdlSSODetail.builder()
//                    .ou("cspire")
//                    .firstName("android")
//                    .lastName("freevisit")
//                    .gender(FwfSSOGender.MALE)
//                    .birthdate("08-08-1988")
//                    .subscriberId("")
//                    .memberId("")
//                    .phone("9178602754")
//                    .email("spradeep@mdlive.com")
//                    .address1("1234 Test Address")
//                    .address2("")
//                    .city("Sunrise")
//                    .state(FwfState.FL)
//                    .zipCode("33325")
//                    .relationship(FwfSSORelationship.SELF)
//                    .build();

//            /******     DTC Plan                    ******/
//            MdlSSODetail ssoDetail = MdlSSODetail.builder()
//                    .ou("cspire")
//                    .firstName("android")
//                    .lastName("59visit")
//                    .gender(FwfSSOGender.MALE)
//                    .birthdate("08-08-1988")
//                    .subscriberId("")
//                    .memberId("")
//                    .phone("9178602754")
//                    .email("spradeep@mdlive.com")
//                    .address1("1234 Test Address")
//                    .address2("")
//                    .city("Sunrise")
//                    .state(FwfState.FL)
//                    .zipCode("33325")
//                    .relationship(FwfSSORelationship.SELF)
//                    .build();

        final Disposable disposable = (Disposable) MdlApplicationSupport.getAuthenticationCenter()
                .singleSignOn(ssoDetail)
                .map(new Function<MdlUserSession, Object>() {
                    @Override
                    public Object apply(MdlUserSession mdlUserSession) throws Exception {
                        return MdlApplicationSupport.getIntentFactory().ssoDashboard(MainActivity.this);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Consumer<Object>() {
                            @Override
                            public void accept(Object o) throws Exception {
                                startActivity((Intent) o);
                                showProgressBar(false);
                            }
                        },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
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

}
