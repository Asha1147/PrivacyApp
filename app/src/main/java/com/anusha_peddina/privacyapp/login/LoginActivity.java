package com.anusha_peddina.privacyapp.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.anusha_peddina.privacyapp.biometrics.BiometricSuccessListener;
import com.anusha_peddina.privacyapp.biometrics.PMBiometricsManager;
import com.anusha_peddina.privacyapp.R;
import com.anusha_peddina.privacyapp.home.HomeActivity;
import com.anusha_peddina.privacyapp.models.Card;
import com.anusha_peddina.privacyapp.models.Password;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements BiometricSuccessListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.biometrics_check_button)
    public void onBiometricsButtonClicked() {
        PMBiometricsManager pMBiometricsManager = new PMBiometricsManager(this, this, true, null, null);
        pMBiometricsManager.setBiometricSuccessListener(this);
        pMBiometricsManager.authenticateWithBioMetrics();
    }

    @Override
    public void callback(boolean isLogin, Card cardModel, Password password) {
        if(isLogin) {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        }
    }
}