package polimi.db2.gamifyDB.entities;

import java.io.Serializable;
import javax.persistence.*;

import com.google.gson.annotations.Expose;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the Review database table.
 * 
 */
@Entity
@Table(name="`Review`")
@NamedQueries({
@NamedQuery(name="Review.findAll", query="SELECT r FROM Review r"),
@NamedQuery(name="Review.findById", query="SELECT r FROM Review r WHERE r.reviewId=?1"),
@NamedQuery(name="Review.findByUserAndQuestionnaire", query="SELECT r FROM Review r WHERE r.questionnaire=?1 AND r.user=?2"),
@NamedQuery(name="Review.findIfUserAlreadySubmitted", query="select case when exists(select r from Review r where r.user = ?1 AND r.questionnaire = ?2) then '1' else '0' end from Review r1"),
@NamedQuery(name="Review.findAllOnDate", query="SELECT r FROM Review r WHERE r.datetime >= ?1 AND r.datetime <= ?2 ORDER BY r.datetime DESC")})

public class Review implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="review_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int reviewId;

	@Column(name="can_access_age")
	private int canAccessAge;

	@Column(name="can_access_sex")
	private int canAccessSex;

	@Temporal(TemporalType.TIMESTAMP)
	private Date datetime;

	private String expertise;

	@Column(name="points_first_section")
	@Expose
	private int pointsFirst;
	
	@Column(name="points_second_section")
	@Expose
	private int pointsSecond;


	//bi-directional many-to-one association to Answer
	@OneToMany(mappedBy="review", fetch = FetchType.EAGER)
	@Expose
	private List<Answer> answers;

	//bi-directional many-to-one association to Questionnaire
	@ManyToOne
	@JoinColumn(name="questionnaire_id")
	private Questionnaire questionnaire;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="user_id")
	@Expose
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
		return this.pointsFirst + this.pointsSecond;
	}

	public void setPoints(Integer pointsFirst, Integer pointsSecond) {
		if(pointsFirst != null) this.pointsFirst = pointsFirst;
		if(pointsSecond != null) this.pointsSecond = pointsSecond;
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

	public void setQuestionnaire(Questionnaire questionnaireId) {
		this.questionnaire = questionnaireId;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}