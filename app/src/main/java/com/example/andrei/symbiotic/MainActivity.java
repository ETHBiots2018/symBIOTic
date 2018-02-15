package com.example.andrei.symbiotic;

import android.app.KeyguardManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.support.annotation.RequiresApi;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class MainActivity extends AppCompatActivity {

    private static final String KEY_NAME = "SwA";

    private KeyStore keyStore;
    private KeyGenerator keyGenerator;
    private FingerprintManager.CryptoObject cryptoObject;

    private FingerprintManager fingerprintManager;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageView image = findViewById(R.id.main_image);
        image.setBackgroundResource(R.drawable.logo);


        /* Buttons */

        final Button registerButton = findViewById(R.id.button_register);
        final Button loginButton = findViewById(R.id.button_login);

        /* END Buttons */


        /* Shared pref - use this only once */
        SharedPreferences.Editor editor = getSharedPreferences("users", MODE_PRIVATE).edit();
        editor.putString("0", "0x5ba28f28aa3113df222858ad69f9b618f2b5bcf0");
        editor.putString("1", "0x5ba28f28aa3113df222858ad69f9b618f2b5bcf0");
        editor.apply();



        /* Fingerprint */
        final FingerprintHandler fph;
        fph = new FingerprintHandler(getApplicationContext(), image);

        final FingerprintHandlerRegister fphRegister;
        fphRegister = new FingerprintHandlerRegister(getApplicationContext(), image);

        if (checkFinger()) {
            // We are ready to set up the cipher and the key

            try {
                generateKey();
                Cipher cipher = generateCipher();
                cryptoObject =
                            new FingerprintManager.CryptoObject(cipher);

            } catch (FingerprintException fpe) {
                // Handle exception
                loginButton.setEnabled(false);
            }
        }


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image.setBackgroundResource(R.drawable.finger_grey);
                fph.doAuth(fingerprintManager, cryptoObject);
            }
        });


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image.setBackgroundResource(R.drawable.finger_grey);
                fphRegister.doAuth(fingerprintManager, cryptoObject);
            }
        });

    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean checkFinger() {

        // Keyguard Manager
        KeyguardManager keyguardManager = (KeyguardManager)
                getSystemService(KEYGUARD_SERVICE);

        // Fingerprint Manager
        fingerprintManager = (FingerprintManager)
                getSystemService(FINGERPRINT_SERVICE);

        FingerprintManagerCompat fingerprintManagerCompat = FingerprintManagerCompat.from(getApplicationContext());


        try {
            // Check if the fingerprint sensor is present
                if (!fingerprintManager.isHardwareDetected()) {
                    // Update the UI with a message
                    Toast.makeText(getApplicationContext(), "Fingerprint authentication not supported", Toast.LENGTH_SHORT).show();
                    return false;
                }


                if (!fingerprintManager.hasEnrolledFingerprints()) {
                    Toast.makeText(getApplicationContext(), "No fingerprint configured.", Toast.LENGTH_SHORT).show();
                    return false;
                }


                if (!keyguardManager.isKeyguardSecure()) {
                    Toast.makeText(getApplicationContext(), "Secure lock screen not enabled", Toast.LENGTH_SHORT).show();
                    return false;
                }

        } catch (SecurityException se) {
            se.printStackTrace();
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void generateKey() throws FingerprintException {
        try {
            // Get the reference to the key store
            keyStore = KeyStore.getInstance("AndroidKeyStore");

            // Key generator to generate the key
            keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");

            keyStore.load(null);
            keyGenerator.init(new
                    KeyGenParameterSpec.Builder(KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT |
                            KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(
                            KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build());

            keyGenerator.generateKey();

        } catch (KeyStoreException
                | NoSuchAlgorithmException
                | NoSuchProviderException
                | InvalidAlgorithmParameterException
                | CertificateException
                | IOException exc) {
            exc.printStackTrace();
            throw new FingerprintException(exc);
        }


    }

    private Cipher generateCipher() throws FingerprintException {
        try {
            Cipher cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/"
                    + KeyProperties.BLOCK_MODE_CBC + "/"
                    + KeyProperties.ENCRYPTION_PADDING_PKCS7);
            SecretKey key = (SecretKey) keyStore.getKey(KEY_NAME,
                    null);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return cipher;
        } catch (NoSuchAlgorithmException
                | NoSuchPaddingException
                | InvalidKeyException
                | UnrecoverableKeyException
                | KeyStoreException exc) {
            exc.printStackTrace();
            throw new FingerprintException(exc);
        }
    }

    private class FingerprintException extends Exception {

        public FingerprintException(Exception e) {
            super(e);
        }
    }


}
