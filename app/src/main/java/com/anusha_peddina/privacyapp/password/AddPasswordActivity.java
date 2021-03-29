package com.anusha_peddina.privacyapp.password;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.anusha_peddina.privacyapp.R;
import com.anusha_peddina.privacyapp.models.Password;
import com.anusha_peddina.privacyapp.utils.PaSharedPreferences;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;

public class AddPasswordActivity extends AppCompatActivity {

    @BindView(R.id.password_back_button) ImageView passwordBackButton;
    @BindView(R.id.account_type) EditText accountType;
    @BindView(R.id.account_user_name) EditText accountUsername;
    @BindView(R.id.account_password) EditText accountPassword;
    @BindView(R.id.add_password_button) Button passwordButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_password);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.password_back_button)
    public void onPasswordBackButton() {
        closeActivity();
    }

    @OnClick(R.id.add_password_button)
    public void onAddPasswordButton() {
        if(passwordButton.isEnabled()) {
            Password password = new Password();
            password.accountType = accountType.getText().toString();
            password.userName = accountUsername.getText().toString();
            password.password = accountPassword.getText().toString();
            ArrayList<Password> oldPasswordList = PaSharedPreferences.getSavedPasswordsList(this);
            oldPasswordList.add(password);
            PaSharedPreferences.savePasswordsListInPreference(this, oldPasswordList);
            closeActivity();
        }
    }

    @OnFocusChange(R.id.account_type)
    public void onAccountTypeFocusChange() {
        checkAddButtonToEnable();
    }

    @OnFocusChange(R.id.account_user_name)
    public void onAccountUsernameFocusChange() {
        checkAddButtonToEnable();
    }

    @OnFocusChange(R.id.account_password)
    public void onAccountPasswordFocusChange() {
        checkAddButtonToEnable();
    }

    @OnTextChanged(R.id.account_type)
    public void onAccountTypeTextChange() {
        checkAddButtonToEnable();
    }

    @OnTextChanged(R.id.account_user_name)
    public void onAccountUsernameTextChange() {
        checkAddButtonToEnable();
    }

    @OnTextChanged(R.id.account_password)
    public void onAccountPasswordTextChange() {
        checkAddButtonToEnable();
    }

    private void checkAddButtonToEnable() {
        if(!accountType.getText().toString().isEmpty() &&
                !accountUsername.getText().toString().isEmpty() &&
                !accountPassword.getText().toString().isEmpty()) {
            passwordButton.setEnabled(true);
        } else {
            passwordButton.setEnabled(false);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        closeActivity();
    }

    void closeActivity() {
        finish();
    }
}