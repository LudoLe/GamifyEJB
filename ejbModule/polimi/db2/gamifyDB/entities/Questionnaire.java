package polimi.db2.gamifyDB.entities;

import java.io.Serializable;
import javax.persistence.*;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the Questionaire database table.
 * 
 */
@Entity
@Table(name="`Questionnaire`")
@NamedQueries({
@NamedQuery(name="Questionnaire.existsOnDate", query="SELECT COUNT(q) FROM Questionnaire q WHERE q.datetime=?1"),
@NamedQuery(name="Questionnaire.getByDate", query="SELECT q FROM Questionnaire q WHERE q.datetime=?1"),
@NamedQuery(name="Questionnaire.listOrdered", query="SELECT q FROM Questionnaire q ORDER BY q.datetime ASC"),
@NamedQuery(name="Questionnaire.listPast", query="SELECT q FROM Questionnaire q WHERE q.datetime < CURRENT_DATE ORDER BY q.datetime ASC"),
@NamedQuery(name="Questionnaire.findByName", query="SELECT q FROM Questionnaire q WHERE q.name LIKE ?1"),
@NamedQuery(name="Questionnaire.findByNameBeforeToday", query="SELECT q FROM Questionnaire q WHERE q.name LIKE ?1 AND q.datetime < CURRENT_DATE"),
}) 
public class Questionnaire implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="questionnaire_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Expose
	private int questionnaireId;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Expose
	private Date datetime;

	@Expose
	private String image;

	@Expose
	private String name;

	//bi-directional many-to-one association to Log
	@OneToMany(mappedBy="questionnaire", fetch = FetchType.LAZY)
	private List<Log> logs;

	//bi-directional many-to-one association to Question
	@OneToMany(mappedBy="questionnaire", fetch = FetchType.LAZY)
	private List<Question> questions;

	//bi-directional many-to-one association to Review
	@OneToMany(mappedBy="questionnaire", fetch = FetchType.LAZY)
	private List<Review> reviews;

	public Questionnaire() {
		questions = new ArrayList<>();
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