package polimi.db2.gamifyDB.services;

import javax.persistence.PersistenceException;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.NonUniqueResultException;
import polimi.db2.gamifyDB.entities.User;
import polimi.db2.gamifyDB.entities.Review;
import java.util.List;
import java.nio.charset.StandardCharsets;
import java.util.Date;



@Stateless
public class UserService{
	
	private static final int ARGON2_ITERATIONS = 4;
	private static final int ARGON2_MEMORY = 65535;

	
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
	
	public boolean checkCredentials(int userId, String password) {
		User user = em.createNamedQuery("User.findById", User.class).setParameter(1, userId).getSingleResult();
		if(user == null) return false;
		Argon2 argon2 = Argon2Factory.createAdvanced(Argon2Factory.Argon2Types.ARGON2id);
		boolean passed = false;
		try {
			String salt = em.createNamedQuery("User.getUserSaltById", String.class).setParameter(1, userId).getSingleResult();
			if(salt != null) {
				 String hash = argon2.hash(ARGON2_ITERATIONS,ARGON2_MEMORY,2,password.concat(salt).toCharArray(),StandardCharsets.UTF_8);
				 String db_hash = em.createNamedQuery("User.getUserHashById", String.class).setParameter(1, userId).getSingleResult();
				 passed = (hash == db_hash);
			}
		} catch(Exception e) {
			passed = false;
		}
	    return passed;

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