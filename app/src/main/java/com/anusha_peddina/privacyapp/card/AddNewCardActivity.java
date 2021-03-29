package com.anusha_peddina.privacyapp.card;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.anusha_peddina.privacyapp.R;
import com.anusha_peddina.privacyapp.models.Card;
import com.anusha_peddina.privacyapp.utils.PaSharedPreferences;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;

public class AddNewCardActivity extends AppCompatActivity {

    @BindView(R.id.card_back_button) ImageView cardBackButton;
    @BindView(R.id.card_name) EditText cardName;
    @BindView(R.id.card_number) EditText cardNumber;
    @BindView(R.id.card_expiry) EditText cardExpiry;
    @BindView(R.id.card_cvv) EditText cardCVV;
    @BindView(R.id.add_card_button) Button addCardButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_card);
        ButterKnife.bind(this);
        cardFormats();
    }

    @OnClick(R.id.card_back_button)
    public void onCardButtonClick() {
        closeActivity();
    }

    @OnClick(R.id.add_card_button)
    public void onAddCardButton() {
        if(addCardButton.isEnabled()) {
            Card card = new Card();
            card.cardName = cardName.getText().toString();
            card.cardNumber = cardNumber.getText().toString();
            card.cardExpiry = cardExpiry.getText().toString();
            card.cardCVV = cardCVV.getText().toString();
            ArrayList<Card> oldCardList = PaSharedPreferences.getSavedCardsList(this);
            oldCardList.add(card);
            PaSharedPreferences.saveCardListInPreference(this, oldCardList);
            closeActivity();
        }
    }

    @OnFocusChange(R.id.card_name)
    public void onCardNameFocusChange() {
        checkAddButtonToEnable();
    }

    @OnFocusChange(R.id.card_number)
    public void onCardNumberFocusChange() {
        checkAddButtonToEnable();
    }

    @OnFocusChange(R.id.card_expiry)
    public void onCardExpiryFocusChange() {
        checkAddButtonToEnable();
    }

    @OnFocusChange(R.id.card_cvv)
    public void onCardCVVFocusChange() {
        checkAddButtonToEnable();
    }




    @OnTextChanged(R.id.card_name)
    public void onCardNameTextChange() {
        checkAddButtonToEnable();
    }

    @OnTextChanged(R.id.card_number)
    public void onCardNumberTextChange() {
        checkAddButtonToEnable();
    }

    @OnTextChanged(R.id.card_expiry)
    public void onCardExpiryTextChange() {
        checkAddButtonToEnable();
    }

    @OnTextChanged(R.id.card_cvv)
    public void onCardCVVTextChange() {
        checkAddButtonToEnable();
    }

    private void checkAddButtonToEnable() {
        if(!cardName.getText().toString().isEmpty() &&
                !cardNumber.getText().toString().isEmpty() &&
                !cardExpiry.getText().toString().isEmpty() &&
                !cardCVV.getText().toString().isEmpty()) {
            addCardButton.setEnabled(true);
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

    void cardFormats() {
        cardNumber.addTextChangedListener(new TextWatcher() {

            private static final int TOTAL_SYMBOLS = 19; // size of pattern 0000-0000-0000-0000
            private static final int TOTAL_DIGITS = 16; // max numbers of digits in pattern: 0000 x 4
            private static final int DIVIDER_MODULO = 5; // means divider position is every 5th symbol beginning with 1
            private static final int DIVIDER_POSITION = DIVIDER_MODULO - 1; // means divider position is every 4th symbol beginning with 0
            private static final char DIVIDER = '-';

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // noop
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // noop
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!isInputCorrect(s, TOTAL_SYMBOLS, DIVIDER_MODULO, DIVIDER)) {
                    s.replace(0, s.length(), buildCorrectString(getDigitArray(s, TOTAL_DIGITS), DIVIDER_POSITION, DIVIDER));
                }
            }

            private boolean isInputCorrect(Editable s, int totalSymbols, int dividerModulo, char divider) {
                boolean isCorrect = s.length() <= totalSymbols; // check size of entered string
                for (int i = 0; i < s.length(); i++) { // check that every element is right
                    if (i > 0 && (i + 1) % dividerModulo == 0) {
                        isCorrect &= divider == s.charAt(i);
                    } else {
                        isCorrect &= Character.isDigit(s.charAt(i));
                    }
                }
                return isCorrect;
            }

            private String buildCorrectString(char[] digits, int dividerPosition, char divider) {
                final StringBuilder formatted = new StringBuilder();

                for (int i = 0; i < digits.length; i++) {
                    if (digits[i] != 0) {
                        formatted.append(digits[i]);
                        if ((i > 0) && (i < (digits.length - 1)) && (((i + 1) % dividerPosition) == 0)) {
                            formatted.append(divider);
                        }
                    }
                }

                return formatted.toString();
            }

            private char[] getDigitArray(final Editable s, final int size) {
                char[] digits = new char[size];
                int index = 0;
                for (int i = 0; i < s.length() && index < size; i++) {
                    char current = s.charAt(i);
                    if (Character.isDigit(current)) {
                        digits[index] = current;
                        index++;
                    }
                }
                return digits;
            }
        });

        cardExpiry.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 2) {
                    if(start==2 && before==1 && !s.toString().contains("/")){
                        cardExpiry.setText(""+s.toString().charAt(0));
                        cardExpiry.setSelection(1);
                    } else {
                        cardExpiry.setText(s + "/");
                        cardExpiry.setSelection(3);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }
}