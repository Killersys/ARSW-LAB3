/*
<< * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blacklistvalidator;

import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hcadavid
 */
public class HostBlackListsValidator {

    private static final int BLACK_LIST_ALARM_COUNT=5;
    private boolean fin = false;
    
    /**
     * Check the given host's IP address in all the available black lists,
     * and report it as NOT Trustworthy when such IP was reported in at least
     * BLACK_LIST_ALARM_COUNT lists, or as Trustworthy in any other case.
     * The search is not exhaustive: When the number of occurrences is equal to
     * BLACK_LIST_ALARM_COUNT, the search is finished, the host reported as
     * NOT Trustworthy, and the list of the five blacklists returned.
     * @param ipaddress suspicious host's IP address.
     * @return  Blacklists numbers where the given host's IP address was found.
     * @throws InterruptedException 
     */
    public List<Integer> checkHost(String ipaddress, int N) throws InterruptedException{
        
        LinkedList<Integer> blackListOcurrences=new LinkedList<>();        
        int ocurrencesCount=0;        
        HostBlacklistsDataSourceFacade skds=HostBlacklistsDataSourceFacade.getInstance();
        int checkedListsCount=0;
        List<HostBLThread> threads = new ArrayList<HostBLThread>();
        int espacio = skds.getRegisteredServersCount()/N; //Modificación para dividir el espacio de búsqueda entre N partes indicadas
        int inicio = 0;
        int fin = inicio + espacio;
        for (int i=0; i<N; i++){
    		HostBLThread running = new HostBLThread(ipaddress, inicio, fin, skds,this);
    		running.start();
    		threads.add(running);
    		inicio = fin + 1;
    		fin = fin + espacio + 1;
    		
    		if (skds.isInBlackListServer(i, ipaddress)){            
    			blackListOcurrences.add(i);         
    			ocurrencesCount++;}}
    for(HostBLThread running : threads) {
    	running.join();}
    for(HostBLThread running : threads) {
    	checkedListsCount = checkedListsCount + running.getCantidadListasVisitadas();
    	ocurrencesCount = ocurrencesCount + running.getCantidaddeOcurrencias();
    	for(Integer i : running.getListaOcurrencias()) {
    		blackListOcurrences.add(i);}}
        
        if (ocurrencesCount>=BLACK_LIST_ALARM_COUNT){
            skds.reportAsNotTrustworthy(ipaddress);}
        else{
            skds.reportAsTrustworthy(ipaddress);}                
        
        LOG.log(Level.INFO, "Checked Black Lists:{0} of {1}", new Object[]{checkedListsCount, skds.getRegisteredServersCount()});
        
        return blackListOcurrences;
    }
    
    public void setFin(boolean fin){
    	this.fin = fin;}
    
    public boolean getFin(){
    	return fin;}
    
    private static final Logger LOG = Logger.getLogger(HostBlackListsValidator.class.getName());
    
    
    
}
