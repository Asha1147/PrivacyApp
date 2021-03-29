package com.anusha_peddina.privacyapp.password;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.anusha_peddina.privacyapp.biometrics.BiometricSuccessListener;
import com.anusha_peddina.privacyapp.R;
import com.anusha_peddina.privacyapp.models.Card;
import com.anusha_peddina.privacyapp.models.Password;
import com.anusha_peddina.privacyapp.utils.PaSharedPreferences;

public class PasswordsFragment extends Fragment implements BiometricSuccessListener {

    ListView list;

    public PasswordsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_passwords, container, false);
        list = view.findViewById(R.id.passwords_list);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateListView();
    }

    public void updateListView() {
        PasswordListAdapter passwordListAdapter = new PasswordListAdapter(
                PaSharedPreferences.getSavedPasswordsList(getActivity()),
                getActivity(),
                PasswordsFragment.this);
        list.setAdapter(passwordListAdapter);
        passwordListAdapter.notifyDataSetChanged();
    }

    @Override
    public void callback(boolean isLogin, Card cardModel, Password passwordModel) {
        if(!isLogin && passwordModel != null) {
            AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
            alertDialog.setTitle("Password of "+passwordModel.accountType);
            alertDialog.setMessage(passwordModel.password);
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }
    }
}
