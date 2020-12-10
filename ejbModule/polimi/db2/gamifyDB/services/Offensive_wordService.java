package polimi.db2.gamifyDB.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import polimi.db2.gamifyDB.entities.Offensive_word;

@Stateless
public class Offensive_wordService {
	@PersistenceContext(unitName = "GamifyEJB")
	private EntityManager em;
	
	public Offensive_wordService(){
	}
	
	public List<String> getWords() throws Exception {
	try {
		return em.createNamedQuery("Offensive_word.getWords", Offensive_word.class).getResultList().stream().map(arg0 -> arg0.getWord()).collect(Collectors.toList()); 
	} catch (PersistenceException e) {
		throw new Exception("Could not retrieve offensive words");
	}
}
}
