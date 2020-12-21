package polimi.db2.gamifyDB.services;

import javax.persistence.PersistenceException;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import polimi.db2.gamifyDB.entities.Answer;
import polimi.db2.gamifyDB.entities.Question;
import polimi.db2.gamifyDB.entities.Questionnaire;
import polimi.db2.gamifyDB.entities.Review;
import java.util.List;

@Stateless
public class AnswerService {
	
	@PersistenceContext(unitName = "GamifyEJB")
	private EntityManager em;
	@EJB(name = "gamifyDB.services/ReviewService")
	private ReviewService reviewService;

	public AnswerService(){
	}

	public int createAnswer(String content, Question question, int reviewId) throws Exception{
		try{
		    Answer answer = new Answer();
		  	Review review = reviewService.find(reviewId);
			
		    answer.setQuestion(question);
	        answer.setReview(review);
	        answer.setContent(content);
	        
	        em.persist(answer);
	        em.flush();
	        return answer.getAnswerId();
		} catch (PersistenceException e) {
			throw new Exception("Could not insert question");
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
	
	public Answer find(int answerId) throws Exception{
		  return em.find(Answer.class, answerId);
		}

}
