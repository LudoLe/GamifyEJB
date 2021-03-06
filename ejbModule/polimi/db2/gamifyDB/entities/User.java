package polimi.db2.gamifyDB.entities;

import java.io.Serializable;

import javax.persistence.*;
import com.google.gson.annotations.Expose;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the User database table.
 * 
 */
@Entity
@Table(name="`User`")
@NamedQueries({
@NamedQuery(name="User.findAll", query="SELECT u FROM User u"),
@NamedQuery(name="User.findAllNoBlockedNoAdmin", query="SELECT u FROM User u WHERE u.blocked = 0 AND u.admin = 0"),
@NamedQuery(name="User.findByUsername", query="SELECT u FROM User u WHERE u.username=?1"),
@NamedQuery(name="User.getHashByUserId", query="SELECT u.passwordHash FROM User u WHERE u.userId=?1"),
@NamedQuery(name="User.exists", query="SELECT count(u) FROM User u WHERE u.username=?1 OR u.email=?2"),
})
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="user_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Expose
	private int userId;

	private int admin;

	@Temporal(TemporalType.DATE)
	@Expose
	private Date birth;

	private int blocked;

	private String email;

	@Column(name="password_hash")
	private String passwordHash;

	@Expose
	private String sex;

	@Expose
	private String username;

	//bi-directional many-to-one association to Log
	@OneToMany(mappedBy="user")
	private List<Log> logs;

	//bi-directional many-to-one association to Review
	@OneToMany(mappedBy="user")
	private List<Review> reviews;

	public User() {
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getAdmin() {
		return this.admin;
	}

	public void setAdmin(int admin) {
		this.admin = admin;
	}

	public Date getBirth() {
		return this.birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public int getBlocked() {
		return this.blocked;
	}

	public void setBlocked(int blocked) {
		this.blocked = blocked;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasswordHash() {
		return this.passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<Log> getLogs() {
		return this.logs;
	}

	public void setLogs(List<Log> logs) {
		this.logs = logs;
	}

	public Log addLog(Log log) {
		getLogs().add(log);
		log.setUser(this);

		return log;
	}

	public Log removeLog(Log log) {
		getLogs().remove(log);
		log.setUser(null);

		return log;
	}

	public List<Review> getReviews() {
		return this.reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	public Review addReview(Review review) {
		getReviews().add(review);
		review.setUser(this);

		return review;
	}

	public Review removeReview(Review review) {
		getReviews().remove(review);
		review.setUser(null);

		return review;
	}

}