package polimi.db2.gamifyDB.services;

import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import polimi.db2.gamifyDB.entities.Log;
import polimi.db2.gamifyDB.entities.Question;
import polimi.db2.gamifyDB.entities.Questionnaire;
import polimi.db2.gamifyDB.entities.Review;
import polimi.db2.gamifyDB.entities.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Stateless
public class QuestionnaireService {
	
	@PersistenceContext(unitName = "GamifyEJB")
	private EntityManager em;

	public QuestionnaireService(){
	}
	
	public Questionnaire createQuestionnaire(String image, String name, Date date, List<String> questions) throws Exception{
		System.out.println("createQuestionnaire");
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
		System.out.println("ok1");
		try {
			QuestionService questionService = new QuestionService();
			for(String question : questions) {
				Question q = questionService.createQuestion(question, questionnaire);
				em.persist(q);
			}
			em.flush();
			System.out.println("ok2");
	        return questionnaire;
		} catch (Exception e) {
			em.clear();
			throw new Exception("Could not insert question");
		} finally {
			em.clear();
		}
	}
	
	public boolean exists(Date date) {
		try {
			return em.createNamedQuery("Questionnaire.existsOnDate", Long.class).setParameter(1, date).getSingleResult() != 0;
		} catch (Exception e) {
			return false;
		}
	}
	
	public Set<User> getCompletedUsers(int id) throws Exception{
		Questionnaire questionnaire = this.find(id);
		if(questionnaire == null) return null;
		List<Review> reviews = questionnaire.getReviews();
		Set<User> users2 = new HashSet<User>();
		if(reviews == null) {
			System.out.println("reviews nullaaaa");
		}else {
			for(Review review: reviews) {
				System.out.println(review.getUser());
				users2.add(review.getUser());
			}
		}
		/*
		 * List<User> users = reviews.stream().map(review -> review.getUser()).distinct().collect(Collectors.toList());
		*/
		return users2;
	}
	
	public List<Questionnaire> getQuestionnairesFill(String name, boolean forDeletion) throws Exception{
		StringBuilder param = new StringBuilder();
		param.append('%');
		param.append(name);
		param.append('%');
		List<Questionnaire> ret;
		if(forDeletion) {
			@SuppressWarnings("unchecked")
			List<Questionnaire> resultList = em.createNamedQuery("Questionnaire.findByNameBeforeToday").setMaxResults(10)
					.setParameter(1, param.toString())
					.getResultList();
			ret =  resultList;
		}else {
			@SuppressWarnings("unchecked")
			List<Questionnaire> resultList = em.createNamedQuery("Questionnaire.findByName").setMaxResults(10)
            .setParameter(1, param.toString())
            .getResultList();
			ret =  resultList;
		}
		return ret;
}
	
	public Set<User> getCanceledUsers(int id) throws Exception{
		Questionnaire questionnaire = this.find(id);
		if(questionnaire == null) return null;
		List<Log> logs = questionnaire.getLogs();
		/*
		List<User> users = logs.stream().map(log -> log.getUser()).distinct().collect(Collectors.toList());
		*/
		Set<User> completed = getCompletedUsers(id);
		Set<User> users2 = new HashSet<User>();
		for(Log log: logs) {
			if(!completed.contains(log.getUser()))
				users2.add(log.getUser());
		}
		return users2;
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
		                .setParameter(1, todate).getResultList().stream().findFirst().orElse(null);
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