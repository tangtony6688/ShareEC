package com.tony.brown.compiler;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.SimpleAnnotationValueVisitor7;

/**
 * Created by Tony on 2017/12/14.
 */

public final class AppRegisterVisitor extends SimpleAnnotationValueVisitor7<Void, Void> {
    private Filer mFiler = null;
    private String mPackageName = null;
    private TypeMirror mTypeMirror = null;

    public void setFiler(Filer filer) {
        this.mFiler = filer;
    }

    @Override
    public Void visitString(String s, Void aVoid) {
        mPackageName = s;
        return aVoid;
    }

    @Override
    public Void visitType(TypeMirror typeMirror, Void aVoid) {
        mTypeMirror = typeMirror;
        generateJavaCode();
        return aVoid;
    }

    private void generateJavaCode() {
        final TypeSpec targetActivity =
                TypeSpec.classBuilder("AppRegister")
                        .addModifiers(Modifier.PUBLIC)
                        .addModifiers(Modifier.FINAL)
                        .superclass(TypeName.get(mTypeMirror))
                        .build();

        final JavaFile javaFile = JavaFile.builder(mPackageName + ".wxapi", targetActivity)
                .addFileComment("微信广播接收器")
                .build();

        try {
            javaFile.writeTo(mFiler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
