package polimi.db2.gamifyDB.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the Question database table.
 * 
 */
@Entity
@NamedQuery(name="Question.findAll", query="SELECT q FROM Question q")
@NamedQuery(name="Answer.findQuestionsByProductId", query="SELECT q FROM Question q WHERE q.questionaire_id=?")
public class Question implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="question_id")
	private int questionId;

	private String content;

	//bi-directional many-to-one association to Answer
	@OneToMany(mappedBy="question")
	private List<Answer> answers;

	//bi-directional many-to-one association to Questionaire
	@ManyToOne
	@JoinColumn(name="questionaire_id")
	private Questionaire questionaire;

	public Question() {
	}

	public int getQuestionId() {
		return this.questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<Answer> getAnswers() {
		return this.answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

	public Answer addAnswer(Answer answer) {
		getAnswers().add(answer);
		answer.setQuestion(this);

		return answer;
	}

	public Answer removeAnswer(Answer answer) {
		getAnswers().remove(answer);
		answer.setQuestion(null);

		return answer;
	}

	public Questionaire getQuestionaire() {
		return this.questionaire;
	}

	public void setQuestionaire(Questionaire questionaire) {
		this.questionaire = questionaire;
	}

}