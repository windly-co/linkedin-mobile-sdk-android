package com.linkedin.android.mobilesdksampleapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.linkedin.platform.DeepLinkHelper;
import com.linkedin.platform.errors.LIDeepLinkError;
import com.linkedin.platform.listeners.DeepLinkListener;

public class DeepLinkActivity extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_deeplink);

    //Opens other LinkedIn member's profile.
    Button liOpenProfileButton = (Button) findViewById(R.id.deeplink_open_profile);
    liOpenProfileButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String profileId = ((EditText) findViewById(R.id.deeplink_profile_id)).getText().toString();

        DeepLinkHelper deepLinkHelper = DeepLinkHelper.getInstance();
        deepLinkHelper.openOtherProfile(DeepLinkActivity.this, profileId, new DeepLinkListener() {
          @Override
          public void onDeepLinkSuccess() {
            ((TextView) findViewById(R.id.deeplink_error)).setText("");
          }

          @Override
          public void onDeepLinkError(LIDeepLinkError error) {
            ((TextView) findViewById(R.id.deeplink_error)).setText(error.toString());
          }
        });
      }
    });

    //Opens current logged in member's profile
    Button liMyProfileButton = (Button) findViewById(R.id.myProfile);
    liMyProfileButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        DeepLinkHelper deepLinkHelper = DeepLinkHelper.getInstance();
        deepLinkHelper.openCurrentProfile(DeepLinkActivity.this, new DeepLinkListener() {
          @Override
          public void onDeepLinkSuccess() {
            ((TextView) findViewById(R.id.deeplink_error)).setText("");
          }

          @Override
          public void onDeepLinkError(LIDeepLinkError error) {
            ((TextView) findViewById(R.id.deeplink_error)).setText(error.toString());
          }
        });
      }
    });
  }

  //To get call back
  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    DeepLinkHelper deepLinkHelper = DeepLinkHelper.getInstance();
    deepLinkHelper.onActivityResult(this, requestCode, resultCode, data);
  }
}
