/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOs;

import POJOs.ButacaLetra;
import POJOs.Disponible;
import POJOs.Pases;
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
    public void crearNuevoDisponibles(){
        PeliculasDAO peliDAO = new PeliculasDAO();
        SalasDAO salasDAO = new SalasDAO();
        PasesDAO pasesDAO = new PasesDAO();
        ButacaLetraDAO butacasDAO = new ButacaLetraDAO();
        
        System.out.println("\n\n CREACIÓN DE UN NUEVO PASE EN SALA");
        // Pedimos una película
        System.out.println("> Película: ");
        Peliculas peliABuscar = peliDAO.buscarPeliculaPorPID();
        // Pedimos una sala
        System.out.println("> Sala: ");
        Salas salaBuscar = salasDAO.buscarSalaPorSid();
        // Pedimos un pase
        System.out.println("> Hora del pase: ");
        Pases paseBuscar = pasesDAO.recogerPasesUnico();
        // Recogemos todas las butacas de la sala
        List<ButacaLetra> butacasTotales = butacasDAO.recogerTodasLasButacas();
        
        boolean comprobarValido = true;
        
        if(paseBuscar == null ){
            comprobarValido = false;
        }
        if(peliABuscar == null ){
            comprobarValido = false;
        }
        if(salaBuscar == null ){
            comprobarValido = false;
        }
        
        System.out.println("Película: " + peliABuscar.getTitulo());
        System.out.println("Sala: " + salaBuscar.getSid());
        System.out.println("Pase: " + paseBuscar.getHora());
        
        if(comprobarValido){
            try{
                iniciarOperacion();
                Disponible nuevoDispo = null;
                int contador = 0;
                for(ButacaLetra butActual: butacasTotales){
                    nuevoDispo = new Disponible();
                    
                    // Parámetros buscados
                    nuevoDispo.setPases(paseBuscar);
                    nuevoDispo.setPeliculas(peliABuscar);
                    nuevoDispo.setSalas(salaBuscar);

                    // Butacas
                    nuevoDispo.setFila(butActual.getFila());
                    nuevoDispo.setLetra(butActual.getLetra());
                    nuevoDispo.setButacas(butActual.getButaca());
                    nuevoDispo.setEstado(0);
                    nuevoDispo.setPos(butActual.getPos());

                    sesion.save(nuevoDispo);
                    contador++;
                }

                tx.commit();
                System.out.println("- Se han agregado " + contador + " asientos -");
            }catch(HibernateException he){
                manejarExcepcion(he);
                throw he;
            }finally{
                sesion.close();
            }
        }else System.out.println(" - Parámetros no válidos - ");
        
    }
    
}
