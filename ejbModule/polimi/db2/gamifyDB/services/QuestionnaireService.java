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
public class QuestionnaireService {
	
	@PersistenceContext(unitName = "GamifyEJB")
	private EntityManager em;

	public QuestionnaireService(){
	}
	
	public int createQuestionnaire(String image, String name) throws Exception{
		try{
			Questionnaire questionnaire= new Questionnaire();
		   
			questionnaire.setImage(image);
			questionnaire.setName(name);
			//date
	        em.persist(questionnaire);
	        em.flush();
	        return questionnaire.getQuestionnaireId();
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
	
	public Questionnaire find(int questionnaireId) throws Exception{
	  return em.find(Questionnaire.class, questionnaireId);
	}

}

