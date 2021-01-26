package polimi.db2.gamifyDB.services;

import javax.persistence.PersistenceException;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import polimi.db2.gamifyDB.entities.Answer;
import polimi.db2.gamifyDB.entities.Question;
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

	public Answer createAnswer(String content, Question question, Review review) throws Exception{
		try{
		    Answer answer = new Answer();			
		    answer.setQuestion(question);
	        answer.setReview(review);
	        answer.setContent(content);	  
	        question = em.merge(question);
	        List<Answer> oldAnswers = question.getAnswers();
	        oldAnswers.add(answer);
	        question.setAnswers(oldAnswers);
	        em.persist(answer);
	        return answer;
		} catch (PersistenceException e) {
			throw new Exception("Could not insert question");
		}     
	}
	
	public void createAnswers(List<Answer> answers, Review review) throws Exception{
		try{
			for(Answer answer : answers){ 
				answer.setReview(review);
				Question question = em.merge(answer.getQuestion());
		        List<Answer> oldAnswers = question.getAnswers();
		        oldAnswers.add(answer);
		        question.setAnswers(oldAnswers);
				em.persist(answer); 
				}
		} catch (PersistenceException e) {
			throw new Exception("Could not insert question");
		}     
	}


	
	public List<Answer> findAll() throws Exception {
		List<Answer> answers = null;
	try {
		answers = em.createNamedQuery("Answer.findAll", Answer.class).getResultList();
		return answers;
	} catch (PersistenceException e) {
		throw new Exception("Could not retrieve answers");
	}
}
	
	public Answer find(int answerId) throws Exception{
		  return em.find(Answer.class, answerId);
		}

}
