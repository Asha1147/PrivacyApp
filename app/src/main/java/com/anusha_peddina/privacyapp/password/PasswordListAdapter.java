package com.anusha_peddina.privacyapp.password;

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
import com.anusha_peddina.privacyapp.models.Password;
import com.anusha_peddina.privacyapp.utils.PaSharedPreferences;

import java.util.ArrayList;

public class PasswordListAdapter extends ArrayAdapter {

    private ArrayList<Password> passwordArrayList;
    Context mContext;
    BiometricSuccessListener biometricSuccessListener;


    // View lookup cache
    private static class ViewHolder {
        TextView accountName;
        TextView userName;
        Button viewPasswordButton;
        ImageView deleteButton;

    }

    public PasswordListAdapter(ArrayList<Password> data, Context context, BiometricSuccessListener biometricSuccessListener) {
        super(context, R.layout.password_list_item, data);
        this.passwordArrayList = data;
        this.mContext = context;
        this.biometricSuccessListener = biometricSuccessListener;

    }

    @Nullable
    @Override
    public Password getItem(int position) {
        return passwordArrayList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder; // view lookup cache stored in tag
        Password password = getItem(position);
        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.password_list_item, parent, false);
            viewHolder.accountName = (TextView) convertView.findViewById(R.id.account_name);
            viewHolder.userName = (TextView) convertView.findViewById(R.id.user_name);
            viewHolder.viewPasswordButton = (Button) convertView.findViewById(R.id.view_password);
            viewHolder.deleteButton = convertView.findViewById(R.id.delete_password_item);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.accountName.setText("Account name : "+password.accountType);
        viewHolder.userName.setText("Username/email : "+password.userName);
        viewHolder.viewPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PMBiometricsManager pMBiometricsManager = new PMBiometricsManager(biometricSuccessListener, mContext, false, null, passwordArrayList.get(position));
                pMBiometricsManager.setBiometricSuccessListener(biometricSuccessListener);
                pMBiometricsManager.authenticateWithBioMetrics();
            }
        });
        viewHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage("Are you sure you want to delete "+passwordArrayList.get(position).accountType+" Information ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                passwordArrayList.remove(position);
                                PaSharedPreferences.savePasswordsListInPreference(mContext, passwordArrayList);
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
