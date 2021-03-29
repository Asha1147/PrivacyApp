package com.anusha_peddina.privacyapp.card;

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
import com.anusha_peddina.privacyapp.password.PasswordListAdapter;
import com.anusha_peddina.privacyapp.password.PasswordsFragment;
import com.anusha_peddina.privacyapp.utils.PaSharedPreferences;

public class CardsFragment extends Fragment implements BiometricSuccessListener {

    ListView list;

    public CardsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cards, container, false);
        list = view.findViewById(R.id.cards_list);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateListView();
    }

    public void updateListView() {
        CardsListAdapter cardsListAdapter = new CardsListAdapter(
                PaSharedPreferences.getSavedCardsList(getActivity()),
                getActivity(),
                CardsFragment.this);
        list.setAdapter(cardsListAdapter);
        cardsListAdapter.notifyDataSetChanged();
    }

    @Override
    public void callback(boolean isLogin, Card cardModel, Password passwordModel) {
        if(!isLogin && cardModel != null) {
            AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
            alertDialog.setTitle("CVV of "+cardModel.cardName);
            alertDialog.setMessage(cardModel.cardCVV);
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
