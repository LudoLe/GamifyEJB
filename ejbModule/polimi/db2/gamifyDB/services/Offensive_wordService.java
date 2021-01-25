package polimi.db2.gamifyDB.services;

import javax.persistence.PersistenceException;

import polimi.db2.gamifyDB.entities.Offensive_word;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class Offensive_wordService{
	
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
	
	public void createOffensive_word(String word) throws Exception{
		try{
			Offensive_word offensiveWord=new Offensive_word();
			offensiveWord.setWord(word);
		   
			em.persist(offensiveWord);
			em.flush();
			return;
		} catch (PersistenceException e) {
			throw new Exception("Could not insert word");
		}     
	}
	
	
	public Offensive_word find(String word) throws Exception{
	  return em.find(Offensive_word.class, word);
	}

}

