package polimi.db2.gamifyDB.entities;
import java.io.Serializable;
import javax.persistence.*;


@Entity
@Table(name = "User", schema = "GamifyDB")
@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
public class User implements Serializable {
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
	
	private Boolean blocked;

	public User(){
	}

	public User(String username, String email, String password) {
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
	
	public boolean getBlocked() {
		return this.blocked;
	}
	
	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}
	

	public String getPasswordSalt() {
		return this.passwordSalt;
	}


	public String getPasswordHash() {
		return this.passwordHash;
	}

}
