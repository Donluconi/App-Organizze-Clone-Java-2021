package com.example.organizze.activity.helper;

import java.text.SimpleDateFormat;

public class DateCustom {

    public static String dataAtual(){

        //currentTimeMillis recuperar a data atual do tipo long.
        long data = System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dataString = simpleDateFormat.format(data);
        return dataString;
    }

    public static String mesAnoDataEscolhida(String data){
        //23/01/2018 método split recebe um parametro e separa
        String retornoData[] = data.split("/");
        String dia = retornoData[0]; // dia 23
        String mes = retornoData[1]; // mes 01
        String ano = retornoData[2]; //ano 2018 - 012018

        String mesAno = mes + ano;
        return mesAno;
    }
}
