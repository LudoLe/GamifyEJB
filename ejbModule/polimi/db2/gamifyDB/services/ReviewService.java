package polimi.db2.gamifyDB.services;

import javax.persistence.PersistenceException;


import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import polimi.db2.gamifyDB.entities.User;
import polimi.db2.gamifyDB.entities.Answer;
import polimi.db2.gamifyDB.entities.Questionnaire;
import polimi.db2.gamifyDB.entities.Review;
import java.util.List;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Stateless
public class ReviewService {
	@EJB(name = "gamifyDB.services/UserService")
	private UserService userService;
	@EJB(name = "gamifyDB.services/AnswerService")
	private AnswerService answerService;
	
	@PersistenceContext(unitName = "GamifyEJB")
	private EntityManager em;
	

	public ReviewService(){
	}
	
	public boolean checkIfAlreadySubmitted(User user, Questionnaire questionnaire) throws Exception{
		try {
			//em.getEntityManagerFactory().getCache().evictAll();
			return em.createNamedQuery("Review.findIfUserAlreadySubmitted", String.class).setParameter(1, user).setParameter(2, questionnaire).setMaxResults(1).getSingleResult().contentEquals("1");
		} catch (NoResultException e) {
			return true;
		}

	}
	
	public Review createReview(int canAccessAge, int canAccessSex, Date date, String expertise, User user, Questionnaire questionnaire, List<Answer> answers, String sex, Date birth) throws Exception{
		Review review = null;
		try{
			System.out.println("createReview");
			review= new Review();	 		  
		    review.setCanAccessAge(canAccessAge);
		    review.setCanAccessSex(canAccessSex);
		    review.setDatetime(date);
		    review.setExpertise(expertise);
		    review.setQuestionnaire(questionnaire);
		    review.setUser(user);
	        review.setAnswers(answers);

	        answerService.createAnswers(answers, review);
	        user = em.merge(user);
		    user.setSex(sex);
		    user.setBirth(birth);
		    List<Review> oldReviews = user.getReviews();
		    oldReviews.add(review);
		    user.setReviews(oldReviews);
		    questionnaire = em.merge(questionnaire);
		    oldReviews = questionnaire.getReviews();
		    oldReviews.add(review);
		    questionnaire.setReviews(oldReviews);
	        em.persist(review);

	        em.flush();
		} catch (PersistenceException e) {
			em.clear();
			throw new Exception("Could not insert question");
		}

        return review;
	}
	
	public Review find(int reviewId) throws Exception{
		  return em.find(Review.class,reviewId);
		}
	
	public List<Review> findAll() throws Exception{
		List<Review> reviews = null;
		try {
			reviews = em.createNamedQuery("Review.findAll", Review.class).getResultList();
			return reviews;
		} catch (PersistenceException e) {
			throw new Exception("Could not retrieve questions");	}

	}
	public List<Review> findAllToday() throws Exception{
		List<Review> reviews = null;
		try {
			//em.getEntityManagerFactory().getCache().evictAll();
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String strDate = dateFormat.format(new Date());
            String strStart = strDate+" 00:00:00";
            String strEnd = strDate+" 23:59:59";
            Date start = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(strStart);
            Date end = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(strEnd);
			reviews = em.createNamedQuery("Review.findAllOnDate", Review.class).setParameter(1, start).setParameter(2, end).getResultList();
			
			return reviews;
		} catch (PersistenceException e) {
			throw new Exception("Could not retrieve questions");
		}

	}
	public List<Review> findTodayFirst(int maxResult) throws Exception{
		List<Review> reviews = null;
		try {
			//em.getEntityManagerFactory().getCache().evictAll();
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String strDate = dateFormat.format(new Date());
            String strStart = strDate+" 00:00:00";
            String strEnd = strDate+" 23:59:59";
            Date start = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(strStart);
            Date end = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(strEnd);
			
			
			reviews = em.createNamedQuery("Review.findAllOnDate", Review.class).setParameter(1, start).setParameter(2, end).getResultList();
			if(reviews.size() > maxResult)
			    reviews = reviews.subList(0, maxResult);
			
			return reviews;
		} catch (PersistenceException e) {
			throw new Exception("Could not retrieve questions");
		}

	}
}
