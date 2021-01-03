package polimi.db2.gamifyDB.services;

import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletResponse;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import polimi.db2.gamifyDB.entities.Answer;
import polimi.db2.gamifyDB.entities.Log;
import polimi.db2.gamifyDB.entities.Question;
import polimi.db2.gamifyDB.entities.Questionnaire;
import polimi.db2.gamifyDB.entities.Review;
import polimi.db2.gamifyDB.entities.User;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


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
			if(this.exists(date)) return null;
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
		questions = em.createNamedQuery("Answer.list", Question.class).getResultList();
		return questions;
	} catch (PersistenceException e) {
		throw new Exception("Could not retrieve questions");
	}
	}
	
	public boolean exists(Date date) {
	try {
		return em.createNamedQuery("Questionnaire.existsOnDate", Long.class).setParameter(1, date).getSingleResult() != 0;
	} catch (Exception e) {
		return false;
	}
}
	
	public List<User> getCompletedUsers(int id) throws Exception{
		Questionnaire questionnaire = this.find(id);
		if(questionnaire == null) return null;
		List<Review> reviews = questionnaire.getReviews();
		List<User> users = reviews.stream().map(review -> review.getUser()).distinct().collect(Collectors.toList());
		return users;
}
	
	public List<Questionnaire> getQuestionnairesFill(String name, boolean forDeletion) throws Exception{
		StringBuilder param = new StringBuilder();
		param.append('%');
		param.append(name);
		param.append('%');
		String queryString = "SELECT q FROM Questionnaire q WHERE q.name like ?1";
		
		if(forDeletion) queryString += " AND q.datetime < CURRENT_DATE";
		
		return em.createQuery(queryString, Questionnaire.class)
				.setMaxResults(10)
                .setParameter(1, param.toString())
                .getResultList();
}
	
	public List<User> getCanceledUsers(int id) throws Exception{
		Questionnaire questionnaire = this.find(id);
		if(questionnaire == null) return null;
		List<Log> logs = questionnaire.getLogs();
		List<User> users = logs.stream().map(log -> log.getUser()).distinct().collect(Collectors.toList());
		return users;
}
	
	
	public Questionnaire find(int questionnaireId) throws Exception{
	  return em.find(Questionnaire.class, questionnaireId);
	}
	
	public Questionnaire findByDate(Date today) throws Exception{
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String date=simpleDateFormat.format(today);
		Date todate=simpleDateFormat.parse(date);

		
		 return em
		                .createNamedQuery("Questionnaire.getByDate", Questionnaire.class)
		                .setParameter(1, todate)
		                .getSingleResult();
		    
		}

	
	public boolean delete(int questionnaireId) throws Exception{
		Questionnaire questionnaire = this.find(questionnaireId);
		if(questionnaire == null) return false;
		
		Date date = questionnaire.getDate();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date parsedDate = simpleDateFormat.parse(simpleDateFormat.format(new Date()));

		if(date.after(parsedDate) || date.equals(parsedDate)) {
			return false;
		}
		em.remove(questionnaire);
		em.flush();
		return true;
		}

	public List<Questionnaire> getQuestionnaires(int start, int size, boolean past) throws Exception {
		TypedQuery<Questionnaire> query;
		if(past) query = em.createNamedQuery("Questionnaire.listPast", Questionnaire.class);
		else query = em.createNamedQuery("Questionnaire.listOrdered", Questionnaire.class);
		query.setMaxResults(size);
		query.setFirstResult(start);
		return query.getResultList();
	}

	
}