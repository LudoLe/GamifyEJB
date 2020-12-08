package polimi.db2.gamifyDB.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the Answer database table.
 * 
 */
@Entity
@NamedQuery(name="Answer.findAll", query="SELECT a FROM Answer a")
public class Answer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="answer_id")
	private int answerId;

	private String content;

	//bi-directional many-to-one association to Question
	@ManyToOne
	@JoinColumn(name="question_id")
	private Question question;

	//bi-directional many-to-one association to Review
	@ManyToOne
	@JoinColumn(name="review_id")
	private Review review;

	public Answer() {
	}

	public int getAnswerId() {
		return this.answerId;
	}

	public void setAnswerId(int answerId) {
		this.answerId = answerId;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Question getQuestion() {
		return this.question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public Review getReview() {
		return this.review;
	}

	public void setReview(Review review) {
		this.review = review;
	}

}