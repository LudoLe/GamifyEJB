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


	
	protected class PointsPK implements Serializable {
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
	}
	
}

