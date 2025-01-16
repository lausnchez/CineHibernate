/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOs;

import POJOs.Peliculas;
import cine_hibernate.HibernateUtil;
import java.util.List;
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
    /**
     * Método que nos muestra todas las películas de la tabla películas
     */
    public void mostrarTodasLasPeliculas(){
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
        try{
            iniciarOperacion();
            String query = "FROM Peliculas";
            List<Peliculas> listado = sesion.createQuery(query).list();
            for(Peliculas resultado: listado){
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
        }catch(HibernateException he){
            manejarExcepcion(he);
            throw he;
        }finally{
            sesion.close();
        }
    }
}
