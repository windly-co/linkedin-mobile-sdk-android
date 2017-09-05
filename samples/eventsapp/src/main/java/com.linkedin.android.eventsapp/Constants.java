package com.linkedin.android.eventsapp;

import com.linkedin.platform.utils.Scope;

public class Constants {
  public static final String personByIdBaseUrl = "https://api.linkedin.com/v1/people/id=";
  public static final String shareBaseUrl = "https://api.linkedin.com/v1/people/~/shares";
  public static final String personProjection = ":(first-name,last-name,location,headline,picture-url)";
  public static final Scope scope = Scope.build(Scope.R_BASICPROFILE, Scope.W_SHARE);
}
