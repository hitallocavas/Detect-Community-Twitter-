package grafo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import com.opencsv.CSVWriter;

import grafo.Status;
import grafo.*;

public class principal {
	
	 static List<Status> statuses = new ArrayList<Status>();
     static String textos = "";
     static int qtdTextos = 0;
     static List<Frequence> frequences = new ArrayList<Frequence>();
     static List<Term> terms = new ArrayList<Term>();
     static List<String> stopwords = new ArrayList<String>();
     static List<List<String>> docs = new ArrayList<List<String>>();
     static List<Status> statuses2 = new ArrayList<Status>();
     
     
     static double tf(List<String> doc, String term) {
         double result = 0;
         for (String word : doc) {
             if (term.equalsIgnoreCase(word))
                 result++;
         }
         return result / doc.size();
     }

    
     static double idf(List<List<String>> docs, String term) {
         double n = 0;
         for (List<String> doc : docs) {
             for (String word : doc) {
                 if (term.equalsIgnoreCase(word)) {
                     n++;
                     break;
                 }
             }
         }
         return Math.log(docs.size() / n);
     }

     static double tfIdf(List<String> doc, List<List<String>> docs, String term) {
         return tf(doc, term) * idf(docs, term);

     } 
     
     
     static void readCSV() throws FileNotFoundException{
 		
 		// Lendo o CSV de Estoque
 		@SuppressWarnings("resource")
		Scanner scan = new Scanner(new File("data.csv")).useDelimiter(";|\\n");
 		
 		// Pegando a primeira linha
 		scan.nextLine();
 		
 		// Varrendo o Documento e adicionando cada Produto na Lista de Produtos
 		int tl = 0;
 		while (scan.hasNext()) {
 		
 			//Coletando Atributos
 			String status = scan.next();
 			System.out.println(status);
 		    String user = scan.next();
 		    textos = status + textos;
 		    qtdTextos += 1;
 		    
 		    //Declarando produto com atributos coletados anteriormente 
 		    Status st = new Status();
 		    
 		    st.setNomeUsuario(user);
 		    st.setId(tl);
 		    
 		    
 		    //Adicionando na Lista
 		   
 		    status = status.replace("RT", "");
 		    status = status.replace("@", "");
 		    status = status.replace("http", "");
 		    status = status.replace(".", "");
 		    status = status.replace(",", "");
 		    status = status.replace(";", "");
 		    status = status.replace(":", "");
 		    status = status.replace("#", "");
 		    status = status.replace("-", "");
 		    status = status.replace("  ", " ");
 		    status = status.toLowerCase();
 		    st.setTweet(status);
 		    statuses.add(st);
 		    tl++;
 		}
 		
 		}
	
	
		static void readFrequences()throws FileNotFoundException{
			// Lendo o CSV de Estoque
	 		@SuppressWarnings("resource")
			Scanner scan = new Scanner(new File("freq.csv")).useDelimiter(",|\\n");
	 		
	 		// Pegando a primeira linha
	 		scan.nextLine();
	 		
	 		// Varrendo o Documento e adicionando cada Produto na Lista de Produtos
	 		int tl = 0;
	 		while (scan.hasNext()) {
	 		
	 			//Coletando Atributos
	 			String text = scan.next();
	 			int freq = Integer.parseInt(scan.next().trim());
	 		    
	 		    //Declarando produto com atributos coletados anteriormente 
	 		    Frequence frequencia = new Frequence();
	 		    
	 		    frequencia.setFreq(freq);
	 		    frequencia.setTerm(text);
	 		    frequences.add(frequencia);
	 		    tl++;
	 		
	 		}
		}
	
		
	static void readStopWords() throws FileNotFoundException {
		Scanner scan = new Scanner(new File("stopwords.txt"));
		while(scan.hasNext()) {
			stopwords.add(scan.next().toLowerCase());
		}
	}
	
