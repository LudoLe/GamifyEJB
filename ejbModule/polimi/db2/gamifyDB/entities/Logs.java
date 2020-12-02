package polimi.db2.gamifyDB.entities;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import polimi.db2.gamifyDB.entities.Logs.LogsPK;

@Entity
@IdClass(LogsPK.class)
@Table(name = "Logs", schema = "GamifyDB")
@NamedQuery(name = "Logs.findAll", query = "SELECT l FROM Logs l")
public class Logs implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User userId;

	@Id
	@Column(name="product_id")
	private int productId;

	@Temporal(TemporalType.DATE)
	private Date timestamp;
	
	private boolean submit;
	
	

	public Logs(){}

	public Logs(User userId, int productId, Date timestamp, boolean submit) {
		this.userId = userId;
		this.productId = productId;
		this.timestamp = timestamp;
		this.submit = submit;

	}
		
	
	public User getUserId() {
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
	
	
	public class LogsPK implements Serializable {
	    @Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getEnclosingInstance().hashCode();
			result = prime * result + productId;
			result = prime * result + userId;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			LogsPK other = (LogsPK) obj;
			if (!getEnclosingInstance().equals(other.getEnclosingInstance()))
				return false;
			if (productId != other.productId)
				return false;
			if (userId != other.userId)
				return false;
			return true;
		}

		/**
		 * 
		 */
		//private static final long serialVersionUID = 1L;
		protected int userId;
	    protected int productId;

	    public LogsPK() {}

	    public LogsPK(int userId, int productId) {
	        this.userId = userId;
	        this.productId = productId;
	    }

		private Logs getEnclosingInstance() {
			return Logs.this;
		}
	}
	
}

