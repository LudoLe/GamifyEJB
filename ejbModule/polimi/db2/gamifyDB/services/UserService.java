package polimi.db2.gamifyDB.services;

import javax.persistence.PersistenceException;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import polimi.db2.gamifyDB.entities.User;
import polimi.db2.gamifyDB.entities.Question;
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

	
	public User checkCredentials(String username, String password) throws Exception {
		User user = em.createNamedQuery("User.findByUsername", User.class).setParameter(1, username).getSingleResult();
		if(user == null) throw new Exception();
		Argon2 argon2 = Argon2Factory.createAdvanced(Argon2Factory.Argon2Types.ARGON2id);
		boolean passed = false;
		try {
			String salt = user.getPasswordSalt();
			if(salt != null) {
				 String hash = argon2.hash(ARGON2_ITERATIONS,ARGON2_MEMORY,2,password.concat(salt).toCharArray(),StandardCharsets.UTF_8);
				 String db_hash = user.getPasswordHash();
				 passed = (hash == db_hash);
			}
		} catch(Exception e) {
			passed = false;
		}
		if(!passed) throw new Exception();
		else return user;


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
	
	public User find(int userId) throws Exception{
		  return em.find(User.class, userId);
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
	public void getUserWhoSubmitted(){}
	public void getUserWhoCancelled(){}

	public void updateProfile(User u) throws Exception{
		try {
			em.merge(u);
		} catch (PersistenceException e) {
			throw new Exception("Could not change profile");
		}
	}
}