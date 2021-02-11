/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blacklistvalidator;

import java.util.List;

/**
 *
 * @author hcadavid
 */
public class Main {
    
    public static void main(String a[]) throws InterruptedException{
    	//Para controlar el tiempo de ejecucion hacemos uso del runtime long
    	long iniciarEjecucion = System.currentTimeMillis();
        HostBlackListsValidator hblv=new HostBlackListsValidator();
        int cantidadProcesadores = Runtime.getRuntime().availableProcessors(); //Consultamos la cantidad de procesadores disponibles
        //List<Integer> blackListOcurrences=hblv.checkHost("202.24.34.55",cantidadProcesadores); //añadimos el valor cantidad de Procesadores para evaluar
        List<Integer> blackListOcurrences=hblv.checkHost("200.24.34.55",4); //añadimos el valor para N (cantidad de Threads)
        System.out.println("The host was found in the following blacklists:"+blackListOcurrences);
        long finalizarEjecucion = System.currentTimeMillis();
        long tiempoEjecucion = (finalizarEjecucion-iniciarEjecucion)/1000; 
        System.out.println("Tiempo ejecución: "+tiempoEjecucion+" segundos");
        //System.out.println("Numero de procesadores: "+cantidadProcesadores); //Mostramos la cantidad de procesadores
        }}  
