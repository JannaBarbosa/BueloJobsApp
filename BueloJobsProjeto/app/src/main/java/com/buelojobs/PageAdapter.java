package com.buelojobs;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import eventos.Eventos;
import vagas.Vagas;

import formacao.Formacao;

public class PageAdapter extends FragmentPagerAdapter {

    private int tabCount;
    public PageAdapter(FragmentManager fm, int tabCount) {

        super(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.tabCount = tabCount;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 :
                return new Vagas();
            case 1:
                return new Formacao();
            case 2:
                return new Eventos();
            default:
                return null;
        }
    }



    @Override
    public int getCount() {
        return tabCount;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }
}



