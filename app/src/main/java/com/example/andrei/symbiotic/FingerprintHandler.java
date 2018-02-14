package com.example.andrei.symbiotic;

/**
 * Created by Andrei on 2/14/2018.
 */

/*
 * Copyright (C) 2016 Surviving with Android (http://www.survivingwithandroid.com)
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

import android.content.Context;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.support.annotation.RequiresApi;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;

/**
 * Created by francesco on 29/11/16.
 */

@RequiresApi(api = Build.VERSION_CODES.M)
public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {

    private Context c;

    public FingerprintHandler(Context c) {
        this.c = c;


    }

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {
        super.onAuthenticationError(errorCode, errString);
        Toast.makeText(c, "Auth ERROR.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
        super.onAuthenticationHelp(helpCode, helpString);

    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        super.onAuthenticationSucceeded(result);

        Signature sig = result.getCryptoObject().getSignature();


        Toast.makeText(c, "Authenticated!", Toast.LENGTH_SHORT).show();


        Intent intent = new Intent(c, Home.class);
        String userId = "id";
        intent.putExtra("id", userId);
        c.startActivity(intent);

    }

    @Override
    public void onAuthenticationFailed() {
        super.onAuthenticationFailed();

        Toast.makeText(c, "Auth Failed!", Toast.LENGTH_SHORT).show();
    }

    public void doAuth(FingerprintManager manager, FingerprintManager.CryptoObject obj) {

        CancellationSignal signal = new CancellationSignal();

        Toast.makeText(c, "Scan your finger!", Toast.LENGTH_SHORT).show();

        try {
            manager.authenticate(obj, signal, 0, this, null);
        } catch (SecurityException sce) {

        }
    }
}

@RequiresApi(api = Build.VERSION_CODES.M)
class FingerprintHandlerRegister extends FingerprintManager.AuthenticationCallback {

    private Context c;

    public FingerprintHandlerRegister(Context c) {
        this.c = c;
    }

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {
        super.onAuthenticationError(errorCode, errString);
        Toast.makeText(c, "Auth ERROR.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
        super.onAuthenticationHelp(helpCode, helpString);

    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        super.onAuthenticationSucceeded(result);

        Toast.makeText(c, "User Registered!", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(c, Home.class);
        String userId = "id";
        intent.putExtra("id", userId);
        c.startActivity(intent);

    }

    @Override
    public void onAuthenticationFailed() {
        super.onAuthenticationFailed();

    }

    public void doAuth(FingerprintManager manager, FingerprintManager.CryptoObject obj) {


        CancellationSignal signal = new CancellationSignal();

        Toast.makeText(c, "Scan your finger!", Toast.LENGTH_SHORT).show();

        try {
            manager.authenticate(obj, signal, 0, this, null);
        } catch (SecurityException sce) {
        }
    }
}