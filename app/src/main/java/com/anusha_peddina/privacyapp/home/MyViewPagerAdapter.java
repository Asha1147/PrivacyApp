package com.anusha_peddina.privacyapp.home;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;

import com.anusha_peddina.privacyapp.card.CardsFragment;
import com.anusha_peddina.privacyapp.password.PasswordsFragment;


public class MyViewPagerAdapter extends FragmentStatePagerAdapter {

    private int numberOfTabs;
    private FragmentManager fragmentManager;
    private PasswordsFragment tab1;
    private CardsFragment tab2;

    public MyViewPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        fragmentManager = fm;
        this.numberOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                if(tab1 == null) { tab1 = new PasswordsFragment(); }
                return tab1;
            case 1:
                if(tab2 == null) { tab2 = new CardsFragment(); }
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }

    public void deleteTabs(){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if(tab1 != null) {
            transaction.remove(tab1);
            tab1 = null;
        }

        if(tab2 != null){
            transaction.remove(tab2);
            tab2 = null;
        }

        transaction.commitNowAllowingStateLoss();
    }
}