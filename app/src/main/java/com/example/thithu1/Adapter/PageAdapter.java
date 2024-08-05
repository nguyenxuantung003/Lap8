package com.example.thithu1.Adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;


import com.example.thithu1.Fragment.DanhsachFragment;
import com.example.thithu1.Fragment.ThemFragment;

public class PageAdapter extends FragmentStateAdapter {

    public PageAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment;

        switch (position) {
            case 0:
                fragment = new DanhsachFragment();
                break;
            case 1:
                // Thay đổi thành GoalsFragment nếu bạn có một Fragment khác cho mục tiêu
                fragment = new ThemFragment();
                break;
            default:
                fragment = new DanhsachFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
