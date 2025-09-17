import java.util.Scanner;
import java.util.Stack;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Queue;


public class Graph {

	private int n; // lignes
	private int m; // colonnes
	private int size; // taille du graphe


	private int[][] A; // matricee des aij
	private int[][] B; // matrice des bij
	private int[][] Ph;// matrice des Phij (pénalités horizontales)
	private int[][] Pv;// matrice des Pvij (pénalités verticales)
	
	private Node[] adjlist;// Implémentation du réseau de transport dans une liste d'adjacence
	private boolean[] visitNodes;

	private NodeR[] residuel;// Implémentation du réseau résiduel dans une liste d'adjacence
	private boolean[] visitedR;  
	private int[] predecesseurs; // Pour la détermination des prédecesseurs lors du bfs

 	private QueueM file = new QueueM(); // pour l'implémentation du bfs
	
	private ArrayList<Integer> X; // Pour le calcul de la coupe min
	private ArrayList<Integer> Y; //

	private String[][] image; // Stockage de l'image dans une matrice
	private String[] arrayimage;


	public Graph(Scanner sc){
	
	    String[] firstline = sc.nextLine().split(" ");
	    this.n    = Integer.parseInt(firstline[0]);
        this.m    = Integer.parseInt(firstline[1]);
		this.size = n * m + 2;
		
		this.A    = new int[this.n][this.m];
		this.B    = new int[this.n][this.m];
		this.Ph   = new int[this.n][this.m-1];
		this.Pv   = new int[this.n-1][this.m]; 
		
		this.predecesseurs = new int[size];
		sc.nextLine();// Jumps a blank line
		
		for(int i=0;i<n;i++){
			String[] line = sc.nextLine().split(" ");		
			for(int j=0;j<m;j++){
				A[i][j] = Integer.parseInt(line[j]);
			}
		}

		// Affichage matrice A
		/*for(int i = 0;i<m;i++){
			for(int j=0;j<m;j++){
				System.out.print(A[i][j]);
				System.out.print(" ");
			}
			System.out.println();
		} */


		sc.nextLine(); // jumps a blank line
		
		for(int i=0;i<n;i++){
			String[] line = sc.nextLine().split(" ");
			//System.out.println(line.length);
			for(int j=0;j<m;j++){
				//System.out.print(line[j]);
				//System.out.print(" ");
				B[i][j] = Integer.parseInt(line[j]);
			}
			//System.out.println();
		}
		
		// Affichage matrice B
		/*for(int i = 0;i<m;i++){
			for(int j=0;j<m;j++){
				System.out.print(B[i][j]);
				System.out.print(" ");
			}
			System.out.println();
		}*/ 

		sc.nextLine(); // jumps a blank line

		for(int i=0;i<n;i++){
			String[] line = sc.nextLine().split(" ");
			for(int j=0;j<m-1;j++){
				//System.out.print(line[j]);
				//System.out.print(" ");
				Ph[i][j] = Integer.parseInt(line[j]);
			}
			//System.out.println();
		} 

		sc.nextLine(); // jumps a blank line
		for(int i=0;i<n-1;i++){	
			String[] line = sc.nextLine().split(" ");
			for(int j=0;j<m;j++){

				Pv[i][j] = Integer.parseInt(line[j]);

			}
	
		} 

	  	// Construction de la liste d'adjacence:
	  }
	  
