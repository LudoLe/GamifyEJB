package polimi.db2.gamifyDB.services;

import javax.persistence.PersistenceException;


import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import polimi.db2.gamifyDB.entities.User;
import polimi.db2.gamifyDB.entities.Answer;
import polimi.db2.gamifyDB.entities.Questionnaire;
import polimi.db2.gamifyDB.entities.Review;
import java.util.List;
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
	
	public Review createReview(int canAccessAge, int canAccessSex, Date date, String expertise, User user, Questionnaire questionnaire, List<Answer> answers, String sex, Date birth) throws Exception{
		Review review = null;
		try{
			review= new Review();	 		  
		    review.setCanAccessAge(canAccessAge);
		    review.setCanAccessSex(canAccessSex);
		    review.setDatetime(date);
		    review.setExpertise(expertise);
		    review.setQuestionnaire(questionnaire);
		    review.setUser(user);
		    user.setSex(sex);
		    user.setBirth(birth);
		    
		    userService.updateProfile(user);
	        em.persist(review);
	        for(Answer answer: answers) {
	 		    answer.setReview(review);
	        }
	        answerService.createAnswers(answers);

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

}
