package polimi.db2.gamifyDB.entities;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "Logs", schema = "GamifyDB")
@NamedQuery(name = "Logs.findAll", query = "SELECT l FROM Logs l")
public class Logs implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="user_id")
	private int userId;

	@Column(name="product_id")
	private int productId;

	@Temporal(TemporalType.DATE)
	private Date timestamp;
	
	private boolean submit;

	public Logs(){}

	public Logs(int userId, int productId, Date timestamp, boolean submit) {
		this.userId = userId;
		this.productId = productId;
		this.timestamp = timestamp;
		this.submit = submit;

	}
		
	
	public int getUserId() {
		return userId;
	}
	
	public int getProductId() {
		return productId;
	}

	public Date getTimeStamp() {
		return timestamp;
	}


	public void setTimeStamp(Date timestamp) {
		this.timestamp = timestamp;
	}


	public boolean getExpertise() {
		return submit;
	}


	public void setExpertise(boolean submit) {
		this.submit = submit;
	}
	
}

