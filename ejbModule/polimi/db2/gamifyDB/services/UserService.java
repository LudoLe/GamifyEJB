package polimi.db2.gamifyDB.services;

import javax.persistence.PersistenceException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.NonUniqueResultException;
import polimi.db2.gamifyDB.entities.User;
import polimi.db2.gamifyDB.entities.Review;
import java.util.List;
import java.util.Date;

@Stateless
public class UserService{
	@PersistenceContext(unitName = "GamifyEJB")
	private EntityManager em;

	public UserService(){
	}

	
	public User checkCredentials(String usrn, String pwd) throws Exception, NonUniqueResultException {
		List<User> uList = null;
		try {
			uList = em.createNamedQuery("User.checkCredentials", User.class).setParameter(1, usrn).setParameter(2, pwd)
					.getResultList();
		} catch (PersistenceException e) {
			throw new Exception("Could not verify credentals");
		}
		if (uList.isEmpty())
			return null;
		else if (uList.size() == 1)
			return uList.get(0);
		throw new NonUniqueResultException("More than one user registered with same credentials");

	}
	
	public List<User> getUsers() throws Exception{
			List<User> uList = null;
		try {
			uList = em.createNamedQuery("User.getUsers", User.class).getResultList();
			return uList;
		} catch (PersistenceException e){
			throw new Exception("Could not retrieve users");
		}
	}
	
	public User findUserById(int userId) throws Exception{
		
	try {
		User user = new User();
		user = em.createNamedQuery("User.findUserbyId", User.class).setParameter(1, userId).getSingleResult();
		return user;
	}catch (PersistenceException e){
		throw new Exception("Could not retrieve answers for this product" + userId);
	}
}
	
	public int createUser(String usrn, String pwd, String email)throws Exception{
		try{
		  User user = new User();
	        user.setUsername(usrn);
	        user.setPasswordHash(pwd);
	        user.setEmail(email);
	        user.setAdmin((byte) 0);
	        em.persist(user);
	        em.flush();
	        return user.getUserId();
		} catch (PersistenceException e) {
			throw new Exception("Could not insert user");
		}
	       

	}
	public User getUserWhoSubmitted(){}
	public User getUserWhoCancelled(){}

	public void updateProfile(User u) throws UpdateProfileException {
		try {
			em.merge(u);
		} catch (PersistenceException e) {
			throw new UpdateProfileException("Could not change profile");
		}
	}
}