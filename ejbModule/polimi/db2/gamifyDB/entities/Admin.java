package polimi.db2.gamifyDB.entities;
import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "Admin", schema = "GamifyDB")
@NamedQuery(name = "Admin.findAll", query = "SELECT a FROM Admin a")
public class Admin implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String username;

	private String email;

	@Column(name="password_hash")
	private String passwordHash;
	
	@Column(name="password_salt")
	private String passwordSalt;
	
	public Admin(){
	}

	public Admin(String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.passwordSalt = password;
	}

	public int getId() {
		return this.id;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	
	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return this.email;
	}
	

	public String getPasswordSalt() {
		return this.passwordSalt;
	}


	public String getPasswordHash() {
		return this.passwordHash;
	}
	
}

