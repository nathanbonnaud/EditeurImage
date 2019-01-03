/*
 * Copyright (C) 2011-2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * This file is auto-generated. DO NOT MODIFY!
 * The source Renderscript file: D:\\Projet techno\\app\\src\\main\\rs\\contrasteD.rs
 */

package com.android.rssample;

import android.support.v8.renderscript.*;
import android.content.res.Resources;

/**
 * @hide
 */
public class ScriptC_contrasteD extends ScriptC {
    private static final String __rs_resource_name = "contrasted";
    // Constructor
    public  ScriptC_contrasteD(RenderScript rs) {
        this(rs,
             rs.getApplicationContext().getResources(),
             rs.getApplicationContext().getResources().getIdentifier(
                 __rs_resource_name, "raw",
                 rs.getApplicationContext().getPackageName()));
    }

    public  ScriptC_contrasteD(RenderScript rs, Resources resources, int id) {
        super(rs, resources, id);
        mExportVar_minred = 255.f;
        __F32 = Element.F32(rs);
        mExportVar_mingreen = 255.f;
        mExportVar_minblue = 255.f;
        mExportVar_maxred = 0.f;
        mExportVar_maxgreen = 0.f;
        mExportVar_maxblue = 0.f;
        __U8_4 = Element.U8_4(rs);
    }

    private Element __F32;
    private Element __U8_4;
    private FieldPacker __rs_fp_F32;
    private final static int mExportVarIdx_minred = 0;
    private float mExportVar_minred;
    public synchronized void set_minred(float v) {
        setVar(mExportVarIdx_minred, v);
        mExportVar_minred = v;
    }

    public float get_minred() {
        return mExportVar_minred;
    }

    public Script.FieldID getFieldID_minred() {
        return createFieldID(mExportVarIdx_minred, null);
    }

    private final static int mExportVarIdx_mingreen = 1;
    private float mExportVar_mingreen;
    public synchronized void set_mingreen(float v) {
        setVar(mExportVarIdx_mingreen, v);
        mExportVar_mingreen = v;
    }

    public float get_mingreen() {
        return mExportVar_mingreen;
    }

    public Script.FieldID getFieldID_mingreen() {
        return createFieldID(mExportVarIdx_mingreen, null);
    }

    private final static int mExportVarIdx_minblue = 2;
    private float mExportVar_minblue;
    public synchronized void set_minblue(float v) {
        setVar(mExportVarIdx_minblue, v);
        mExportVar_minblue = v;
    }

    public float get_minblue() {
        return mExportVar_minblue;
    }

    public Script.FieldID getFieldID_minblue() {
        return createFieldID(mExportVarIdx_minblue, null);
    }

    private final static int mExportVarIdx_maxred = 3;
    private float mExportVar_maxred;
    public synchronized void set_maxred(float v) {
        setVar(mExportVarIdx_maxred, v);
        mExportVar_maxred = v;
    }

    public float get_maxred() {
        return mExportVar_maxred;
    }

    public Script.FieldID getFieldID_maxred() {
        return createFieldID(mExportVarIdx_maxred, null);
    }

    private final static int mExportVarIdx_maxgreen = 4;
    private float mExportVar_maxgreen;
    public synchronized void set_maxgreen(float v) {
        setVar(mExportVarIdx_maxgreen, v);
        mExportVar_maxgreen = v;
    }

    public float get_maxgreen() {
        return mExportVar_maxgreen;
    }

    public Script.FieldID getFieldID_maxgreen() {
        return createFieldID(mExportVarIdx_maxgreen, null);
    }

    private final static int mExportVarIdx_maxblue = 5;
    private float mExportVar_maxblue;
    public synchronized void set_maxblue(float v) {
        setVar(mExportVarIdx_maxblue, v);
        mExportVar_maxblue = v;
    }

    public float get_maxblue() {
        return mExportVar_maxblue;
    }

    public Script.FieldID getFieldID_maxblue() {
        return createFieldID(mExportVarIdx_maxblue, null);
    }

    private final static int mExportVarIdx_Lutred = 6;
    private float[] mExportVar_Lutred;
    public synchronized void set_Lutred(float[] v) {
        mExportVar_Lutred = v;
        FieldPacker fp = new FieldPacker(1024);
        for (int ct1 = 0; ct1 < 256; ct1++) {
            fp.addF32(v[ct1]);
        }

        int []__dimArr = new int[1];
        __dimArr[0] = 256;
        setVar(mExportVarIdx_Lutred, fp, __F32, __dimArr);
    }

