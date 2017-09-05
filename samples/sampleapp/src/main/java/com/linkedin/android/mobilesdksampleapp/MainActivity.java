package com.linkedin.android.mobilesdksampleapp;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.linkedin.platform.LISession;
import com.linkedin.platform.LISessionManager;
import com.linkedin.platform.errors.LIAuthError;
import com.linkedin.platform.listeners.AuthListener;
import com.linkedin.platform.utils.Scope;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends Activity {
  public static final String PACKAGE_MOBILE_SDK_SAMPLE_APP = "com.linkedin.android.mobilesdksampleapp";
  private static final String TAG = MainActivity.class.getSimpleName();

  private static Scope buildScope() {
    return Scope.build(Scope.R_BASICPROFILE, Scope.R_EMAILADDRESS, Scope.W_SHARE);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    final Activity thisActivity = this;
    setUpdateState();

    //Initialize session
    Button liLoginButton = (Button) findViewById(R.id.login_li_button);
    liLoginButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        LISessionManager.getInstance(getApplicationContext()).init(thisActivity, buildScope(), new AuthListener() {
          @Override
          public void onAuthSuccess() {
            setUpdateState();
            final String token =
                LISessionManager.getInstance(getApplicationContext()).getSession().getAccessToken().toString();
            Toast.makeText(getApplicationContext(), "success" + token, Toast.LENGTH_LONG).show();
          }

          @Override
          public void onAuthError(LIAuthError error) {
            setUpdateState();
            ((TextView) findViewById(R.id.at)).setText(error.toString());
            Toast.makeText(getApplicationContext(), "failed " + error.toString(), Toast.LENGTH_LONG).show();
          }
        }, true);
      }
    });

    //Clear session
    Button liForgetButton = (Button) findViewById(R.id.logout_li_button);
    liForgetButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        LISessionManager.getInstance(getApplicationContext()).clearSession();
        setUpdateState();
      }
    });

    //Go to API calls page
    Button liApiCallButton = (Button) findViewById(R.id.apiCall);
    liApiCallButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, ApiActivity.class);
        startActivity(intent);
      }
    });

    //Go to DeepLink page
    Button liDeepLinkButton = (Button) findViewById(R.id.deeplink);
    liDeepLinkButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, DeepLinkActivity.class);
        startActivity(intent);
      }
    });

    //Compute application package and hash
    Button liShowPckHashButton = (Button) findViewById(R.id.showPckHash);
    liShowPckHashButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        try {
          PackageInfo info = getPackageManager().getPackageInfo(
              PACKAGE_MOBILE_SDK_SAMPLE_APP,
              PackageManager.GET_SIGNATURES);
          for (Signature signature : info.signatures) {
            MessageDigest md = MessageDigest.getInstance("SHA");
            md.update(signature.toByteArray());

            ((TextView) findViewById(R.id.pckText)).setText(info.packageName);
            ((TextView) findViewById(R.id.pckHashText)).setText(Base64.encodeToString(md.digest(), Base64.NO_WRAP));
          }
        } catch (PackageManager.NameNotFoundException e) {
          Log.d(TAG, e.getMessage(), e);
        } catch (NoSuchAlgorithmException e) {
          Log.d(TAG, e.getMessage(), e);
        }
      }
    });
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    LISessionManager.getInstance(getApplicationContext()).onActivityResult(this, requestCode, resultCode, data);
  }

  private void setUpdateState() {
    LISessionManager sessionManager = LISessionManager.getInstance(getApplicationContext());
    LISession session = sessionManager.getSession();
    boolean accessTokenValid = session.isValid();

    ((TextView) findViewById(R.id.at)).setText(
        accessTokenValid ? session.getAccessToken().toString() : "Sync with LinkedIn to enable these buttons");
    ((Button) findViewById(R.id.apiCall)).setEnabled(accessTokenValid);
    ((Button) findViewById(R.id.deeplink)).setEnabled(accessTokenValid);
  }
}
