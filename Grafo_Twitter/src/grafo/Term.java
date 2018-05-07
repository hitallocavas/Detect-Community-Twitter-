package grafo;

public class Term {

	private String term;
	private int id;
	private double tfIdf;
	
	
	
	public Term() {
		
	}

	public String getTerm() {
		return term;
	}

	public int getId() {
		return id;
	}

	public double getTfIdf() {
		return tfIdf;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setTfIdf(double tfIdf) {
		this.tfIdf = tfIdf;
	}

	
}
