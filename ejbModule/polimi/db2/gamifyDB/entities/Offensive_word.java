package polimi.db2.gamifyDB.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the Offensive_word database table.
 * 
 */
@Entity
@NamedQuery(name="Offensive_word.findAll", query="SELECT o FROM Offensive_word o")
public class Offensive_word implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String word;

	public Offensive_word() {
	}

	public String getWord() {
		return this.word;
	}

	public void setWord(String word) {
		this.word = word;
	}

}