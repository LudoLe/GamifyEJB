package polimi.db2.gamifyDB.entities;
import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "Answer", schema = "GamifyDB")
@NamedQuery(name = "Answer.findAll", query = "SELECT a FROM Answer a")
public class Answer implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="question_id")
	private int questionId;
	@Column(name="product_id")
	private int productId;
	private String content;

	
	public Answer(){}
	public Answer(int productId, String content, int questionId){
		this.questionId=questionId;
		this.productId=productId;
		this.content=content;
	}

		
	public int getId() {
		return id;
	}


	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	
	
	
}

