package com.anusha_peddina.privacyapp.biometrics;

import com.anusha_peddina.privacyapp.models.Card;
import com.anusha_peddina.privacyapp.models.Password;

public interface BiometricSuccessListener {
    public void callback(boolean isLogin, Card cardModel, Password passwordModel);
}


