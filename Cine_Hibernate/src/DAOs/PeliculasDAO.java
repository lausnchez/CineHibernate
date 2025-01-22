/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOs;

import POJOs.Peliculas;
import cine_hibernate.HibernateUtil;
import cine_hibernate.Utils;
import java.util.List;
import java.util.Scanner;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author laura.sanchez
 */
public class PeliculasDAO {
    private Session sesion;
    private Transaction tx;
    private Scanner scan = new Scanner(System.in);
    
    //Iniciar Sesión
    private void iniciarOperacion(){
        sesion = HibernateUtil.getSessionFactory().openSession();
        tx = sesion.beginTransaction();
    }
    
    // Manejar excepción
    private void manejarExcepcion(HibernateException he){
        tx.rollback();
        throw new HibernateException("Error en la capa de acceso a Datos", he);
    }
    
    // FUNCIONES DE LA CLASE ---------------------------------------------------
    
    /*
        +---------------+---------------+------+-----+---------+----------------+
        | Field         | Type          | Null | Key | Default | Extra          |
        +---------------+---------------+------+-----+---------+----------------+
        | pid           | int(11)       | NO   | PRI | NULL    | auto_increment |
        | Titulo        | varchar(50)   | NO   |     | NULL    |                |
        | Director      | varchar(50)   | NO   |     | NULL    |                |
        | Nacionalidad  | varchar(50)   | NO   |     | NULL    |                |
        | Genero        | varchar(50)   | NO   |     | NULL    |                |
        | Clasificacion | varchar(50)   | NO   |     | NULL    |                |
        | Descr         | varchar(4000) | NO   |     | NULL    |                |
        | Duracion      | double        | NO   |     | NULL    |                |
        | Actores       | varchar(100)  | NO   |     | NULL    |                |
        | Link_Exterior | varchar(100)  | NO   |     | NULL    |                |
        | Link_Imagen   | varchar(100)  | NO   |     | NULL    |                |
        | Estado        | int(11)       | NO   |     | NULL    |                |
        +---------------+---------------+------+-----+---------+----------------+
    */
    
    // MÉTODOS DE ATRIBUTOS INDIVIDUALES
    //---------------------------------------------------------------------------
    
    /**
     * Comprueba si una String es mayor de 50 caracteres (para Titulo, Director,
     * Nacionalidad, Género y Clasificación).
     * @param string
     * @return false(mayor de)/true(menor de -> válido)
     */
    public boolean comprobar50(String string){
        if(string.length() > 50){
            return false;
        }else return true;
    }
    
    /**
     * Comprueba si una String es mayor de 100 caracteres (para Actores, 
     * Link_Exterior, Link_Imagen);
     * @param string
     * @return false(mayor de)/true(menor de -> válido)
     */
    public boolean comprobar100(String string){
        if(string.length() > 100){
            return false;
        }else return true;
    }
    
    /**
     * Comprueba si una String es mayor de 4000 caracteres (para la Descripción).
     * @param string
     * @return false(mayor de)/true(menor de -> válido)
     */
    public boolean comprobar4000(String string){
        if(string.length() > 4000){
            return false;
        }else return true;
    }
    
    /**
     * Inserta un título válido a la película que le pasemos por parámetros
     * @param pelicula 
     */
    public void insertarTitulo(Peliculas pelicula){
        String linea = "";
        do{
            System.out.print("> Título: ");
            linea = scan.nextLine();
        }while(!comprobar50(linea));
        pelicula.setTitulo(linea);
    }
    
    /**
     * Inserta un director válido a la película que le pasemos por parámetros
     * @param pelicula 
     */
    public void insertarDirector(Peliculas pelicula){
        do{
            System.out.print("> Director: ");
            pelicula.setDirector(scan.nextLine());
        }while(!comprobar50(pelicula.getDirector()));
    }
    
    /**
     * Inserta una nacionalidad válida a la película que le pasemos por parámetros
     * @param pelicula 
     */
    public void insertarNacionalidad(Peliculas pelicula){
        do{
            System.out.print("> Nacionalidad: ");
            pelicula.setNacionalidad(scan.nextLine());
        }while(!comprobar50(pelicula.getNacionalidad()));
    }
    
    /**
     * Inserta un género válido a la película que le pasemos por parámetros
     * @param pelicula 
     */
    public void insertarGenero(Peliculas pelicula){
        do{
            System.out.print("> Género: ");
            pelicula.setGenero(scan.nextLine());
        }while(!comprobar50(pelicula.getGenero()));
    }
    
    /**
     * Inserta una clasificación válida a la película que le pasemos por parámetros
     * @param pelicula 
     */
    public void insertarClasificacion(Peliculas pelicula){
        do{
            System.out.print("> Clasificación: ");
            pelicula.setClasificacion(scan.nextLine());
        }while(!comprobar50(pelicula.getClasificacion()));
    }
    
