/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOs;

import POJOs.PaseSala;
import cine_hibernate.HibernateUtil;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author laura.sanchez
 */
public class PaseSalaDAO {
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
    public List<PaseSala> recogerPasesPorSala(String sid){
        PaseSala pases = new PaseSala();
        
        return pases;
    }
}
