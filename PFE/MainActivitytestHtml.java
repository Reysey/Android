package com.reydev.tuto.androiddatabase;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.codesgood.views.JustifiedTextView;

public class MainActivitytestHtml extends AppCompatActivity {
       TextView textView;
       WebView webView;
       JustifiedTextView justifiedTextView;

       String dummy_introduction;
       String dummy_1, dummy_2, dummy_3, dummy_4, dummy_5, dummy_6, dummy_7, dummy_8,dummy_88, dummy_9;
    String dummy_10, dummy_11, dummy_12, dummy_13, dummy_14, dummy_15, dummy_16, dummy_17;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activitytest_html);
        getSupportActionBar().setTitle("Comment avoir un bon IMC");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //textView = findViewById(R.id.text_view);
        webView = findViewById(R.id.web_view);
        justifiedTextView =findViewById(R.id.justified_text_view);

        dummy_introduction = getResources().getString(R.string.conseil_web_introduction);
        dummy_1 = getResources().getString(R.string.conseil_web_1);
        dummy_2 = getResources().getString(R.string.conseil_web_2);
        dummy_3 = getResources().getString(R.string.conseil_web_3);
        dummy_4 = getResources().getString(R.string.conseil_web_4);
        dummy_5 = getResources().getString(R.string.conseil_web_5);
        dummy_6 = getResources().getString(R.string.conseil_web_6);
        dummy_7 = getResources().getString(R.string.conseil_web_7);
        dummy_8 = getResources().getString(R.string.conseil_web_8);
        dummy_88 = getResources().getString(R.string.conseil_web_88);
        dummy_9 = getResources().getString(R.string.conseil_web_9);
        dummy_10 = getResources().getString(R.string.conseil_web_10);
        dummy_11 = getResources().getString(R.string.conseil_web_11);
        dummy_12 = getResources().getString(R.string.conseil_web_12);
        dummy_13 = getResources().getString(R.string.conseil_web_13);
        dummy_14 = getResources().getString(R.string.conseil_web_14);
        dummy_15 = getResources().getString(R.string.conseil_web_15);
        dummy_16 = getResources().getString(R.string.conseil_web_16);
        dummy_17 = getResources().getString(R.string.conseil_web_17);
       // textView.setText(dummyText);
        String webText = String.valueOf(Html.fromHtml("<![CDATA[<body style=\" text-align:justify \">"+" &nbsp;"+dummy_introduction+"<br/>"+dummy_1+"<br/>"+dummy_2+"<br/>"+dummy_3+"<br/>"+dummy_4+"<br/>"+dummy_5+"<br/>"+dummy_6+"<br/>"+dummy_7+"<br/>"+dummy_8+"<br/><mark>"+dummy_88+"</mark><br/>"+dummy_9+"<br/>"+dummy_10+"<br/>"+dummy_11+"<br/>"+dummy_12+"<br/><mark>"+dummy_13+"</mark><br/>"+dummy_14+"<br/>"+dummy_15+"<br/>"+dummy_16+"<br/>"+dummy_17+"</body>]]>"));
        webView.setBackgroundColor(Color.TRANSPARENT);
        webView.loadData(webText,"text/html;charset=utf-8","UTF-8");
        justifiedTextView.setText(Html.fromHtml(webText));

        getIntent();
    }
}