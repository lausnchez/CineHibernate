/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOs;

import cine_hibernate.HibernateUtil;
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
    private void mostrarTodasLasPeliculas(){
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
    }
}
