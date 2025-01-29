/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOs;

import POJOs.Pases;
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
public class PasesDAO {
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
    public List<Pases> recogerTresPrimerosPases(){
        iniciarOperacion();
        List<Pases> listado = sesion.createQuery("from Pases p WHERE pid < 4").list();
        return listado;
    }
    
    /**
     * Pide una número de pase y nos devuelve un resultado
     * @return 
     */
    public Pases recogerPasesUnico(){
        Pases busquedaPase = new Pases();
        String busqueda = "";
        Scanner scan = new Scanner(System.in);
        
        System.out.println("¿Qué nº de pase desea buscar?");
        System.out.print("    > ");
        busqueda = scan.nextLine();
        
        if(Utils.comprobarInt(busqueda)){
            try{
            iniciarOperacion();
                busquedaPase = (Pases)sesion.createQuery("FROM Pases p WHERE tid=:param")
                        .setParameter("param", Integer.parseInt(busqueda))
                        .uniqueResult();  
            }catch(HibernateException he){
                manejarExcepcion(he);
                throw he;
            }finally{
                sesion.close();
            }
        }else System.out.println("Valor no válido");
        
        if(busquedaPase == null) System.out.println("No se encontraron resultados");
        return busquedaPase;
    }
}
