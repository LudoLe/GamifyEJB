package polimi.db2.gamifyDB.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.eclipse.persistence.annotations.Cache;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "Leaderboard")
@NamedQueries({
@NamedQuery(name="Leaderboard.findAll", query="SELECT l FROM Leaderboard l")
})
public class Leaderboard implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Expose
	private String username;
	
	@Expose
	private int points;
	
	public Leaderboard() {
		
	};
	
}