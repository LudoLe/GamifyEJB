package polimi.db2.gamifyDB.services;

import javax.persistence.PersistenceException;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import polimi.db2.gamifyDB.entities.User;
import java.util.List;

@Stateless
public class UserService{
	
	// I set this parameters to low, pretty unsafe values so login is faster
	private static final int ARGON2_ITERATIONS = 2;
	private static final int ARGON2_MEMORY = 16384;
	private static final int ARGON2_PARALLELISM = 1;
	private static final int ARGON2_SALT_LENGTH = 64;
	private static final int ARGON2_HASH_LENGTH = 128;


	
	@PersistenceContext(unitName = "GamifyEJB")
	private EntityManager em;

	public UserService(){
	}

	
	public User checkCredentials(String username, String password) throws Exception {
		User user;
		try {
			user = em.createNamedQuery("User.findByUsername", User.class).setParameter(1, username).getSingleResult();
		} catch (Exception e) {
			return null;
		}
		if(user == null) throw new Exception();
		boolean passed = false;
        Argon2PasswordEncoder encoder = new Argon2PasswordEncoder(ARGON2_SALT_LENGTH, ARGON2_HASH_LENGTH, ARGON2_PARALLELISM, ARGON2_MEMORY, ARGON2_ITERATIONS);
		try {
			passed = encoder.matches(password, user.getPasswordHash());
		} catch(Exception e) {
			passed = false;
		}
		return passed ? user : null;


	}
	
	
	public List<User> findAll() throws Exception{
			List<User> uList = null;
		try {
			uList = em.createNamedQuery("User.findAll", User.class).getResultList();
			return uList;
		} catch (PersistenceException e){
			throw new Exception("Could not retrieve users");
		}
	}
	
	public User find(int userId) throws Exception{
		  return em.find(User.class, userId);
		}
	
	public User createUser(String usrn, String pwd, String email) throws Exception{
		try{
			//checks that username and email aren't already in use
		  if(em.createNamedQuery("User.exists", Long.class).setParameter(1, usrn).setParameter(2, email).getSingleResult() >= 1) return null;
		  User user = new User();
	        user.setUsername(usrn);
	        Argon2PasswordEncoder encoder = new Argon2PasswordEncoder(ARGON2_SALT_LENGTH, ARGON2_HASH_LENGTH, ARGON2_PARALLELISM, ARGON2_MEMORY, ARGON2_ITERATIONS);
	        String hash = encoder.encode(pwd);				
	        user.setPasswordHash(hash);
	        user.setEmail(email);
	        user.setAdmin((byte) 0);
	        em.persist(user);
	        em.flush();
	        return user;
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