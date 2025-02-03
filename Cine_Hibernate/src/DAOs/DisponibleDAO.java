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
import cine_hibernate.Utils;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
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
       
        // Comprobamos que los tres valores existen
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
        
        if(comprobarValido){
            System.out.println("-------------------------");
                System.out.println("\nPelícula: " + peliABuscar.getTitulo());
                System.out.println("Sala: " + salaBuscar.getSid());
                System.out.println("Pase: " + paseBuscar.getHora());
                System.out.println("-------------------------");
            if(recogerSalaPase(salaBuscar.getSid(), paseBuscar.getTid()).size() == 0){ 
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
            }else System.out.println("- Sesión ya reservada -");      
        }else System.out.println(" - Parámetros no válidos - ");   
    }
    
    /**
     * En caso de que la sala y sesión que se le haya pasado estén disponibles
     * devolverá true.
     * @param sid
     * @param tid
     * @return 
     */
    public List<Disponible> recogerSalaPase(int sid, int tid){
        List<Disponible> resultados = null;
        try{
            iniciarOperacion();
            resultados = sesion.createQuery("FROM Disponible WHERE tid=:param1 AND sid=:param2")
                    .setParameter("param1", tid)
                    .setParameter("param2", sid)
                    .list();
        }catch(HibernateException he){
            manejarExcepcion(he);
            throw he;
        }finally{
            sesion.close();
        }
        return resultados;
    }
    
    public void reservarEntradas(){
        SalasDAO salasDAO = new SalasDAO();
        PasesDAO pasesDAO = new PasesDAO();
        Scanner scan = new Scanner(System.in);
        
        System.out.println("\n    - Reserva de entradas :");
        // Pedimos una sala
        System.out.println("> Sala: ");
        Salas salaBuscar = salasDAO.buscarSalaPorSid();
        // Pedimos un pase
        System.out.println("> Hora del pase: ");
        Pases paseBuscar = pasesDAO.recogerPasesUnico();
        
        // Recogemos todas las butacas de la sala 
        List<Disponible> listadoButacas = recogerSalaPase(salaBuscar.getSid(), paseBuscar.getTid());
        if(listadoButacas != null){
            System.out.println("Se encontró un pase");
            mostrarEntradas(listadoButacas);
            // Recoger la fila de la reserva
            int filaClienta = pedirFilaReserva("¿Qué fila desea reservar?", maximoFilas(salaBuscar, paseBuscar));
            // Recoger la butaca de la reserva
            String butacaClienta = pedirButacaReserva("¿Qué butaca desea reservar?", filaClienta, listadoButacas);
            // Comprobar la disponibilidad de la fila y butaca escogidas
            if(comprobarDisponibilidadButaca
                (filaClienta, butacaClienta, salaBuscar.getSid(), paseBuscar.getTid())){
                // Reservar entrada y mostrar
                comprarEntrada(filaClienta, butacaClienta, salaBuscar.getSid(), paseBuscar.getTid());
                System.out.println("Entrada comprada");
                // Mostrar las entradas actualizadas
                mostrarEntradas(recogerSalaPase(salaBuscar.getSid(), paseBuscar.getTid()));
            }else{
                System.out.println("Butaca no disponible.\n");
            }
        }else System.out.println("No se encontró una sesión en esa sala");     
    }
    
    public void mostrarEntradas(List<Disponible> butacas){
        // Por cada butaca hace una comprobación de su posición
        int filaNueva = -1;
        System.out.println(" >> MOSTRAR BUTACAS");
        System.out.print("----------------------------------------------------");
        for(Disponible disp: butacas){        
            // En caso de que sea una fila nueva imprimimos el número de fila 
            if(filaNueva != disp.getFila()){
                filaNueva = disp.getFila();
                System.out.print("\n" + filaNueva + "    ");
            }
            // Imprimimos las butacas
            if(!disp.getLetra().trim().equals("PP")){
                if(disp.getEstado() == 0){
                    System.out.print(" " + disp.getLetra());
                }else System.out.print(" _");
            }else System.out.print("       ");
        }
        System.out.println("\n");
    } 
    
    public int pedirFilaReserva(String pregunta, int maxFilas){   
        String filaReserva;
        Scanner scan = new Scanner(System.in);
        boolean filaValido = true;
        
        do{   
            filaValido = true;
            System.out.print(pregunta + " ");
            filaReserva = scan.nextLine();
            if(!Utils.comprobarInt(filaReserva)){
                filaValido = false;
            }else{
                if(Integer.parseInt(filaReserva) > maxFilas || Integer.parseInt(filaReserva) <= 0){
                    filaValido = false;
                }
            }
        }while(!filaValido); 
        return Integer.parseInt(filaReserva);
    }
    
    public int maximoFilas(Salas sala, Pases pase){
        iniciarOperacion();
        int maxFilas = (int)sesion.createQuery
                ("SELECT MAX(d.fila) from Disponible d where sid="+ sala.getSid() +" AND tid=" + pase.getTid())
                .list().get(0);
        return maxFilas;
    }
    
    public String pedirButacaReserva(String pregunta, int fila, List<Disponible> listadoButacas){   
        String butacaReserva;
        Scanner scan = new Scanner(System.in);
        boolean butacaValido = true;
        HashMap<Integer, String> butacasFila = new HashMap();
        int contador = 0;
        
        // Recoger las butacas de la fila seleccionada
        for(Disponible disp: listadoButacas){
            if(disp.getFila() == fila){
                butacasFila.put(contador, disp.getLetra());
                contador++;
            }
        }
        
        // Pedir butaca
        do{   
            butacaValido = true;
            System.out.print(pregunta + " ");
            butacaReserva = scan.nextLine();
            if(
                    (butacaReserva.length() < 0 && butacaReserva.length() > 3) ||
                    !butacasFila.containsValue(butacaReserva) ||
                    butacaReserva.equals("PP"))
            {
                butacaValido = false;
                System.out.println("Butaca no válida. Inserte una sola letra dentro del rango de las butacas de la fila seleccionada.\n");
            }
            
        }while(!butacaValido); 
        return butacaReserva;
    }
    
    public boolean comprobarDisponibilidadButaca(int fila, String butaca, int sid, int tid){
        boolean disponible = true;
        
        iniciarOperacion();
        int estado = (int)sesion.createQuery
            ("SELECT estado FROM Disponible d WHERE sid=:param1 AND tid=:param2 AND d.fila=:param3 AND d.letra=:param4")          
            .setInteger("param1", sid)
            .setInteger("param2", tid)
            .setInteger("param3", fila)
            .setString("param4", butaca)
            .list().get(0);
        if(estado != 0){
            disponible = false;
        }
        sesion.close();
        return disponible;
    }
    
    public void comprarEntrada(int fila, String butaca, int sid, int tid){
        iniciarOperacion();
        try{
            // Recoger butaca
            Disponible dispReserva = (Disponible)sesion.createQuery("FROM Disponible d WHERE sid=:param1 AND tid=:param2 AND fila=:param3 AND letra=:param4")          
                .setInteger("param1", sid)
                .setInteger("param2", tid)
                .setInteger("param3", fila)
                .setString("param4", butaca)
                .list().get(0);
            dispReserva.setEstado(1);
            sesion.update(dispReserva);
            tx.commit();
        }catch(HibernateException he){
            manejarExcepcion(he);
            throw he;
        }finally{
            sesion.close();
        }
    }
}