	static void removeStopFrequences(){
		List<Frequence> freqlistaux = new ArrayList<Frequence>();
	    freqlistaux = frequences;
//		
		int fqcnt = 0;
		for (Frequence fq:frequences) {	
			for(String stop:stopwords) {
				if(stop.equalsIgnoreCase(fq.getTerm())) {
					
					if(stop.equalsIgnoreCase("julgar") || stop.equalsIgnoreCase("roubar") || stop.equalsIgnoreCase("ajudar")) {}
					else {
						frequences.get(fqcnt).setTerm("lalala");
					}
					}
				}
			fqcnt++;
			}
			
		
			for (Iterator<Frequence> i = frequences.iterator(); i.hasNext();) {
				Frequence fq = i.next();
				
				if(fq.getTerm().equals("lalala")) {
					i.remove();;	
				}
				
				else if(fq.getFreq() < 100) {
					i.remove();
				}
			}	
		
		
		frequences = freqlistaux;
		
	}
	
	static void updateDocs() {
		for(Status status:statuses) {
			docs.add(status.tweetToList());
		}
	}
	
	static void updateTerm() {
		
		for(Status status:statuses) {
			
			List<String> termos = status.tweetToList();
			
			String termoUp = "Other";
			double tfIdfAux = 0.2;
			
			for(String valor:termos) {
				valor = valor.toLowerCase();
				double tfidfNovo = tfIdf(termos, docs, valor);
				
				if(valor.equals("lulalivre")
				   ||valor.equals("bolsonaro")
				   ||valor.equals("justica")
				   ||valor.equals("justiça")
				   ||valor.equals("prisao")
				   ||valor.equals("prisão")
				   ||valor.equals("pt")
				   ||valor.equals("moro")
				   ||valor.equals("curitiba")
				   ||valor.equals("esquerda")
				   ||valor.equals("psol") 
				   ||valor.equals("direita")
				   ||valor.equals("livre")
				   ||valor.equals("liberdade")){
					
					tfIdfAux = 1.0;
					termoUp = valor;
				}
				
				else if(tfidfNovo > tfIdfAux) {
					tfIdfAux = tfidfNovo;
					termoUp = "OutroCluster: " +valor;
				}	
			
			}
			
			status.setTermo(termoUp);
			status.setPeso(tfIdfAux);
			statuses2.add(status);
			
			
		}
		
	}
	
	static void fazGrafo() throws IOException {
		PrintWriter pw1 = new PrintWriter(new File("nodes.csv"));
		PrintWriter pw = new PrintWriter(new File("edges.csv"));
		
		StringBuilder sb1 = new StringBuilder();
		sb1.append("id");
        sb1.append(',');
        sb1.append('\n');
        pw1.write(sb1.toString());
        
        sb1 = new StringBuilder();
		
		
		StringBuilder sb = new StringBuilder();
		sb.append("source");
        sb.append(',');
        sb.append("target");
        sb.append(',');
        sb.append("weight");
        sb.append(',');
        sb.append("category");
        sb.append('\n');
        pw.write(sb.toString());
        sb = new StringBuilder();
		
		
		List<No> nos = new ArrayList<No>();
		List<Aresta> arestas = new ArrayList<Aresta>();
		for (Status st:statuses) {
			No no = new No();
			no.setDescricao(st.getTweet());
			no.setId(st.getId());
		    sb1.append(st.getId());
		    sb1.append('\n');

		}
		
        pw1.write(sb1.toString());
		pw1.close();
		
		for (Status st:statuses2){
			
			for (Status st1:statuses2) {
				
				if(st == st1) {}
				else {
					if(st.getTermo().equals(st1.getTermo())) {
						Aresta e = new Aresta();
						e.setSource(Integer.toString(st.getId()));
						e.setTarget(Integer.toString(st1.getId()));
						e.setCategory(st.getTermo());
						e.setPeso(st.getPeso());
						arestas.add(e);
					}
				}
				
			}
			
		}
		

		for (Aresta e:arestas) {
	        
	        sb.append("\""+e.getSource()+"\"");
	        sb.append(',');
	        sb.append("\""+e.getTarget()+"\"");
	        sb.append(',');
	        sb.append(e.getPeso());
	        sb.append(',');
	        sb.append("\"" +e.getCategory()+"\"");
	        sb.append('\n');

	        pw.write(sb.toString());
	       
	        System.out.println(e.getSource());
			
			}
	   
		 pw.close();
		
	
	}
	

	public static void main(String[] args) throws FileNotFoundException  {
		// TODO Auto-generated method stub
		
			readFrequences();
		
			readStopWords();
	
			readCSV();
		
		    removeStopFrequences();
		    
		    updateDocs();
		    
		    updateTerm();
		    
		    try {
				fazGrafo();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
		}

	}


