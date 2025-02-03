/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cine_hibernate;

import DAOs.DisponibleDAO;
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
        DisponibleDAO dispDAO = new DisponibleDAO();
        
        do{
            System.out.println("\n*************** MENU *****************");
            System.out.println("> 1. Mostrar todas las peliculas");
            System.out.println("> 2. Mostrar una película");
            System.out.println("> 3. Insertar una película");
            System.out.println("> 4. Modificar una película");
            System.out.println("> 5. Eliminar una película");
            System.out.println("> 6. Mostrar una película");
            System.out.println("> 7. Mostrar películas de un actor");
            System.out.println("> 8. Agregar un pase");
            System.out.println("> 9. Consultar sesiones y salas de una película");
            System.out.println(">10. Reservar entradas");
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
                    // Mostrar una sola película
                    Peliculas resultado = peliculas.buscarPeliculaPorNombre();
                    peliculas.mostrarPelicula(resultado);
                    break;
                case 3:
                    // Insertar una película
                    peliculas.insertarPelicula();
                    break;
                case 4:
                    // Modificar una película
                    peliculas.modificarPelicula();
                    break;
                case 5:
                    // Eliminar una película
                    peliculas.eliminarPelicula();
                    break;   
                case 6:
                    // Mostrar una película
                    System.out.println("- - - - Búsqueda por parámetros - - - - ");
                    Peliculas busqueda = peliculas.buscarPeliculaUnica();
                    if(busqueda != null){
                        peliculas.mostrarPelicula(busqueda);
                    }
                    break;
                case 7:
                     // Mostrar películas del actor
                    peliculas.mostrarPeliculasActor();
                    break;
                case 8:
                    // Sala 1 Sesion 2 peli 2
                    // Sala 1 Sesion 3 pel 15
                    // Sala 2 Sesion 1 peli 1
                    // Sala 2 Sesion 2 peli 2
                    // Sala 2 Sesion 3 peli 15
                    // Sala 3 Sesion 1 peli 1
                    // Sala 3 Sesion 2 peli 2
                    // Sala 3 Sesion 3 peli 15     
                    dispDAO.crearNuevoDisponibles();
                    break;
                case 9:
                    // Consultar todas las horas/sesiones y salas de una pelicula dada (por titulo)
                    // Tablas peliculas, pases, pase_sala, salas
                    /*
                        PELICULAS -> PID
                        PASE_SALA -> ID, SID, TID
                        PASES -> TID, HORA
                        SALAS -> descr
                        DISPONIBLE -> PID, SID, TID
                    
                        PELICULA, SESION, SALA(SALAS) , HORA(PASES)
                    */
                    break;
                case 10:
                    /*
                    - Reservar entradas - 
                    Pedimos una sala y una sesión al usuario
                    Mostramos cómo está actualmente la sala en forma de matriz
                    Ocupadas _ y libre la letra de la butaca, pasillo en blanco
                    */
                    dispDAO.reservarEntradas();
                    break;
                case 11:
                    /*
                    Encontrar la película que más espectadores tiene, 
                    independientemente de las salas y los pases, mostrando todas
                    las películas
                    */
                    dispDAO.contarEspectadores();
                    break;
            }
            
            /*
            - Mostrar numero de peliculas
            - Le pasas una peli y te dice en que salas y a que horas está
            */
            
        }while(opcion != 0);
        System.exit(0);
    }
    
}
