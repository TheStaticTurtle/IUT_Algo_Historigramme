import java.util.*;

/**
 * Exo n3: Nombre d'occurence dans un texte
 * @author TUGLER Samuel
 * @version 2.6
 */
class Graph{
    static final Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.print("Entrer une phrase: ");


        String text = "";   //Il y'a surement un meuilleur moyen de recuperrer l'entrer utilisateur mais se code fonctione quand on pipe un fichier (java Graph < DATA.data)
        try {
        	String d = "?";
        	while (true){
                text += input.nextLine();
        	}  
        } catch(java.util.NoSuchElementException e) {}

        char[] characters = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','u','r','s','t','v','w','x','y','z','é','è','’','.',',',';','!','?',':'};
        int[] occurences = compteOccurences(text,characters);
        // print(occurences,characters);
        graph(occurences,characters);
    }


    /**
	 * Compte les occurences d'un charactere dans une chaine
	 * @param text       Le text
	 * @param characters Liste des characteres a tester
	 * @return int[]     Nomre d'occurennce pour chaque charactere
	 */
    public static int[] compteOccurences(String texte, char[] characters) {
        texte = texte.toLowerCase();
        int occurences[] = new int[characters.length];
        for (int i =0; i< texte.length(); i++) {
            char c = texte.charAt(i);
            for (int j=0; j< characters.length; j++) {
            	if(c == characters[j]) {
            		occurences[j] = occurences[j] +1;
            	}
            }
        }
        return occurences;
    }

    /**
	 * Calcul de mise a l'echelle
	 * Voir l'appendix ici:
	 *   https://www.arduino.cc/reference/en/language/functions/math/map/
	 * @param x   Valeur 
	 * @param in_min   Valeur minimale de x
	 * @param in_max   Valeur maximale de x
	 * @param out_min  Valeur minimale de sortie
	 * @param out_max  Valeur maximale de sortie
	 * @return int Valeur modifiée
	 */
    public static int map(int x, int in_min, int in_max, int out_min, int out_max) {
      return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }

    /**
	 * Automatiquement ajuster la taille d'un chaine (Seulement pour agrandir)
	 * @param text   Texte de base
	 * @param fill   Remplir le reste par ce char
	 * @param len    Taille finale de la chaine
	 * @return String texte ajuster
	 */
    public static  String fill(String text, char fill, int len) {
        while(text.length() < len) {
        	text += fill;
        }
        return text;
    }


    /**
	 * Affiche le nombre d'occurence pour chaque clé
	 * @param table   Liste des occurences
	 * @param keys    Nom de chaque occurence
	 * @return void
	 */
    public static void print(int[] table, char keys[]) {
        for (int i=0; i<keys.length; i++) {
            char character = keys[i];
            System.out.println("La lettre "+character+" est apparue "+table[i]+"fois.");
        }
    }

    /**
	 * Affiche un graphique en couleur dans le terminal
	 * @param table   Liste des occurences
	 * @param keys    Nom de chaque occurence
	 * @return void
	 */
    public static void graph(int[] table, char keys[]) {
        int maxHeight = 40;         //Hauteur max dans le terminal
        int minCharSpace = 3;       //Largeur de colone minimale (Peu etre modifier automatiquement si besion)
        int[] fadeValues = {70,255}; //Valeur du degrader {min,max}  => 255 au maximum
        Boolean spaceBetweenColums = false;

        int maxY = table[0];
        for (int i=0; i<table.length; i++) {                                                  //Cherche le nb d'occurence max ainsi que 
            if(maxY < table[i]) {
                maxY = table[i];
            }

            int len = String.valueOf(table[i]).length();                                      //Ainsi que le texte le plus long pour le calcul de largeur de colone maximale
            if(len > minCharSpace) {
            	minCharSpace = len;
            }
        }

        String data = "\n";                                                                    //Ajout saut de ligne (Pour faire joli quand il y'a une redirection du stdin)

        for (int y=maxHeight+2; y>=0; y--) {                                                   //Iteration a travers les lignes
			for (int x=0; x<table.length; x++) {                                               //Puis par les colones
				int end = map(table[x],0,maxY,0,maxHeight);                                    //Calcul de la ligne maimale pour x dans table (Adaptaion a l'ecrant)
				if (y == 0) {                                                                  //Si c'est la derniere ligne 
		            data += "\033[38;2;255;255;255m" + fill(""+keys[x],' ',minCharSpace);      //Afficher la cle de la colone corespondante
				} else if ( y == end+2) {                                                      //Si haute max
		            data += "\033[38;2;255;255;255m" + fill(""+table[x],' ',minCharSpace);     //Afficher le nb d'occurence
		        } else if(y < end+2) {                                                         //SI en dessous de la limite 
		        	int g = map(Math.min(y,maxHeight),0,maxHeight,fadeValues[0],fadeValues[1]);//Calcul degrader de la couleur en fonction de la hauteur
            		data += "\033[38;2;255;"+ g +";0m"+fill("",'█',minCharSpace);              //Afficher un charactere block (ref https://en.wikipedia.org/wiki/Box-drawing_character)
		        } else {                                                                       //Sinon
		        	data += fill("",' ',minCharSpace);                                         //Afficher un espace pour combler le vide
		        }

		        if(spaceBetweenColums) {
		        	data += " ";
		        }
			}
            data+="\n";                                                                        //Ligne suivante
        }
        System.out.println(data);
    }
}



