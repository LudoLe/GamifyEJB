package polimi.db2.gamifyDB.services;

import javax.persistence.PersistenceException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import polimi.db2.gamifyDB.entities.Question;
import polimi.db2.gamifyDB.entities.Questionnaire;

import java.util.Date;
import java.util.List;


@Stateless
public class QuestionnaireService {
	
	@PersistenceContext(unitName = "GamifyEJB")
	private EntityManager em;

	public QuestionnaireService(){
	}
	
	public Questionnaire createQuestionnaire(String image, String name, Date date, List<String> questions) throws Exception{
		Questionnaire questionnaire = new Questionnaire();
		try{		   
			questionnaire.setImage(image);
			questionnaire.setName(name);
			questionnaire.setDate(date);
	        em.persist(questionnaire);
		} catch (PersistenceException e) {
			throw new Exception("Could not insert questionnaire");
		}    
		try {
			QuestionService questionService = new QuestionService();
			for(String question : questions) {
				Question q = questionService.createQuestion(question, questionnaire);
				em.persist(q);
			}
			em.flush();
	        return questionnaire;
		} catch (Exception e) {
			em.clear();
			throw new Exception("Could not insert question");
		} finally {
			em.clear();
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

