package polimi.db2.gamifyDB.entities;
import java.io.Serializable;
import javax.persistence.*;
import polimi.db2.gamifyDB.entities.Points.PointsPK;

@Entity
@IdClass(PointsPK.class)
@Table(name = "Points", schema = "GamifyDB")
@NamedQuery(name = "Points.findAll", query = "SELECT s FROM Points s")
public class Points implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="user_id")
	private int userId;
	@Id
	@Column(name="product_id")
	private int productId;

	private int points;

	
	public Points(){}

	public Points(int userId, int productId, int points) {
		this.userId = userId;
		this.productId = productId;
		this.points=points;
	}
		
	
	public int getUserId() {
		return userId;
	}
	
	public int getProductId() {
		return productId;
	}

	public int getPoints() {
		return points;
	}


	public void setPoints(int points) {
		this.points = points;
	}


	
	public class PointsPK implements Serializable {
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
			PointsPK other = (PointsPK) obj;
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

	    public PointsPK() {}

	    public PointsPK(int userId, int productId) {
	        this.userId = userId;
	        this.productId = productId;
	    }

		private Points getEnclosingInstance() {
			return Points.this;
		}
	}
	
}

