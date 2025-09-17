import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;


public class Main {

  public static void main(String args[]){

    try{

      File file= new File(args[0]);
                  

      Scanner sc = new Scanner(file);
      Graph graphL = new Graph(sc); 
      
      graphL.constructionReseau();
      //graphL.affichage_RT();
      graphL.CalculFlotMax();
      graphL.CalulCoupeMin();
      //graphL.affichagecoupestest();
      graphL.ResoudreBinIm(" ", "*");


      sc.close();
      } catch(Exception e) {
      e.printStackTrace();
  
  
    }
  }
}