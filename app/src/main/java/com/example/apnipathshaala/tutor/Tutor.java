package com.example.apnipathshaala.tutor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import com.example.apnipathshaala.R;
import com.example.apnipathshaala.common.Homepage;
import com.example.apnipathshaala.sfragments.AccountFragment;
import com.example.apnipathshaala.sfragments.PostFragment;
import com.example.apnipathshaala.sfragments.SearchFragment;
import com.example.apnipathshaala.tfragments.tSearchFragment;
import com.example.apnipathshaala.tfragments.tpostfragment;
import com.example.apnipathshaala.utils.SectionsPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class Tutor extends AppCompatActivity {

    private static final String TAG = "Tutor";
    private static final int REQUEST_CODE = 1;

    //widgets
    private TabLayout mTabLayout;
    public ViewPager mViewPager;


    //vars
    public SectionsPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor);
        mTabLayout = findViewById(R.id.tabs);
        mViewPager = findViewById(R.id.viewpager_container);

        verifyPermissions();
    }

    private void setupViewPager(){
        mPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mPagerAdapter.addFragment(new tSearchFragment());
        mPagerAdapter.addFragment(new tpostfragment());
        mPagerAdapter.addFragment(new AccountFragment());

        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.getTabAt(0).setText(getString(R.string.fragment_search));
        mTabLayout.getTabAt(1).setText(getString(R.string.fragment_post));
        mTabLayout.getTabAt(2).setText(getString(R.string.fragment_account));

    }

    private void verifyPermissions(){
        Log.d(TAG, "verifyPermissions: asking user for permissions");
        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                permissions[0]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this.getApplicationContext(),
                permissions[1]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this.getApplicationContext(),
                permissions[2]) == PackageManager.PERMISSION_GRANTED){
            setupViewPager();
        }else{
            ActivityCompat.requestPermissions(com.example.apnipathshaala.tutor.Tutor.this,
                    permissions,
                    REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        verifyPermissions();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(getApplicationContext(), Homepage.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
    }
}