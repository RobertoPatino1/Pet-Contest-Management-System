
package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;

public class UtilDate {
  //retorna un tipo date a partir del string recibido
    public static Date getDateFromString(String strDate){
        try {  
            return new SimpleDateFormat("yyyy-MM-dd").parse(strDate);
        } catch (ParseException ex) {
            System.out.println("Error al convertir");
            return new Date();
        }
    }
     
}
