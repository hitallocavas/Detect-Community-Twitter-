package grafo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Status {
	
	private String nomeUsuario;

	private String tweet;
	
	private int id;
	
	private String termo;
	
	private double peso;

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

	public String getTermo() {
		return termo;
	}

	public void setTermo(String termo) {
		this.termo = termo;
	}

	public Status() {
		
	}
	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}
	
	public List<String> tweetToList(){
		String[] splitedTweet = this.tweet.split(" ");
		List<String> list = new ArrayList<String>(Arrays.asList(splitedTweet));
		return list;
	}
	
	public  String getNomeUsuario() {
		return this.nomeUsuario;
	}

	
	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}
	
	public String getTweet() {
		return this.tweet;
	}

	public void setTweet(String tweet) {
		this.tweet = tweet;
	}
}
