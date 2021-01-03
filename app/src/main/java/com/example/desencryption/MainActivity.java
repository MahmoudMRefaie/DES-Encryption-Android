package com.example.desencryption;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends AppCompatActivity {

    TextInputLayout plainText;
    TextInputLayout key;
    TextView cipher;
    Button encrypt;
    Button decrypt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        plainText = findViewById(R.id.plainText);
        key = findViewById(R.id.key);
        cipher = findViewById(R.id.cipher);
        encrypt = findViewById(R.id.encrypt);
        decrypt = findViewById(R.id.decrypt);

        encrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(plainText.getEditText().getText().toString().matches("") || key.getEditText().getText().toString().matches("")){
                    App.ToastMaker(getApplicationContext(),"Enter Plain Text And Key");
                }else if(key.getEditText().getText().toString().length() != 8){
                    App.ToastMaker(getApplicationContext(),"Enter 8 Character As KEY");
                }else{
                    cipher.setText(encrypt(plainText.getEditText().getText().toString(),getApplicationContext()));
                }
            }
        });

        decrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(plainText.getEditText().getText().toString().matches("") || key.getEditText().getText().toString().matches("")){
                    App.ToastMaker(getApplicationContext(),"Enter Encrypted Text And Key");
                }else if(key.getEditText().getText().toString().length() != 8){
                    App.ToastMaker(getApplicationContext(),"Enter 8 Character As KEY");
                }else{
                    cipher.setText(decrypt(plainText.getEditText().getText().toString(),getApplicationContext()));
                }
            }
        });
    }

    public String encrypt(String value, Context c) {

        App.Logger("Encrypt Started ...");

        String crypted = "";

        try {
            byte[] cleartext = value.getBytes("UTF-8");

            SecretKeySpec keySec = new SecretKeySpec(key.getEditText().getText().toString().getBytes(), "DES");

            Cipher cipher = Cipher.getInstance("DES/ECB/ZeroBytePadding");

            // Initialize the cipher for decryption
            cipher.init(Cipher.ENCRYPT_MODE, keySec);

            crypted = Base64.encodeToString(cipher.doFinal(cleartext),Base64.DEFAULT);


        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            App.DialogMaker(c,"Encrypt Error","Error" + "\n" + e.getMessage());
            return "Encrypt Error";
        }
        catch (NoSuchPaddingException e) {
            e.printStackTrace();
            App.DialogMaker(c,"Encrypt Error","Error" + "\n" + e.getMessage());
            return "Encrypt Error";
        }
        catch (IllegalBlockSizeException e) {
            e.printStackTrace();
            App.DialogMaker(c,"Encrypt Error","Error" + "\n" + e.getMessage());
            return "Encrypt Error";
        }
        catch (BadPaddingException e) {
            e.printStackTrace();
            App.DialogMaker(c,"Encrypt Error","Error" + "\n" + e.getMessage());
            return "Encrypt Error";
        }
        catch (InvalidKeyException e) {
            e.printStackTrace();
            App.DialogMaker(c,"Encrypt Error","Error" + "\n" + e.getMessage());
            return "Encrypt Error";
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            App.DialogMaker(c,"Encrypt Error","Error" + "\n" + e.getMessage());
            return "Encrypt Error";
        }
        catch (Exception e) {
            e.printStackTrace();
            App.DialogMaker(c,"Encrypt Error","Error" + "\n" + e.getMessage());
            return "Encrypt Error";
        }

        App.Logger("Encrypt Finished ...");

        //return "code==" + crypted;
        return crypted;
    }

    public String decrypt(String value,Context c) {

        App.Logger("Decrypt Started ...");

        String coded;
        if(value.startsWith("code==")){
            coded = value.substring(6,value.length()).trim();
        }else{
            coded = value.trim();
        }

        String result = null;

        try {
            // Decoding base64
            byte[] bytesDecoded = Base64.decode(coded.getBytes("UTF-8"),Base64.DEFAULT);

            SecretKeySpec keySec = new SecretKeySpec(key.getEditText().getText().toString().getBytes(), "DES");

            Cipher cipher = Cipher.getInstance("DES/ECB/ZeroBytePadding");

            // Initialize the cipher for decryption
            cipher.init(Cipher.DECRYPT_MODE, keySec);

            // Decrypt the text
            byte[] textDecrypted = cipher.doFinal(bytesDecoded);

            result = new String(textDecrypted);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            App.DialogMaker(c,"Decrypt Error","Erorr:" + "\n" + e.getMessage());
            return "Decrypt Error";
        }
        catch (NoSuchPaddingException e) {
            e.printStackTrace();
            App.DialogMaker(c,"Decrypt Error","Erorr:" + "\n" + e.getMessage());
            return "Decrypt Error";
        }
        catch (IllegalBlockSizeException e) {
            e.printStackTrace();
            App.DialogMaker(c,"Decrypt Error","Erorr:" + "\n" + e.getMessage());
            return "Decrypt Error";
        }
        catch (BadPaddingException e) {
            e.printStackTrace();
            App.DialogMaker(c,"Decrypt Error","Erorr:" + "\n" + e.getMessage());
            return "Decrypt Error";
        }
        catch (InvalidKeyException e) {
            e.printStackTrace();
            App.DialogMaker(c,"Decrypt Error","Erorr:" + "\n" + e.getMessage());
            return "Decrypt Error";
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            App.DialogMaker(c,"Decrypt Error","Erorr:" + "\n" + e.getMessage());
            return "Decrypt Error";
        }
        catch (Exception e) {
            e.printStackTrace();
            App.DialogMaker(c,"Decrypt Error","Erorr:" + "\n" + e.getMessage());
            return "Decrypt Error";
        }

        App.Logger("Decrypt Finished ...");
        return result;
    }

}