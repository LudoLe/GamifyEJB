package polimi.db2.gamifyDB.services;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.NonUniqueResultException;
import it.polimi.db2.mission.entities.User;
import it.polimi.db2.mission.exceptions.*;
import java.util.List;

@Stateless
public class UserService{
	@PersistenceContext(unitName = "GamifyEJB")
	private EntityManager em;

	public UserService(){
	}

	
	public User checkCredentials(String usrn, String pwd) throws CredentialsException, NonUniqueResultException {
		List<User> uList = null;
		try {
			uList = em.createNamedQuery("User.checkCredentials", User.class).setParameter(1, usrn).setParameter(2, pwd)
					.getResultList();
		} catch (PersistenceException e) {
			throw new CredentialsException("Could not verify credentals");
		}
		if (uList.isEmpty())
			return null;
		else if (uList.size() == 1)
			return uList.get(0);
		throw new NonUniqueResultException("More than one user registered with same credentials");

	}
	
	public List<User> getUsers(){
			List<User> uList = null;
		try {
			uList = em.createNamedQuery("User.getUsers", User.class).getResultList();
			return uList;
		} catch (PersistenceException e) {
			throw new Exception("Could not retrieve users");
		}
		else return null;
	}
	
	public User findUserById(int userId){
		User user = null;
	try {
		user = em.createNamedQuery("User.findUserbyId", User.class).setParameter(1, productId).getResultList();
		return answers;
	} catch (PersistenceException e) {
		throw new Exception("Could not retrieve answers for thi product" + productId);
	}
	else return null;
}
	
	public void createUser(String usrn, String pwd, String email){
		try{
		  User user = new User();
	        user.setUsername(username);
	        user.setPassword(password);
	        user.setEmail(email);
	        user.setAdmin((byte) 0);
	        em.persist(user);
	        em.flush();
	        return user.getId();
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