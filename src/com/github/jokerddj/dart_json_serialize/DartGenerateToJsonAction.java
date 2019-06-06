// Copyright 2000-2019 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.github.jokerddj.dart_json_serialize;

import com.jetbrains.lang.dart.ide.generation.BaseDartGenerateAction;
import com.jetbrains.lang.dart.ide.generation.BaseDartGenerateHandler;
import com.jetbrains.lang.dart.psi.DartClass;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DartGenerateToJsonAction extends BaseDartGenerateAction {

  private static final String FROM_JSON = "fromJson";
  private static final String TO_JSON = "toJson";


  @NotNull
  @Override
  protected BaseDartGenerateHandler getGenerateHandler() {
    return new DartGenerateToJsonGenerateHandler();
  }

  @Override
  protected boolean doEnable(@Nullable DartClass dartClass) {
    if (dartClass == null) {
      return false;
    }
    return true;
  }
}
