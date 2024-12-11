package roshan.dhimal.rdhima2_project_3;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import androidx.fragment.app.Fragment;

public class FragmentTwo extends Fragment {

    private static final String ARG_URL = "url";

    public static FragmentTwo newInstance(String url) {
        FragmentTwo fragment = new FragmentTwo();
        Bundle args = new Bundle();
        args.putString(ARG_URL, url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_two, container, false);
        WebView webView = view.findViewById(R.id.webview);
        if (getArguments() != null && getArguments().containsKey(ARG_URL)) {
            String url = getArguments().getString(ARG_URL);
            webView.loadUrl(url);
        }
        return view;
    }

    public void updateWebView(String url) {
        View view = getView();
        if (view != null) {
            WebView webView = view.findViewById(R.id.webview);
            webView.loadUrl(url);
        }
    }
}
