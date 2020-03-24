package com.mdlive.demosdk;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.material.snackbar.Snackbar;
import com.mdlive.mdlcore.activity.apienvironment.MdlApiEnvironmentActivity;
import com.mdlive.mdlcore.activity.debugmenu.fragment.DebugPinDialogFragment;
import com.mdlive.mdlcore.activity.support.MdlSupportActivity;
import com.mdlive.mdlcore.application.MdlApplicationSupport;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.mdlive.mdlcore.application.configuration.MdlBootstrap;
import com.mdlive.mdlcore.fwfrodeo.fwf.FwfGuiHelper;
import com.mdlive.models.enumz.fwf.FwfSSOGender;
import com.mdlive.models.enumz.fwf.FwfSSORelationship;
import com.mdlive.models.enumz.fwf.FwfState;
import com.mdlive.models.model.MdlSSODetail;
import com.mdlive.models.model.MdlUserSession;
import com.mdlive.models.model.apienvironment.MdlApiEnvironment;
import com.mdlive.services.exception.MdlRunTimeException;


import io.reactivex.Single;
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

    private MdlSSODetail ssoDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSeeProviderButton = findViewById(R.id.enter);
        mProgressBar = findViewById(R.id.progress_bar);
    }

    public void enterSDK(View v) {
        showProgressBar(true);

        final Disposable disposable = MdlApplicationSupport.getAuthenticationCenter()
                .singleSignOn(ssoDetail)
                .map((Function<MdlUserSession, Object>) mdlUserSession -> MdlApplicationSupport.getIntentFactory().ssoDashboard(MainActivity.this))
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> showProgressBar(false))
                .subscribe(intent -> startActivity((Intent) intent), throwable -> Snackbar.make(getWindow().getDecorView().findViewById(android.R.id.content),
                        throwable.getMessage(),
                        Snackbar.LENGTH_SHORT)
                        .show()
                );
    }

    private void showProgressBar(boolean showProgressBar) {
        mSeeProviderButton.setVisibility(showProgressBar ? View.GONE : View.VISIBLE);
        mProgressBar.setVisibility(showProgressBar ? View.VISIBLE : View.GONE);
    }

    public void switchEnvironment(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose an environment");

        // add a list
        String[] animals = {"Production", "Development"};
        builder.setItems(animals, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0: // Production

                        ssoDetail = MdlSSODetail.builder()
                                .ou("Cigna")
                                .firstName("Xuanyu")
                                .lastName("Hu")
                                .gender(FwfSSOGender.MALE)
                                .birthdate("26-08-1989")
                                .subscriberId("10407361700")
                                .memberId("jeffreywho89|1656205449.2447784|NGI1NjlmOTBmNzhiODMyOGM5YTZiNGRlNmI5MTVlYzE0M2IwNjlhMjgwMTY4YmQyN2M1ZGNhYjNjMzM5NTFiMA==")
                                .phone("8888888888")
                                .email("test@mdlive.com")
                                .address1("123 Test Road")
                                .address2("")
                                .city("Sunrise")
                                .state(FwfState.FL)
                                .zipCode("33325")
                                .relationship(FwfSSORelationship.SELF)
                                .build();

                        MdlBootstrap.initialize(MdlBootstrap.PRODUCTION);

                        break;
                    case 1: // Development

                        ssoDetail = MdlSSODetail.builder()
                                .ou("Cigna")
                                .firstName("staging")
                                .lastName("cignasdk")
                                .gender(FwfSSOGender.MALE)
                                .birthdate("01-01-1980")
                                .subscriberId("cignasdk2019")
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

                        MdlBootstrap.initialize(MdlBootstrap.DEVELOPMENT);

                        break;
                }
            }
        });

// create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();


    }

}
