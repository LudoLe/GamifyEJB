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
public class QuestionService {
	
	@PersistenceContext(unitName = "GamifyEJB")
	private EntityManager em;

	public QuestionService(){
	}
	
	public void createQuestion(String content, int questionaireId){
		try{
		    Question question= new Question();
		    question.setContent(content);
		    question.setquestionaireId(questionaireId);
	        
	        em.persist(answer);
	        em.flush();
	        return question.getId();
		} catch (PersistenceException e) {
			throw new Exception("Could not insert question");
		}     
	}
	
	public List<Question> getQuestionsByProduct(int productId){
		List<question> questions = null;
	try {
		questions = em.createNamedQuery("Question.getQuestionsByProductId", Question.class).setParameter(1, productId).getResultList();
		return questions;
	} catch (PersistenceException e) {
		throw new Exception("Could not retrieve questions for this product" + productId );
	}
	else return null;
}
	
	public List<Question> findAll(){
		List<Question> questions = null;
	try {
		questions = em.createNamedQuery("Answer.findAll", Question.class).getResultList();
		return answers;
	} catch (PersistenceException e) {
		throw new Exception("Could not retrieve questions");
	}
	else return null;
}

}
