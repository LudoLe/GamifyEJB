package polimi.db2.gamifyDB.services;

import javax.persistence.PersistenceException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.NonUniqueResultException;
import polimi.db2.gamifyDB.entities.Answer;
import polimi.db2.gamifyDB.entities.Question;
import polimi.db2.gamifyDB.entities.Questionnaire;
import polimi.db2.gamifyDB.entities.Review;
import java.util.List;
import java.util.Date;


@Stateless
public class QuestionService {
	
	@PersistenceContext(unitName = "GamifyEJB")
	private EntityManager em;

	public QuestionService(){
	}
	
	public Question createQuestion(String content, Questionnaire questionnaire) throws Exception{
		try{
			Question question= new Question();
		    question.setContent(content);
		    question.setQuestionnaire(questionnaire);
		    question.setAnswers(null);
	        return question;
		} catch (PersistenceException e) {
			throw new Exception("Could not insert question");
		}     
	}
	
	
	public List<Question> findAll() throws Exception{
		List<Question> questions = null;
	try {
		questions = em.createNamedQuery("Answer.findAll", Question.class).getResultList();
		return questions;
	} catch (PersistenceException e) {
		throw new Exception("Could not retrieve questions");
	}
}
	
	public Question find(int questionId) throws Exception{
		  return em.find(Question.class, questionId);
		}

}
