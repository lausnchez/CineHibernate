/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cine_hibernate;

import DAOs.PeliculasDAO;
import POJOs.Peliculas;
import java.util.Scanner;

/**
 *
 * @author laura.sanchez
 */
public class Cine_Hibernate {

    static Scanner scan = new Scanner(System.in);
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        menu();
    }
    
    public static void menu(){
        int opcion = -1;
        String linea = "";
        PeliculasDAO peliculas = new PeliculasDAO();
        
        do{
            System.out.println("\n*************** MENU *****************");
            System.out.println("> 1. Mostrar todas las peliculas");
            System.out.println("> 2. Insertar una película");
            System.out.println("> 3. Modificar una película");
            System.out.println("> 4. Eliminar una película");
            System.out.println("> 5. Mostrar una película");
            System.out.println("> 0. Salir");
            
            do{
                System.out.print("> OPCIÓN: ");
                linea = scan.nextLine();
                if(!Utils.comprobarInt(linea)){
                    System.out.println("Inserte un valor numérico");
                }else{
                    opcion = Integer.parseInt(linea);
                    if(opcion < 0){
                        System.out.println("Valor no válido");
                    }
                }
            }while(opcion < 0);
            
            switch(opcion){
                case 0: 
                    // Salir de la aplicación
                    System.out.println("Hasta pronto!");
                    break;
                default: 
                    // Valor no contemplado en el menú
                    System.out.println("Valor no válido");
                    break;
                case 1:
                    // Mostrar todas las películas
                    peliculas.mostrarTodasLasPeliculas();
                    break;
                case 2:
                    // Insertar una película
                    peliculas.insertarPelicula();
                    break;
                case 3:
                    // Modificar una película
                    peliculas.modificarPelicula();
                    break;
                case 4:
                    // Eliminar una película
                    break;
                case 5:
                    // Mostrar una película
                    System.out.println("- - - - Búsqueda por parámetros - - - - ");
                    Peliculas busqueda = peliculas.buscarPeliculaUnica();
                    if(busqueda != null){
                        peliculas.mostrarPelicula(busqueda);
                    }
                    break;          
            }
            
        }while(opcion != 0);
    }
    
}
