package com.anusha_peddina.privacyapp.card;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.anusha_peddina.privacyapp.biometrics.BiometricSuccessListener;
import com.anusha_peddina.privacyapp.biometrics.PMBiometricsManager;
import com.anusha_peddina.privacyapp.R;
import com.anusha_peddina.privacyapp.models.Card;
import com.anusha_peddina.privacyapp.utils.PaSharedPreferences;

import java.util.ArrayList;

public class CardsListAdapter extends ArrayAdapter {

    private ArrayList<Card> cardArrayList;
    Context mContext;
    BiometricSuccessListener biometricSuccessListener;

    // View lookup cache
    private static class ViewHolder {
        TextView cardName;
        TextView cardNumber;
        TextView expiry;
        Button viewCvvButton;
        ImageView deleteButton;
    }

    public CardsListAdapter(ArrayList data, Context context, BiometricSuccessListener biometricSuccessListener) {
        super(context, R.layout.card_list_item, data);
        this.cardArrayList = data;
        this.mContext = context;
        this.biometricSuccessListener = biometricSuccessListener;
    }

    @Nullable
    @Override
    public Card getItem(int position) {
        return cardArrayList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder; // view lookup cache stored in tag
        Card card = getItem(position);
        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.card_list_item, parent, false);
            viewHolder.cardName = (TextView) convertView.findViewById(R.id.card_name);
            viewHolder.cardNumber = (TextView) convertView.findViewById(R.id.card_number);
            viewHolder.expiry = (TextView) convertView.findViewById(R.id.expiry);
            viewHolder.viewCvvButton = (Button) convertView.findViewById(R.id.view_cvv_Button);
            viewHolder.deleteButton = convertView.findViewById(R.id.delete_card_item);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.cardName.setText("Card name : "+card.cardName);
        viewHolder.cardNumber.setText("Card Number : "+card.cardNumber);
        viewHolder.expiry.setText("Expiry : "+card.cardExpiry);
        viewHolder.viewCvvButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PMBiometricsManager pMBiometricsManager = new PMBiometricsManager(biometricSuccessListener, mContext, false, cardArrayList.get(position), null);
                pMBiometricsManager.setBiometricSuccessListener(biometricSuccessListener);
                pMBiometricsManager.authenticateWithBioMetrics();
            }
        });
        viewHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage("Are you sure you want to delete "+cardArrayList.get(position).cardName+" card ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                cardArrayList.remove(position);
                                PaSharedPreferences.saveCardListInPreference(mContext, cardArrayList);
                                notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        // Return the completed view to render on screen
        return convertView;
    }



}