	  //Construction du réseau de transport
	public void constructionReseau(){
		//Initialisation:
		this.adjlist  = new Node[this.n*this.m+2];	
		
		//Setting up the source:
		for(int i=0;i<this.n;i++){
			for(int j=0;j<this.m;j++){
				
				int sommet = j + m * i;
				Node p  = adjlist[0];
				Node p2 = new Node(sommet+1,null,A[i][j],0);
				// Parcours jusqu'à la fin de la liste chainée
				
				if(p == null){
					adjlist[0] = p2;
				}
				else{
					while(p.getNext() != null){
						p=p.getNext();
					}
					p.setNext(p2);

				}
			}
		}

		//Affichage:

		Node p=adjlist[0];

		for(int i=0;i<this.n;i++){
			for(int j=0;j<this.m-1;j++){
				int indice = j + m * i;				
				indice       = indice +1;
				int indicevoisin = indice +1;

				Node sommet       = new Node(indicevoisin,null,Ph[i][j],0);
				Node sommetvoisin = new Node(indice,null,Ph[i][j],0);

				Node p1 = adjlist[indice];
				Node p2 = adjlist[indicevoisin];
				
				if(p1 == null){
					adjlist[indice] = sommet;
				}
				else{
					while(p1.getNext() != null){
						p1=p1.getNext();
					}
					p1.setNext(sommet);
				}
				
				if(p2 == null){
					adjlist[indicevoisin] = sommetvoisin;
				}
				else{
					while(p2.getNext() != null){
						p2 = p2.getNext();
					}
					p2.setNext(sommetvoisin);
				}	

			}
		}

		//Arcs verticaux:
		for(int i=0;i<this.n-1;i++){
			for(int j=0;j<this.m;j++){
				int indice = j + m * i;				
				indice       = indice +1;
				int indicevoisin = indice + m;

				Node sommet       = new Node(indicevoisin,null,Pv[i][j],0);
				Node sommetvoisin = new Node(indice,null,Pv[i][j],0);

				Node p1 = adjlist[indice];
				Node p2 = adjlist[indicevoisin];
				
				if(p1 == null){
					adjlist[indice] = sommet;
				}
				else{
					while(p1.getNext() != null){
						p1=p1.getNext();
					}
					p1.setNext(sommet);
				}
				
				if(p2 == null){
					adjlist[indicevoisin] = sommetvoisin;
				}
				else{
					while(p2.getNext() != null){
						p2 = p2.getNext();
					}
					p2.setNext(sommetvoisin);
				}	

			}
		}


		//Target:
		int size    = this.n*this.m + 2;
		
		
		for(int i=0; i<n;i++){
			for(int j=0;j<m;j++){
			int indice  = j + m * i;
			Node sommet = new Node(size-1,null,B[i][j],0);

			Node p3 = adjlist[indice+1];
			
			while(p3.getNext() != null){
				p3=p3.getNext();
			}
			p3.setNext(sommet);
		
			}
		}


	} 

	 //Affichage du réseau de transport
	public void affichage_RT(){
		//AFFICHAGE:

		for(int i=0;i<this.n*this.m+2;i++){
			System.out.print(i);
			System.out.print(" ");
			Node ptest = adjlist[i];

			while(ptest != null){
				System.out.print(ptest.getVal());
				System.out.print("(");
				System.out.print(ptest.getFlow());
				System.out.print("/");
				System.out.print(ptest.getCapacity());
				System.out.print(")");
				System.out.print("  ");
				ptest=ptest.getNext();
			}
			System.out.println();
		} 
	}

	 /*
	 * construction du reseaux residuel associé
	 * 
	 * les arcs avec des capacités residuelles = 0 ne sont pas representé.
	 */
	
