package com.anusha_peddina.privacyapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.anusha_peddina.privacyapp.models.Card;
import com.anusha_peddina.privacyapp.models.Password;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PaSharedPreferences {
    public static ArrayList<Card> getSavedCardsList(Context context) {
        ArrayList<Card> cardArrayList = new ArrayList<>();
        SharedPreferences sharedPreferences = context.getSharedPreferences("app_info", Context.MODE_PRIVATE);
        String serializedObject = sharedPreferences.getString("card_list", null);
        if (serializedObject != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<Card>>(){}.getType();
            cardArrayList = gson.fromJson(serializedObject, type);
        }
        return cardArrayList;
    }

    public static void saveCardListInPreference(Context context, ArrayList<Card> cardList) {
        Gson gson = new Gson();
        String cardListString = gson.toJson(cardList);

        SharedPreferences sharedPref = context.getSharedPreferences("app_info", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString("card_list", cardListString);
        editor.commit();
    }

    public static ArrayList<Password> getSavedPasswordsList(Context context) {
        ArrayList<Password> passwordsList = new ArrayList<>();
        SharedPreferences sharedPreferences = context.getSharedPreferences("app_info", Context.MODE_PRIVATE);
        String serializedObject = sharedPreferences.getString("password_list", null);
        if (serializedObject != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<Password>>(){}.getType();
            passwordsList = gson.fromJson(serializedObject, type);
        }
        return passwordsList;
    }

    public static void savePasswordsListInPreference(Context context, ArrayList<Password> cardList) {
        Gson gson = new Gson();
        String cardListString = gson.toJson(cardList);

        SharedPreferences sharedPref = context.getSharedPreferences("app_info", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString("password_list", cardListString);
        editor.commit();
    }
}
