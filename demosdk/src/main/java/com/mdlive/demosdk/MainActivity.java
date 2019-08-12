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

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

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

   /*     MdlSSODetail ssoDetail = MdlSSODetail.builder()
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
                .build();*/

//        /******     Staging Accounts       ******/
//
//        /******     Subcriber Plan            ******/
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


//            /******     Production Accounts       ******/
//
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
//
//        MdlSSODetail ssoDetail = MdlSSODetail.builder()
//                .ou("cspire")
//                .firstName("JENNIE1")
//                .lastName("TEST1")
//                .gender(FwfSSOGender.FEMALE)
//                .birthdate("15-08-1982")
//                .subscriberId("")
//                .memberId("")
//                .phone("6012783209")
//                .email("qatestcoordinators@cspire.com")
//                .address1("839 LEO PAUL ST")
//                .address2("")
//                .city("PEARL")
//                .state(FwfState.MS)
//                .zipCode("39208")
//                .relationship(FwfSSORelationship.SELF)
//                .build();


        // Production account
//        MdlSSODetail ssoDetail = MdlSSODetail.builder()
//                .ou("cspire")
//                .firstName("emilio")
//                .lastName("TEST5")
//                .gender(FwfSSOGender.MALE)
//                .birthdate("08-08-1988")
//                .subscriberId("")
//                .memberId("")
//                .phone("jhu@mdlive.com")
//                .email("qatestcoordinators@cspire.com")
//                .address1("1234 Test Road")
//                .address2("")
//                .city("Sunrise")
//                .state(FwfState.FL)
//                .zipCode("33325")
//                .relationship(FwfSSORelationship.SELF)
//                .build();

        // Staging account
        MdlSSODetail ssoDetail = MdlSSODetail.builder()
                .ou("cspire")
                .firstName("emilio")
                .lastName("TEST6")
                .gender(FwfSSOGender.MALE)
                .birthdate("08-08-1988")
                .subscriberId("")
                .memberId("")
                .phone("2692679816")
                .email("jhu@mdlive.com")
                .address1("1234 Test Road")
                .address2("")
                .city("Sunrise")
                .state(FwfState.FL)
                .zipCode("33326")
                .relationship(FwfSSORelationship.SELF)
                .build();




        MdlApplicationSupport.getAuthenticationCenter()
                .singleSignOn(ssoDetail)
                .map(new Func1<MdlUserSession, Intent>() {
                    @Override
                    public Intent call(MdlUserSession mdlUserSession) {
                        return MdlApplicationSupport.getIntentFactory().ssoDashboard(MainActivity.this);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
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
    }

    private void showProgressBar(boolean showProgressBar) {
        mSeeProviderButton.setVisibility(showProgressBar ? View.GONE : View.VISIBLE);
        mProgressBar.setVisibility(showProgressBar ? View.VISIBLE : View.GONE);
    }

}