	public void constructionR() {
		this.visitNodes = new boolean[adjlist.length];
		// init
		this.residuel = new NodeR[this.n * this.m + 2];
		// init
		this.visitedR = new boolean[residuel.length];
		// init
		Node currNode = null;
		// init
		for (int i = 0; i < visitNodes.length; i++) {
			visitNodes[i] = false;
		}

		// parcours transport
		for (int i = 0; i < this.adjlist.length; i++) {
			if (!visitNodes[i]) { // si sommet i non traité
				currNode = this.adjlist[i]; // a traité
			}

			while (currNode != null) { // voisin de i
				// if (!visitNodes[currNode.getVal()]) { //

				// creation de l'arc i-q
				// calcul capa residuel pour arc i-q
				int capaRiq = currNode.getCapacity() - currNode.getFlow();
				NodeR iq = new NodeR(currNode.getVal(), null, capaRiq,1);
				
				// calcul capa residuel pour arc retour i-q (q-i)
				int capaRqi = currNode.getCapacity() - capaRiq;
				NodeR qi = new NodeR(i, null, capaRqi,-1);

				// ajout de l'arc i-q
				if (capaRiq != 0) {
					if (this.residuel[i] == null) {
						this.residuel[i] = iq;
					} else {
						NodeR suiv = this.residuel[i];
						while (suiv.getNext() != null) {
							suiv = suiv.getNext();

						}
						suiv.setNext(iq);
					}
				}

				// ajout de l'arc q-i
				if (capaRqi != 0) {
					int indiceq = currNode.getVal();
					if (this.residuel[indiceq] == null) {
						this.residuel[indiceq] = qi;
					} else {
						NodeR suiv = this.residuel[indiceq];
						while (suiv.getNext() != null) {
							suiv = suiv.getNext();

						}
						suiv.setNext(qi);
					}

				}

				// visitNodes[i] = true;
				// }

				currNode = currNode.getNext();

			}

		}

	}// fin construction residuel

	//Affichage du réseau résiduel
	public void affichage_RR(){
				// AFFICHAGE:
		
		for (int i = 0; i < this.n * this.m + 2; i++) {
			System.out.print(i);
			System.out.print(" ");
			NodeR ptest = residuel[i];

			while (ptest != null) {
				System.out.print(ptest.getVal());
				System.out.print("(");
				System.out.print(ptest.getCapacityR());
				System.out.print(")");
				System.out.print(" ");
				ptest = ptest.getNext();
			}
			System.out.println(); 
		}
	}

	//parcours bfs pour la recherche de chemin de la source au puit lors du Ford Fulkerson
	public int bfs(int sommet) {

		int capaciteResiduel=Integer.MAX_VALUE;//  +infinity

		Vertex source = new Vertex(sommet, null); // sommet 
		
		this.file = new QueueM();// empty queue


		file.enqueue(source); // ajoute sommet a la liste à traité

		while (!(file.isEmpty())) { // reste des sommet a traiter
			Vertex head = file.getFront(); // recupère le premier elt de la file
			file.dequeue(); // le retire de la file
			if (visitedR[head.getVal()] == false) { 
				this.visitedR[head.getVal()]=true; // marque le sommet comme étant visité

				NodeR temp = residuel[head.getVal()]; // premier voisin du head de la file				
								
				while (temp != null) {
					
					if(temp.getVal() == (this.size-1)){ // Si le voisin trouvé est le puit

						Vertex puit = new Vertex(temp.getVal(),null);
						this.predecesseurs[puit.getVal()]=head.getVal();// prédecesseur de t

						// Calcul de la capacité résiduelle:
						int j = puit.getVal();
						while(!(j == 0)){

							int     i   = predecesseurs[j];
							NodeR temp2 = residuel[i];
							
							while(temp2 != null){
								if(temp2.getVal() == j ){
									if(temp2.getCapacityR()<capaciteResiduel){
										capaciteResiduel = temp2.getCapacityR();
									}
									break;
								}
								temp2=temp2.getNext();
							}

							j = i;
 						}

						
						return capaciteResiduel;
					}

					if (visitedR[temp.getVal()] == false) {
						Vertex voisin = new Vertex(temp.getVal(),null);	
						file.enqueue(voisin); // ajout voisin dans a traiter
						this.predecesseurs[voisin.getVal()] = head.getVal(); // prédecesseurs
					}
					temp = temp.getNext(); // vas au voisin suivant

				} // fin while


			} // fin if
		} // fin while


		//pas de chemin vers le puit
		return -1;
	}// fin bfs	
	
