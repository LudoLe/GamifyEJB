package polimi.db2.gamifyDB.services;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class Offensive_wordService {
	@PersistenceContext(unitName = "GamifyEJB")
	private EntityManager em;
}
