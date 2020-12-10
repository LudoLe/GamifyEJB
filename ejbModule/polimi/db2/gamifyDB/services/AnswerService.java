package polimi.db2.gamifyDB.services;

import javax.persistence.PersistenceException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.NonUniqueResultException;
import polimi.db2.gamifyDB.entities.Answer;
import polimi.db2.gamifyDB.entities.Question;
import polimi.db2.gamifyDB.entities.Review;
import java.util.List;
import java.util.Date;

@Stateless
public class AnswerService {
	
	@PersistenceContext(unitName = "GamifyEJB")
	private EntityManager em;

	public AnswerService(){
	}

	public int createAnswer(String content, int questionId, int reviewId) throws Exception{
		try{
		    Answer answer = new Answer();
			Question question = em.createNamedQuery("Question.findById", Question.class).setParameter(1, questionId).getSingleResult();
			Review review = em.createNamedQuery("Review.findById", Review.class).setParameter(1, reviewId).getSingleResult();
			answer.setContent(content);
		    answer.setQuestion(question);
	        answer.setReview(review);
	        em.persist(answer);
	        em.flush();
	        return answer.getAnswerId();
		} catch (PersistenceException e) {
			throw new Exception("Could not insert question");
		}     
	}
	
	public List<Answer> findAnswersByProduct(int productId) throws Exception {
		List<Answer> answers = null;
	try {
		answers = em.createNamedQuery("Answer.findAnswersByProductId", Answer.class).setParameter(1, productId).getResultList();
		return answers;
	} catch (PersistenceException e) {
		throw new Exception("Could not retrieve answers for thi product" + productId);
	}
}
	
	public List<Answer> findAll() throws Exception {
		List<Answer> answers = null;
	try {
		answers = em.createNamedQuery("Answer.findALl", Answer.class).getResultList();
		return answers;
	} catch (PersistenceException e) {
		throw new Exception("Could not retrieve answers");
	}
}

}
