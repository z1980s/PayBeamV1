package info.paybeam.www.paybeamv1.PayBeam.SecurityModule;

import android.content.Context;

import javax.crypto.Cipher;


import java.io.InputStream;
import java.security.*;
import java.security.cert.X509Certificate;

public class RSA 
{
    InputStream stream;
    KeyStore trustStore;
    PublicKey publicKey;

    public RSA(Context context) throws Exception {
        stream = context.getAssets().open("www_paybeam_info.bks");

        trustStore = KeyStore.getInstance("BKS");
        trustStore.load(stream, "Se4wyhv8@".toCharArray());
        String alias = "bank_cert";
        X509Certificate cert = (X509Certificate)trustStore.getCertificate(alias);
        publicKey = cert.getPublicKey();
    }

    public byte[] encrypt(byte[] inputData) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.PUBLIC_KEY, publicKey);

        byte[] encrypted  = cipher.doFinal(inputData);
        return encrypted;
    }
}
