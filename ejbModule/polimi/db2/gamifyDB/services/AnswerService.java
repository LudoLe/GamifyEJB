package polimi.db2.gamifyDB.services;

import javax.persistence.PersistenceException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.NonUniqueResultException;
import polimi.db2.gamifyDB.entities.User;
import polimi.db2.gamifyDB.entities.Answer;
import polimi.db2.gamifyDB.entities.Review;
import java.util.List;
import java.util.Date;

@Stateless
public class AnswerService {
	
	@PersistenceContext(unitName = "GamifyEJB")
	private EntityManager em;

	public AnswerService(){
	}

	public void createAnswer(String content, int questionId, int reviewId){
		try{
		    Answer answer = new Answer();
		    answer.setContent(content);
		    answer.setquestionId(questionId);
	        answer.setReviewId(reviewId);
	        em.persist(answer);
	        em.flush();
	        return answer.getId();
		} catch (PersistenceException e) {
			throw new Exception("Could not insert question");
		}     
	}
	
	public List<Answer> findAnswersByProduct(int productId){
		List<Answer> answers = null;
	try {
		answers = em.createNamedQuery("Answer.findAnswersByProductId", Answer.class).setParameter(1, productId).getResultList();
		return answers;
	} catch (PersistenceException e) {
		throw new Exception("Could not retrieve answers for thi product" + productId);
	}
	else return null;
}
	
	public List<Answer> findAll(){
		List<Answer> answers = null;
	try {
		answers = em.createNamedQuery("Answer.findALl", Answer.class).getResultList();
		return answers;
	} catch (PersistenceException e) {
		throw new Exception("Could not retrieve answers");
	}
	else return null;
}

}
