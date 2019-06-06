// Copyright 2000-2019 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.github.jokerddj.dart_json_serialize;

import com.intellij.util.containers.ContainerUtil;
import com.jetbrains.lang.dart.DartComponentType;
import com.jetbrains.lang.dart.ide.generation.BaseCreateMethodsFix;
import com.jetbrains.lang.dart.ide.generation.BaseDartGenerateHandler;
import com.jetbrains.lang.dart.psi.DartClass;
import com.jetbrains.lang.dart.psi.DartComponent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DartGenerateToJsonGenerateHandler extends BaseDartGenerateHandler {
    @NotNull
    @Override
    protected BaseCreateMethodsFix createFix(@NotNull DartClass dartClass) {
        return new DartGenerateToJsonStringFix(dartClass);
    }

    @NotNull
    @Override
    protected String getTitle() {
        return  "toJson";
    }

    @Override
    protected void collectCandidates(@NotNull DartClass dartClass, @NotNull List<DartComponent> candidates) {
        candidates.addAll(ContainerUtil.findAll(computeClassMembersMap(dartClass, false).values(),
                component -> DartComponentType.typeOf(component) == DartComponentType.FIELD));
    }
}
