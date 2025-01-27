/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOs;

import POJOs.ButacaLetra;
import cine_hibernate.HibernateUtil;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author laura.sanchez
 */
public class ButacaLetraDAO {
    private Session sesion;
    private Transaction tx;
    
    //Iniciar operación
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
    public List<ButacaLetra> recogerTodasLasButacas(){
        List<ButacaLetra> listadoCompleto = null;
        String query = "from ButacaLetra bl";
        
        iniciarOperacion();
        listadoCompleto = sesion.createQuery(query).list();
        
        return listadoCompleto;
    }
}
