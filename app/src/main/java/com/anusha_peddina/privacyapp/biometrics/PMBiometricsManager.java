package com.anusha_peddina.privacyapp.biometrics;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.anusha_peddina.privacyapp.R;
import com.anusha_peddina.privacyapp.models.Card;
import com.anusha_peddina.privacyapp.models.Password;

import java.util.concurrent.Executor;

import static androidx.biometric.BiometricConstants.ERROR_NEGATIVE_BUTTON;

public class PMBiometricsManager {

    private Context context;
    private boolean isLogin = false;
    private Card cardModel;
    private Password passwordModel;
    private BiometricPrompt biometricPrompt;

    public void setBiometricSuccessListener(BiometricSuccessListener biometricSuccessListener) {
        this.biometricSuccessListener = biometricSuccessListener;
    }

    BiometricSuccessListener biometricSuccessListener;

    public PMBiometricsManager(BiometricSuccessListener biometricSuccessListener, Context context, boolean isLogin, Card cardModel, Password passwordModel) {
        this.context = context;
        this.isLogin = isLogin;
        this.cardModel = cardModel;
        this.passwordModel = passwordModel;
        this.biometricSuccessListener = biometricSuccessListener;
    }

    public void authenticateWithBioMetrics() {
        Executor executor = ContextCompat.getMainExecutor(context);
        BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle(context.getResources().getString(R.string.confirm_biometrics))
                .setConfirmationRequired(false)
                .setDeviceCredentialAllowed(true)
                .build();
        biometricPrompt = new BiometricPrompt((FragmentActivity) context,
                executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                if(errorCode == ERROR_NEGATIVE_BUTTON) {
                    biometricPrompt.cancelAuthentication();
                }
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                onSuccessfulAuthentication();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }
        });

        biometricPrompt.authenticate(promptInfo);
    }

    private void onSuccessfulAuthentication() {
        if(isLogin) {
            biometricSuccessListener.callback(isLogin, null, null);
            isLogin = false;
        } else if(cardModel != null) {
            biometricSuccessListener.callback(isLogin, cardModel, null);
            cardModel = null;
        } else if(passwordModel != null) {
            biometricSuccessListener.callback(isLogin, null, passwordModel);
            passwordModel = null;
        }
    }
}