    // Calul flot max ===>A la fin modifie le réseau de transport et le réseau résiduel
	public void CalculFlotMax() {
		constructionReseau();// construction du réseau de transport;
		constructionR();     // construction du réseau résiduel;
		int source = 0;
		while(true){
			//affichage_RT();
		
			for (int i = 0; i < this.visitedR.length; i++) {
				//Initialisation à chaque fois
				visitedR[i]      = false;
				predecesseurs[i] = -1;
			}

			int capaciteResiduel=0;
	
			capaciteResiduel=bfs(source); // bfs à partir de la source
			if(capaciteResiduel == -1){
				break;
			}

			//ajout du flux dans le chemin trouvé:
			int j=this.size-1; // target 
			while(!(j==0)){
				int i = predecesseurs[j];

				
				NodeR temp = residuel[i];
				while(temp != null){
					if(temp.getVal() == j ){
						//parcours du réseau de transport
						
						// (i,j) ===> on  ajoute sur (i,j)
						if(temp.getIsback()==1){
							Node temp2 = adjlist[i];
							while(temp2 != null){
								if(temp2.getVal()==j){
									temp2.setFlow(temp2.getFlow()+capaciteResiduel);
								}
								temp2=temp2.getNext();
							}
						}
						// (j,i)   ====> on retranche sur (i,j)
						if(temp.getIsback()==-1){
							Node temp2 = adjlist[j];
							while(temp2 != null){
								if(temp2.getVal()==i){
									temp2.setFlow(temp2.getFlow()-capaciteResiduel);
								}
								temp2=temp2.getNext();
							}
							
							
						}

					}
					temp = temp.getNext();
				}

				j=i;
			}

			constructionR();
		}
	}


	//Parcours dfs pour le calcul de la coupe minimum
	public ArrayList<Integer> dfs(int sommet,ArrayList<Integer> arList){
		
		this.visitedR[sommet] = true;
		arList.add(sommet);

		//parcours des voisins
		NodeR temp = residuel[sommet]; 
		while(temp != null){
			if(this.visitedR[temp.getVal()]==false){
				int index = temp.getVal();
				arList=dfs(index,arList);
			}
			temp=temp.getNext();
		}
		return arList;
	}



	public void CalulCoupeMin(){
		constructionR();
		//Définition des arraylists:
		this.X = new ArrayList<Integer>();
		this.Y = new ArrayList<Integer>();

		for(int i=0; i<this.size;i++){
			this.visitedR[i]=false;
		}

		int source=0;
		int target=this.size-1;
		
		this.X=dfs(source,X);
		this.Y=dfs(target,Y);
	
		

	}

	public void affichagecoupestest(){
		//Affichage X:
		

		System.out.print("Coupe X: ");
		int sizeX = this.X.size();
		
		for(int i=0;i<sizeX;i++){
			System.out.print(this.X.get(i)+" ");
		}
		

		System.out.println();
		//Affichage Y;
		System.out.print("Coupe Y: ");
		int sizeY = this.Y.size();
		
		for(int j=0;j<sizeY;j++){
			System.out.print(this.Y.get(j)+" ");
		}

		System.out.println();

	}

	public void traitement_image(String x,String y){
		this.image 		= new String[this.n][this.m];
		this.arrayimage = new String[this.n*this.m];

		int sizeX=this.X.size();
		for(int i=0;i<sizeX;i++){
			if(this.X.get(i) != 0){
				int index = this.X.get(i);
				this.arrayimage[index-1] = x;
			}
		}		

		int sizeY = this.Y.size();
		for(int j=0;j<sizeY;j++){
			if(this.Y.get(j) != this.size-1){
				int index 			   = this.Y.get(j);
				this.arrayimage[index-1] = y;
			}
		}

		int k=0;
		for(int i=0;i<this.n;i++){
			for(int j=0;j<this.m;j++){
				k=j + m * i;
				this.image[i][j]=this.arrayimage[k];
			}
		}

	}

	public void affichage_image(){
		for(int i=0;i<this.n;i++){
			for(int j=0;j<this.m;j++){
				System.out.print(this.image[i][j]);
			}
			System.out.println();
		}
	}



	public void ResoudreBinIm(String x,String y){
		traitement_image(x, y);
		affichage_image();
	}
	


	
	






	

	  
	  

	  
}
