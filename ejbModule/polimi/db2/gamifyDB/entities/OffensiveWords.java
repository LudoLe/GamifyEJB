package polimi.db2.gamifyDB.entities;
import java.io.Serializable;
import javax.persistence.*;
import polimi.db2.gamifyDB.entities.Statistics.StatisticsPK;

@Entity
@Table(name = "Offensive_words", schema = "GamifyDB")
@NamedQuery(name = "Offensive_words.findAll", query = "SELECT o FROM OffensiveWords o")
public class OffensiveWords implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	private String word;
	
	
	public OffensiveWords(){}
	public OffensiveWords(String word){
		this.word=word;
	}

	public String getContent() {
		return word;
	}

	public void setContent(String word) {
		this.word = word;
	}

	
	
	
}

