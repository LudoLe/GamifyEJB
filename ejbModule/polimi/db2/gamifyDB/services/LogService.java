package polimi.db2.gamifyDB.services;

import javax.persistence.PersistenceException;

import polimi.db2.gamifyDB.entities.Log;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;


@Stateless
public class LogService {
	
	@PersistenceContext(unitName = "GamifyEJB")
	private EntityManager em;

	public LogService(){
	}
	
	public int createLog() throws Exception{
		try{
			
		    Log log=new Log();
		   
		    log.setDatetime(new Date());
		     
	        em.persist(log);
	        em.flush();
	        return log.getLogId();
		} catch (PersistenceException e) {
			throw new Exception("Could not insert log");
		}     
	}
	
	
	public Log find(int logId) throws Exception{
	  return em.find(Log.class, logId);
	}

}