    public float[] get_Lutred() {
        return mExportVar_Lutred;
    }

    public Script.FieldID getFieldID_Lutred() {
        return createFieldID(mExportVarIdx_Lutred, null);
    }

    private final static int mExportVarIdx_Lutgreen = 7;
    private float[] mExportVar_Lutgreen;
    public synchronized void set_Lutgreen(float[] v) {
        mExportVar_Lutgreen = v;
        FieldPacker fp = new FieldPacker(1024);
        for (int ct1 = 0; ct1 < 256; ct1++) {
            fp.addF32(v[ct1]);
        }

        int []__dimArr = new int[1];
        __dimArr[0] = 256;
        setVar(mExportVarIdx_Lutgreen, fp, __F32, __dimArr);
    }

    public float[] get_Lutgreen() {
        return mExportVar_Lutgreen;
    }

    public Script.FieldID getFieldID_Lutgreen() {
        return createFieldID(mExportVarIdx_Lutgreen, null);
    }

    private final static int mExportVarIdx_Lutblue = 8;
    private float[] mExportVar_Lutblue;
    public synchronized void set_Lutblue(float[] v) {
        mExportVar_Lutblue = v;
        FieldPacker fp = new FieldPacker(1024);
        for (int ct1 = 0; ct1 < 256; ct1++) {
            fp.addF32(v[ct1]);
        }

        int []__dimArr = new int[1];
        __dimArr[0] = 256;
        setVar(mExportVarIdx_Lutblue, fp, __F32, __dimArr);
    }

    public float[] get_Lutblue() {
        return mExportVar_Lutblue;
    }

    public Script.FieldID getFieldID_Lutblue() {
        return createFieldID(mExportVarIdx_Lutblue, null);
    }

    //private final static int mExportForEachIdx_root = 0;
    private final static int mExportForEachIdx_GetMinMaxColor = 1;
    public Script.KernelID getKernelID_GetMinMaxColor() {
        return createKernelID(mExportForEachIdx_GetMinMaxColor, 33, null, null);
    }

    public void forEach_GetMinMaxColor(Allocation ain) {
        forEach_GetMinMaxColor(ain, null);
    }

    public void forEach_GetMinMaxColor(Allocation ain, Script.LaunchOptions sc) {
        // check ain
        if (!ain.getType().getElement().isCompatible(__U8_4)) {
            throw new RSRuntimeException("Type mismatch with U8_4!");
        }
        forEach(mExportForEachIdx_GetMinMaxColor, ain, null, null, sc);
    }

    private final static int mExportForEachIdx_Final = 2;
    public Script.KernelID getKernelID_Final() {
        return createKernelID(mExportForEachIdx_Final, 35, null, null);
    }

    public void forEach_Final(Allocation ain, Allocation aout) {
        forEach_Final(ain, aout, null);
    }

    public void forEach_Final(Allocation ain, Allocation aout, Script.LaunchOptions sc) {
        // check ain
        if (!ain.getType().getElement().isCompatible(__U8_4)) {
            throw new RSRuntimeException("Type mismatch with U8_4!");
        }
        // check aout
        if (!aout.getType().getElement().isCompatible(__U8_4)) {
            throw new RSRuntimeException("Type mismatch with U8_4!");
        }
        Type t0, t1;        // Verify dimensions
        t0 = ain.getType();
        t1 = aout.getType();
        if ((t0.getCount() != t1.getCount()) ||
            (t0.getX() != t1.getX()) ||
            (t0.getY() != t1.getY()) ||
            (t0.getZ() != t1.getZ()) ||
            (t0.hasFaces()   != t1.hasFaces()) ||
            (t0.hasMipmaps() != t1.hasMipmaps())) {
            throw new RSRuntimeException("Dimension mismatch between parameters ain and aout!");
        }

        forEach(mExportForEachIdx_Final, ain, aout, null, sc);
    }

    private final static int mExportFuncIdx_createlutred = 0;
    public void invoke_createlutred() {
        invoke(mExportFuncIdx_createlutred);
    }

    private final static int mExportFuncIdx_createlutgreen = 1;
    public void invoke_createlutgreen() {
        invoke(mExportFuncIdx_createlutgreen);
    }

    private final static int mExportFuncIdx_createlutblue = 2;
    public void invoke_createlutblue() {
        invoke(mExportFuncIdx_createlutblue);
    }

}

