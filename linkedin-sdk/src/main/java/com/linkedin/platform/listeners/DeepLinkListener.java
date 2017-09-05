package com.linkedin.platform.listeners;

import com.linkedin.platform.errors.LIDeepLinkError;

public interface DeepLinkListener {

  void onDeepLinkSuccess();

  void onDeepLinkError(LIDeepLinkError error);
}

