package com.example.organizze.activity.helper;

import android.util.Base64;

import java.io.UnsupportedEncodingException;

public class Base64Custom {

    public static String codificarBase64(String texto)
    {           //replaceALL substituir A por O por exemplo em "Laranja"
     // return Base64.encodeToString(texto.getBytes(), Base64.DEFAULT).replaceAll("(\\n|\\r)", "");

        byte[] data =null;

        try{
            data =texto.getBytes("UTF-8");

        }catch (UnsupportedEncodingException e){

            e.printStackTrace();

        }

        return Base64.encodeToString(data,Base64.NO_WRAP);

    }



    public static String decodificarBase64(String textoCodificado)
    {
     //return new String(Base64.decode(textoCodificado, Base64.DEFAULT));
        byte [] dataDec = Base64.decode(textoCodificado,Base64.DEFAULT);

        String decodeString ="";

        try{

            decodeString = new String(dataDec,"UTF-8");

        }catch (UnsupportedEncodingException e){

            e.printStackTrace();

        }finally {

            return  decodeString;

        }

    }
}
