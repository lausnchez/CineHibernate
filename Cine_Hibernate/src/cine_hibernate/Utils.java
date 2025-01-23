/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cine_hibernate;

import java.util.Scanner;

/**
 *
 * @author Laura Sánchez
 */
public class Utils {
    
    static Scanner scan = new Scanner(System.in);
    
    /**
     * Comprueba que una String que le pasemos por parámetros es una String
     * @param linea
     * @return 
     */
    public static boolean comprobarString(String linea){
        try{
            linea.toString();
            return true;
        }catch(Exception e){
            return false;
        }
    }
   
    /**
     * Comprueba que el valor que le pasamos por parámetros es un valor int
     * @param linea
     * @return 
     */
    public static boolean comprobarInt(String linea){
        try{
            Integer.parseInt(linea);
            return true;
        }catch(Exception e){
            return false;
        }
    }
    
    /**
     * Comprieba que una String que le pasamos por parámetros es un valor Double
     * @param linea
     * @return 
     */
    public static boolean comprobarDouble(String linea){
        try{
            Double.parseDouble(linea);
            return true;
        }catch(Exception e){
            return false;
        }
    }
    
    /**
     * Pide al usuario una confirmación y devuelve true o false.
     * @return 
     */
    public static boolean pedirConfirmacion(String pregunta){
       int respuesta = -1;
       boolean res = false;
       do{
           System.out.println(pregunta);
           System.out.println("> 1. Sí");
           System.out.println("> 2. No");
           System.out.print("> ");
           respuesta = scan.nextInt();
       }while(respuesta != 1 && respuesta != 2); 
       if(respuesta == 1){
           res = true;
       }else if(respuesta == 2){
           res = false;
       }
       return res;
    }
}