    /**
     * Inserta una descripción válida a la película que le pasemos por parámetros
     * @param pelicula 
     */
    public void insertarDescripcion(Peliculas pelicula){
        do{
            System.out.print("> Descripción: ");
            pelicula.setDescr(scan.nextLine());
        }while(!comprobar4000(pelicula.getDescr()));
    }
    
    /**
     * Inserta una duración válida a la película que le pasemos por parámetros
     * @param pelicula 
     */
    public void insertarDuracion(Peliculas pelicula){
        String linea = "";
        do{
           System.out.println("> Duración: "); 
           linea = scan.nextLine();
        }while(!Utils.comprobarDouble(linea));
        pelicula.setDuracion(Double.parseDouble(linea));
    }
    
    /**
     * Inserta un campo de actores válidos a la película que le pasemos por parámetros
     * @param pelicula 
     */
    public void insertarActores(Peliculas pelicula){
        do{
            System.out.print("> Actores: ");
            pelicula.setActores(scan.nextLine());
        }while(!comprobar100(pelicula.getActores()));
    }
    
    /**
     * Inserta un Link exterior válido a la película que le pasemos por parámetros
     * @param pelicula 
     */
    public void insertarLinkExterior(Peliculas pelicula){
        do{
            System.out.print("> Link Exterior: ");
            pelicula.setLinkExterior(scan.nextLine());
        }while(!comprobar100(pelicula.getLinkExterior()));
    }
    
    /**
     * Inserta un Link de imagen válido a la película que le pasemos por parámetros
     * @param pelicula 
     */
    public void insertarLinkImagen(Peliculas pelicula){
        do{
            System.out.print("> Link de la Imagen: ");
            pelicula.setLinkImagen(scan.nextLine());
        }while(!comprobar100(pelicula.getLinkImagen()));
    }
    
    /**
     * Inserta un estado válido a la película que le pasemos por parámetros
     * @param pelicula 
     */
    public void insertarEstado(Peliculas pelicula){
        String linea = "";
        do{
            System.out.print("> Estado: ");
            linea = scan.nextLine();
        }while(!Utils.comprobarInt(linea));
        pelicula.setEstado(Integer.parseInt(linea));
    }
    
    // MÉTODOS GENERALES DE BBDD
    //--------------------------------------------------------------------------
    
    /**
     * Muestra los datos de una sola película en la consola
     * @param resultado 
     */
    public void mostrarPelicula(Peliculas resultado){
        System.out.println(resultado.getPid()+ ": " +resultado.getTitulo());
        System.out.println("    -> Director: " + resultado.getDirector());
        System.out.println("    -> Nacionalidad: " + resultado.getNacionalidad());
        System.out.println("    -> Género: " + resultado.getGenero());
        System.out.println("    -> Clasificación: " + resultado.getClasificacion());
        System.out.println("    -> Descripción: " + resultado.getDescr());
        System.out.println("    -> Duración: " + resultado.getDuracion());
        System.out.println("    -> Actores: " + resultado.getActores());
        System.out.println("    -> Link exterior: " + resultado.getLinkExterior());
        System.out.println("    -> Link imagen: " + resultado.getLinkImagen());
        System.out.println("    -> Estado: " + resultado.getEstado());
    }
    
    /**
     * Método que nos muestra todas las películas de la tabla películas
     */
    public void mostrarTodasLasPeliculas(){     
        try{
            iniciarOperacion();
            String query = "FROM Peliculas";
            List<Peliculas> listado = sesion.createQuery(query).list();
            for(Peliculas resultado: listado){
                mostrarPelicula(resultado);
            }
        }catch(HibernateException he){
            manejarExcepcion(he);
            throw he;
        }finally{
            sesion.close();
        }
    }
    
    /**
     * Inserta una pelicula y nos devuelve su ID. En caso de no poder realizarse
     * la operación devolverá -1.
     * @param pelicula
     * @return 
     */
    public int insertarPelicula(){
        Peliculas nuevaPelicula = new Peliculas();
        int ID = -1;
        System.out.println(".... INSERTAR PELICULA ....");
        insertarTitulo(nuevaPelicula);
        insertarDirector(nuevaPelicula);
        insertarNacionalidad(nuevaPelicula);
        insertarGenero(nuevaPelicula);
        insertarClasificacion(nuevaPelicula);
        insertarDescripcion(nuevaPelicula);
        insertarDuracion(nuevaPelicula);
        insertarActores(nuevaPelicula);
        insertarLinkExterior(nuevaPelicula);
        insertarLinkImagen(nuevaPelicula);
        insertarEstado(nuevaPelicula);
        
        try{
            iniciarOperacion();
            ID = (int)sesion.save(nuevaPelicula);
        }catch(HibernateException he){
            manejarExcepcion(he);
            throw he;
        }finally{
            sesion.close();
        }
        return ID;
    }
    
    
    public void modificarPelicula(Peliculas pelicula){
        
    }
}
