package polimi.db2.gamifyDB.services;

import javax.persistence.PersistenceException;

import org.eclipse.persistence.config.CacheUsage;
import org.eclipse.persistence.config.QueryHints;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import polimi.db2.gamifyDB.entities.Answer;
import polimi.db2.gamifyDB.entities.Leaderboard;
import java.util.List;

@Stateless
public class LeaderboardService {
	
	@PersistenceContext(unitName = "GamifyEJB")
	private EntityManager em;
	

	public LeaderboardService(){
		
	}


	
	public List<Leaderboard> findBetween(int low, int high) throws Exception {
		List<Leaderboard> answers = null;
		try {
			
			answers = em.createNamedQuery("Leaderboard.findAll", Leaderboard.class).setHint(QueryHints.CACHE_USAGE, CacheUsage.DoNotCheckCache).getResultList().subList(low, high);
			return answers;
		} catch (PersistenceException e) {
			throw new Exception("Could not retrieve leaderboard");
		}
	}
	
	public List<Leaderboard> findAll() throws Exception {
		List<Leaderboard> answers = null;
		try {
			answers = em.createNamedQuery("Leaderboard.findAll", Leaderboard.class).getResultList();
			return answers;
		} catch (PersistenceException e) {
			throw new Exception("Could not retrieve leaderboard");
		}
	}
	
	public Answer find(String username) throws Exception{
		return em.find(Answer.class, username);
	}

}
