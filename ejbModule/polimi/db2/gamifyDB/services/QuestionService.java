package polimi.db2.gamifyDB.services;
import javax.persistence.PersistenceException;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import polimi.db2.gamifyDB.entities.Question;
import polimi.db2.gamifyDB.entities.Questionnaire;


@Stateless
public class QuestionService {
	
	@PersistenceContext(unitName = "GamifyEJB")
	private EntityManager em;

	public QuestionService(){
	}
	
	public Question createQuestion(String content, Questionnaire questionnaire) throws Exception{
		System.out.println("createQuestion");
		try{
			Question question = new Question();
		    question.setContent(content);
		    question.setQuestionnaire(questionnaire);
		    question.setAnswers(null);
		    /*
		    System.out.println("half createQuestion");
		    try {
		    	questionnaire = em.merge(questionnaire);
		    }catch(Exception e) {
		    	System.out.println(e.getMessage());
		    }*/
		    System.out.println(questionnaire.getQuestions());
		    System.out.println("half createQuestion");
		    List<Question> oldQuestions = questionnaire.getQuestions();
		    oldQuestions.add(question);
		    questionnaire.setQuestions(oldQuestions);
		    System.out.println("done createQuestion");
	        return question;
		} catch (PersistenceException e) {
			throw new Exception("Could not insert question");
		}     
	}
	
	
	public Question find(int questionId) throws Exception{
		  return em.find(Question.class, questionId);
		}

}
