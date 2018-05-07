package grafo;

import java.util.ArrayList;
import java.util.List;

public class Grafo {
	
	private List<Aresta> arestas = new ArrayList<Aresta>();
	private List<No> nos = new ArrayList<No>();
	
	public Grafo() {
		
	}

	public List<Aresta> getArestas() {
		return arestas;
	}

	public List<No> getNos() {
		return nos;
	}

	public void setArestas(List<Aresta> arestas) {
		this.arestas = arestas;
	}

	public void setNos(List<No> nos) {
		this.nos = nos;
	}
	
	
	

}
