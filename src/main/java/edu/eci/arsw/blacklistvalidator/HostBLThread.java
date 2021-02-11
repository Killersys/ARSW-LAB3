package edu.eci.arsw.blacklistvalidator;
import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import edu.eci.arsw.blacklistvalidator.HostBlackListsValidator;


public class HostBLThread extends Thread {
	
	
	private static final int BLACK_LIST_ALARM_COUNT=5; // El número mínimo de listas en las que debe aparecer para dar la dirección como NotTurst
	private int numOcurrencias; //Contador de ocurrencias; incrementa su valor si encuentra la IP en una lista	
	private LinkedList<Integer> listaOcurrencias; // Lista donde guarda la lista donde encuentra la ocurrencia con la IP dada
	private int inicio; 
	private int fin;
	private int numListasVisitadas;
	private HostBlacklistsDataSourceFacade skds;
	private String IP;
	
	
	public HostBLThread(String IP, int in,int fi, HostBlacklistsDataSourceFacade skds) { 
		this.inicio=in;
		this.fin=fi;
		this.IP= IP;
		this.numListasVisitadas = 0;
		this.numOcurrencias = 0;
		this.skds = skds;
		listaOcurrencias = new LinkedList<Integer>();}
	
	public void run() {
		for(int i = inicio; i < fin; i++) {
			numListasVisitadas = numListasVisitadas + 1;
			if(skds.isInBlackListServer(i, IP)) {
				listaOcurrencias.add(i);
				numOcurrencias = numOcurrencias + 1;}}}
	
	public LinkedList<Integer> getListaOcurrencias() { //Devuelve la lista donde encontró las ocurrencias con la IP
		return listaOcurrencias;}
	
	    public int getCantidadListasVisitadas() { 		// Devuelve el número de Listas que recorrió
		return numListasVisitadas;}
	    
	    public int getCantidaddeOcurrencias() { 		// Devuelve la cantidad de ocurrencias que encontró en el recorrido
		return numOcurrencias;}
	}