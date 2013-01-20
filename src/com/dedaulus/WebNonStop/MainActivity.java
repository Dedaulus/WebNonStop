package com.dedaulus.WebNonStop;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

public class MainActivity extends Activity implements SetUrlDialogFragment.SetUrlDialogListener, SetUrlDialogFragment.SetUrlDialogInitializer {
    private static final String PREFERENCES_FILE = "preferences";
    private static final String PREF_URL = "url";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        WebView webView = (WebView)findViewById(R.id.web);
        webView.getSettings().setJavaScriptEnabled(true);
        // prevents opening links in installed web browser
        webView.setWebViewClient(new WebViewClient());

        setHomeUrl(getUrlFromPreferences());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.url_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_url:
                showSetUrlDialog();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setHomeUrl(String url) {
        saveUrlToPreferences(url);

        WebView webView = (WebView)findViewById(R.id.web);
        webView.loadUrl(url);
    }

    public void showSetUrlDialog() {
        DialogFragment dialogFragment = new SetUrlDialogFragment();
        dialogFragment.show(getFragmentManager(), "SetUrlDialogFragment");
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        EditText editText = (EditText)dialog.getDialog().findViewById(R.id.url);
        String url = editText.getText().toString();
        if (!url.isEmpty() && !url.startsWith("http://")) {
            url = "http://" + url;
        }
        setHomeUrl(url);
    }

    private void saveUrlToPreferences(String url) {
        SharedPreferences preferences = getSharedPreferences(PREFERENCES_FILE, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREF_URL, url);
        editor.commit();
    }

    private String getUrlFromPreferences() {
        SharedPreferences preferences = getSharedPreferences(PREFERENCES_FILE, MODE_PRIVATE);
        return preferences.getString(PREF_URL, "");
    }

    @Override
    public String getUrl() {
        return getUrlFromPreferences();
    }
}
