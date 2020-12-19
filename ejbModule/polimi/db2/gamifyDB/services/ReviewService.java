package polimi.db2.gamifyDB.services;

import javax.persistence.PersistenceException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import polimi.db2.gamifyDB.entities.User;
import polimi.db2.gamifyDB.entities.Questionnaire;
import polimi.db2.gamifyDB.entities.Review;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;

@Stateless
public class ReviewService {
	
	@PersistenceContext(unitName = "GamifyEJB")
	private EntityManager em;

	public ReviewService(){
	}
	
	public int createReview(int canAccessAge, int canAccessSex,Date date, String expertise, int userId, int questionaireId) throws Exception{
		try{
		    Review review= new Review();
		 
		    User user=(new UserService()).find(userId);
		  
		    review.setCanAccessAge(canAccessAge);
		    review.setCanAccessSex(canAccessSex);
		    review.setDatetime(date);
		    review.setExpertise(expertise);
		    review.setUser(user);
		   
	        em.persist(review);
	        em.flush();
	        return review.getReviewId();
		} catch (PersistenceException e) {
			throw new Exception("Could not insert question");
		}     
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
