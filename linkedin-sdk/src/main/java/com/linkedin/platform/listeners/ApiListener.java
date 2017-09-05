package com.linkedin.platform.listeners;

import com.linkedin.platform.errors.LIApiError;

public interface ApiListener {

  void onApiSuccess(ApiResponse apiResponse);

  void onApiError(LIApiError LIApiError);
}

