package gaussjordan;

/**
 *
 * @author Misho
 */
public class Main
{

	public static void main(String [] args) throws java.io.FileNotFoundException
	{

	Matica mat;
	mat = new Matica(20,20);            //   vytvori novu nulovu maticu 20x20
	System.out.print('\f');             //   vymaze terminalove okno
//      mat.nacitajZKlavesnice();           //   citanie matice z klavesnice - NacitajMaticu(A,PocRia,PocStl);}
        mat.nacitajZoSuboru("prikladik.txt");    //   nacitanie suboru zo suboru
        
        System.out.println("Matica po nacitani:");
        mat.zobraz();

        mat.GJE();    //   TUTO METODU NAPROGRAMOVAT

        System.out.println("Matica po uprave:");
        mat.zobraz();
        
        mat.VyhodnotRiesenie();  //   TUTO METODU NAPROGRAMOVAT
        
        
	}
}

