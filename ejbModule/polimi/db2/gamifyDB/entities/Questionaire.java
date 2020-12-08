package polimi.db2.gamifyDB.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the Questionaire database table.
 * 
 */
@Entity
@NamedQuery(name="Questionaire.findAll", query="SELECT q FROM Questionaire q")
public class Questionaire implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="questionaire_id")
	private int questionaireId;

	private String image;

	private String name;

	//bi-directional many-to-one association to Log
	@OneToMany(mappedBy="questionaire")
	private List<Log> logs;

	//bi-directional many-to-one association to Question
	@OneToMany(mappedBy="questionaire")
	private List<Question> questions;

	//bi-directional many-to-one association to Review
	@OneToMany(mappedBy="questionaire")
	private List<Review> reviews;

	public Questionaire() {
	}

	public int getQuestionaireId() {
		return this.questionaireId;
	}

	public void setQuestionaireId(int questionaireId) {
		this.questionaireId = questionaireId;
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

	public Log addLog(Log log) {
		getLogs().add(log);
		log.setQuestionaire(this);

		return log;
	}

	public Log removeLog(Log log) {
		getLogs().remove(log);
		log.setQuestionaire(null);

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
		question.setQuestionaire(this);

		return question;
	}

	public Question removeQuestion(Question question) {
		getQuestions().remove(question);
		question.setQuestionaire(null);

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
		review.setQuestionaire(this);

		return review;
	}

	public Review removeReview(Review review) {
		getReviews().remove(review);
		review.setQuestionaire(null);

		return review;
	}

}