package polimi.db2.gamifyDB.entities;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the Questionaire database table.
 * 
 */
@Entity
@Table(name="`Questionnaire`")
@NamedQuery(name="Questionnaire.findAll", query="SELECT q FROM Questionnaire q")

public class Questionnaire implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="questionnaire_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int questionnaireId;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date datetime;

	private String image;

	private String name;

	//bi-directional many-to-one association to Log
	@OneToMany(mappedBy="questionnaire")
	private List<Log> logs;

	//bi-directional many-to-one association to Question
	@OneToMany(mappedBy="questionnaire")
	private List<Question> questions;

	//bi-directional many-to-one association to Review
	@OneToMany(mappedBy="questionnaire")
	private List<Review> reviews;

	public Questionnaire() {
	}

	public int getQuestionnaireId() {
		return this.questionnaireId;
	}

	public void setQuestionnaireId(int questionnaireId) {
		this.questionnaireId = questionnaireId;
	}

	public String getImage() {
		return this.image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Log> getLogs() {
		return this.logs;
	}

	public void setLogs(List<Log> logs) {
		this.logs = logs;
	}
	
	public void setDate(Date date) {
		this.datetime = date;
	}
	
	public Date getDate() {
		return this.datetime;
	}

	public Log addLog(Log log) {
		getLogs().add(log);
		log.setQuestionnaire(this);

		return log;
	}

	public Log removeLog(Log log) {
		getLogs().remove(log);
		log.setQuestionnaire(null);

		return log;
	}

	public List<Question> getQuestions() {
		return this.questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public Question addQuestion(Question question) {
		getQuestions().add(question);
		question.setQuestionnaire(this);

		return question;
	}

	public Question removeQuestion(Question question) {
		getQuestions().remove(question);
		question.setQuestionnaire(null);

		return question;
	}

	public List<Review> getReviews() {
		return this.reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	public Review addReview(Review review) {
		getReviews().add(review);
		review.setQuestionnaire(this);

		return review;
	}

	public Review removeReview(Review review) {
		getReviews().remove(review);
		review.setQuestionnaire(null);

		return review;
	}

}