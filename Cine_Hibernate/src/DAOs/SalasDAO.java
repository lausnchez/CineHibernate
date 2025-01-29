/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOs;

import POJOs.Salas;
import cine_hibernate.HibernateUtil;
import java.util.List;
import java.util.Scanner;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author laura.sanchez
 */
public class SalasDAO {
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
    public List<Salas> recogerTodasLasSalas(){
        iniciarOperacion();
        List<Salas> listadoSalas = sesion.createQuery("from Salas s").list();
        return listadoSalas;
    }
    
    public Salas buscarSalaUnica(){
        System.out.println("Inserte la sala que quiere buscar: ");
        System.out.print("    > ");
        Scanner scan = new Scanner(System.in);
        String salaBusqueda = scan.nextLine();
        return new Salas();
    }
    
    public Salas buscarSalaPorNombre(){
        Salas resultado = null;
        String busqueda = "";
        Scanner scan = new Scanner(System.in);
        
        System.out.println("¿Qué desea buscar?");
        System.out.print("    > ");
        busqueda = scan.nextLine();
            try{
                iniciarOperacion();
                Query query = sesion.createQuery("FROM Salas s WHERE s.sid = :param1");
                query.setString("param1", busqueda);
                query.uniqueResult();               
            }catch(HibernateException he){
                manejarExcepcion(he);
                throw he;
            }finally{
                sesion.close();
            }
            
        if(resultado == null){
            System.out.println("No se encontraron resultados");
        }
        return resultado;
    }
}
