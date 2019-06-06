// Copyright 2000-2019 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.github.jokerddj.dart_json_serialize;

import com.intellij.codeInsight.template.Template;
import com.intellij.codeInsight.template.TemplateManager;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.jetbrains.lang.dart.ide.generation.BaseCreateMethodsFix;
import com.jetbrains.lang.dart.psi.DartClass;
import com.jetbrains.lang.dart.psi.DartComponent;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.Set;

public class DartGenerateToJsonStringFix extends BaseCreateMethodsFix<DartComponent> {

  public DartGenerateToJsonStringFix(@NotNull DartClass dartClass) {
    super(dartClass);
  }


  @Override
  protected void processElements(@NotNull final Project project,
                                 @NotNull final Editor editor,
                                 @NotNull final Set<DartComponent> elementsToProcess) {
    final TemplateManager templateManager = TemplateManager.getInstance(project);
    anchor = doAddMethodsForOne(editor, templateManager, buildFunctionsText(templateManager, elementsToProcess), anchor);
  }

  @Override
  @NotNull
  protected String getNothingFoundMessage() {
    return ""; // can't be called actually because processElements() is overridden
  }

  protected Template buildFunctionsText(TemplateManager templateManager, Set<DartComponent> elementsToProcess) {
    final Template template = templateManager.createTemplate(getClass().getName(), DART_TEMPLATE_GROUP);
    template.setToReformat(true);

    //      User.fromJson(Map<String, dynamic> json)
    //      : name = json['name'],
    //        email = json['email'];

    template.addTextSegment(myDartClass.getName() + ".fromJson(Map<String, dynamic> json)\n");
    template.addTextSegment(": ");
    boolean first = true;
    //  fromJson
    for (Iterator<DartComponent> iterator = elementsToProcess.iterator(); iterator.hasNext(); ) {
      DartComponent component = iterator.next();

      if (first) {
        template.addTextSegment(component.getName());
        template.addTextSegment(" = json['");
        template.addTextSegment(component.getName());
        template.addTextSegment("']");
        first = false;
      }
      else {
        template.addTextSegment(",\n");
        template.addTextSegment(component.getName());
        template.addTextSegment(" = json['");
        template.addTextSegment(component.getName());
        template.addTextSegment("']");
      }
      if (!iterator.hasNext()) {
        template.addTextSegment(";\n");
      }

    }


    //  toJson
    //
    //  Map<String, dynamic> toJson() =>
    //    {
    //      'name': name,
    //      'email': email,
    //    };

    template.addTextSegment("Map<String, dynamic> toJson() => {\n");

    for (Iterator<DartComponent> iterator = elementsToProcess.iterator(); iterator.hasNext(); ) {

      //  'name': name,
      DartComponent component = iterator.next();

      template.addTextSegment("  '" + component.getName() + "'" + ":");
      template.addTextSegment(component.getName());
      template.addTextSegment(",\n");
    }
    template.addTextSegment("};\n");

    return template;
  }

  @Override
  protected Template buildFunctionsText(TemplateManager templateManager, DartComponent e) {
    // ignore
    return null;
  }
}
