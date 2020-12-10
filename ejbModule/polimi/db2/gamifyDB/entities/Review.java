package polimi.db2.gamifyDB.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the Review database table.
 * 
 */
@Entity
@NamedQueries({
@NamedQuery(name="Review.findAll", query="SELECT r FROM Review r"),
@NamedQuery(name="Review.findById", query="SELECT r FROM Review r WHERE r.reviewId=?1")})

public class Review implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="review_id")
	private int reviewId;

	@Column(name="can_access_age")
	private int canAccessAge;

	@Column(name="can_access_sex")
	private int canAccessSex;

	@Temporal(TemporalType.TIMESTAMP)
	private Date datetime;

	private String expertise;

	private int points;

	//bi-directional many-to-one association to Answer
	@OneToMany(mappedBy="review")
	private List<Answer> answers;

	//bi-directional many-to-one association to Questionnaire
	@ManyToOne
	@JoinColumn(name="questionnaire_id")
	private Questionnaire questionnaire;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;

	public Review() {
	}

	public int getReviewId() {
		return this.reviewId;
	}

	public void setReviewId(int reviewId) {
		this.reviewId = reviewId;
	}

	public int getCanAccessAge() {
		return this.canAccessAge;
	}

	public void setCanAccessAge(int canAccessAge) {
		this.canAccessAge = canAccessAge;
	}

	public int getCanAccessSex() {
		return this.canAccessSex;
	}

	public void setCanAccessSex(int canAccessSex) {
		this.canAccessSex = canAccessSex;
	}

	public Date getDatetime() {
		return this.datetime;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}

	public String getExpertise() {
		return this.expertise;
	}

	public void setExpertise(String expertise) {
		this.expertise = expertise;
	}

	public int getPoints() {
		return this.points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public List<Answer> getAnswers() {
		return this.answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

	public Answer addAnswer(Answer answer) {
		getAnswers().add(answer);
		answer.setReview(this);

		return answer;
	}

	public Answer removeAnswer(Answer answer) {
		getAnswers().remove(answer);
		answer.setReview(null);

		return answer;
	}

	public Questionnaire getQuestionnaire() {
		return this.questionnaire;
	}

	public void setQuestionnaire(Questionnaire questionnaire) {
		this.questionnaire = questionnaire;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}