/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOs;

import POJOs.Peliculas;
import POJOs.Salas;
import cine_hibernate.HibernateUtil;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author laura.sanchez
 */
public class DisponibleDAO {
    private Session sesion;
    private Transaction tx;
    
    // Iniciar Operación
    private void iniciarOperacion(){
        sesion = HibernateUtil.getSessionFactory().openSession();
        tx = sesion.beginTransaction();
    }
    
    // Manejar excepción
    private void manejarExcepcion(HibernateException he){
        tx.rollback();
        throw new HibernateException("Error en la capa de acceso a datos", he);
    }
    
    // FUNCIONES DE LA CLASE ---------------------------------------------------
    public void rellenarDisponibles(){
        // Llenar salas (select a disponibles y replicar)
            // Sala 1 Pase 2 peli 2
            // Sala 1 Pase 3 pel 15
            // Sala 2 Pase 1 peli 1
            // Sala 2 Pase 2 peli 2
            // Sala 2 Pase 3 peli 15
            // Sala 3 Pase 1 peli 1
            // Sala 3 Pase 2 peli 2
            // Sala 3 Pase 3 peli 15
        /*
            Primero como deberemos hacer esto por cada sala tres veces haremos un
            bucle que se hará 3 veces, que es el máximo de salas disponibles.
            Después haremos 3 pases, por cada una de las salas, y en cada uno de
            ellos habrá una película distinta.
            */
        
        PeliculasDAO peliDAO = new PeliculasDAO();
        SalasDAO salasDAO = new SalasDAO();
        
        Peliculas peliABuscar = peliDAO.buscarPeliculaPorNombre();
        Salas salaBuscar = salasDAO.buscarSalaPorNombre();
    }
    
}
