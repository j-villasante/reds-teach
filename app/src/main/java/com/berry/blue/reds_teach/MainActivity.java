package com.berry.blue.reds_teach;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.tech.MifareUltralight;
import android.nfc.tech.NfcA;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.berry.blue.reds_teach.interfaces.activities.Main;
import com.berry.blue.reds_teach.nfcUtils.TagControl;
import com.berry.blue.reds_teach.results.ResultsFragment;
import com.berry.blue.reds_teach.words.WordsFragment;
import com.berry.blue.reds_teach.words.dialog.NfcDialog;
import com.berry.blue.reds_teach.words.dialog.WordsDialog;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements Main{
    @BindView(R.id.drawer_layout) DrawerLayout drawer;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.fab) FloatingActionButton fab;
    @BindView(R.id.nav_view) NavigationView navigationView;
    @BindView(R.id.coordinator_layout) CoordinatorLayout coordinatorLayout;

    private ActionBarDrawerToggle toggle;
    private FragmentManager fragmentManager;
    private DialogFragment currentDialog;
    private TagControl tagControl;

    // NFC variables
    private NfcAdapter mNfcAdapter;
    private PendingIntent mPendingIntent;
    private IntentFilter[] mFilters;
    private String[][] mTechLists;
    private boolean isWriteToNfcEnabled;

    private String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        fab.setOnClickListener((View view) -> this.showDialog(WordsDialog.newInstance()));

        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);

        navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);

        this.fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        WordsFragment fragment = new WordsFragment().setView(this);
        transaction.add(R.id.fragment_container, fragment);
        transaction.commit();

        tagControl = TagControl.instance().setView(this);
        nfcInit();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }

    @Override
    protected void onPause() {
        disableNfc();
        super.onPause();
    }

    @Override
    protected void onResume() {
        enableNfc();
        super.onResume();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        if (isWriteToNfcEnabled) tagControl.writeKeyToTagFromIntent(intent);
        Log.i(TAG, "Discovered nfc");
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void showDialog(DialogFragment dialog) {
        this.currentDialog = dialog;
        dialog.show(fragmentManager, "NewWordDialog");
    }

    @Override
    public void dismissDialog() {
        if (currentDialog != null && currentDialog.getDialog().isShowing())
            this.currentDialog.dismiss();
    }

    @Override
    public void showMessage(String message) {
        Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onDismissNfcDialog() {
        isWriteToNfcEnabled = false;
    }

    @Override
    public void onOpenNfcDialog() {
        isWriteToNfcEnabled = true;
    }

    public boolean onNavigationItemSelected(MenuItem item) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.nav_words: {
                fragment = new WordsFragment().setView(this);
                break;
            }
            case R.id.nav_results: {
                fragment = new ResultsFragment().setView(this);
                break;
            }
        }

        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
        //item.setChecked(true);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void nfcInit() {
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (mNfcAdapter != null) {
            mPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
            IntentFilter ndef = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
            try {
                ndef.addDataType("*/*");
            } catch (IntentFilter.MalformedMimeTypeException e) {
                throw new RuntimeException("fail", e);
            }
            mFilters = new IntentFilter[] {ndef, };
            mTechLists = new String[][] { new String[] { MifareUltralight.class.getName() } };
        }
    }

    private void enableNfc() {
        if (mNfcAdapter != null) mNfcAdapter.enableForegroundDispatch(this, mPendingIntent, mFilters, mTechLists);
        Log.i(TAG, "NFC read enabled");
    }

    private void disableNfc() {
        if (mNfcAdapter != null) mNfcAdapter.disableForegroundDispatch(this);
        Log.i(TAG, "NFC read disabled");
    }
}
