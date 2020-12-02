package polimi.db2.gamifyDB.entities;
import java.io.Serializable;
import javax.persistence.*;
import polimi.db2.gamifyDB.entities.Statistics.StatisticsPK;

@Entity
@IdClass(StatisticsPK.class)
@Table(name = "Statistics", schema = "GamifyDB")
@NamedQuery(name = "Statistics.findAll", query = "SELECT s FROM Statistics s")
public class Statistics implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="user_id")
	private int userId;
	@Id
	@Column(name="product_id")
	private int productId;

	private int sex;
	private int age;
	private int expertise;

	
	public Statistics(){}

	public Statistics(int userId, int productId, int sex, int age, int expertise) {
		this.userId = userId;
		this.productId = productId;
		this.sex = sex;
		this.age = age;
		this.expertise = expertise;
	}
		
	
	public int getUserId() {
		return userId;
	}
	
	public int getProductId() {
		return productId;
	}

	public int getSex() {
		return sex;
	}


	public void setSex(int sex) {
		this.sex = sex;
	}


	public int getAge() {
		return age;
	}


	public void setAge(int age) {
		this.age = age;
	}


	public int getExpertise() {
		return expertise;
	}


	public void setExpertise(int expertise) {
		this.expertise = expertise;
	}

	
	public class StatisticsPK implements Serializable {
	    /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		/**
		 * 
		 */
		//private static final long serialVersionUID = 1L;
		protected int userId;
	    protected int productId;

	    public StatisticsPK(){}

	    public StatisticsPK(int userId, int productId) {
	        this.userId = userId;
	        this.productId = productId;
	    }

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
			StatisticsPK other = (StatisticsPK) obj;
			if (!getEnclosingInstance().equals(other.getEnclosingInstance()))
				return false;
			if (productId != other.productId)
				return false;
			if (userId != other.userId)
				return false;
			return true;
		}

		private Statistics getEnclosingInstance() {
			return Statistics.this;
		}
	}
	
}

