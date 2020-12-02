package polimi.db2.gamifyDB.entities;
import java.awt.Image;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;


@Entity
@Table(name = "Product", schema = "GamifyDB")
@NamedQuery(name = "Product.findAll", query = "SELECT p FROM Product p")
public class Product implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;

	@Column(name="image_ext")
	private String imageExt;
	

	@Column(name="creator_id")
	private int creatorId;

	
	public Product(){
	}
	
	public Product(String name,  Image image,  int creatorId){
		this.name = name;
		//TODO prendere Image ed estrarre estensione da puttare in ImageExt
		this.imageExt = imageExt;
		this.creatorId = creatorId;
	}
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImageExt() {
		return imageExt;
	}

	public void setImageExt(String image_ext) {
		this.imageExt = image_ext;
	}

	public int getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(int creatorId) {
		this.creatorId = creatorId;
	}

}

