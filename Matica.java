package gaussjordan;


/**
 *
 * @author Misho
 */


public class Matica
{
    // atributy triedy Matica
    private int pocRia;        // aktualny pocet riadkov matice
    private int pocStl;        // aktualny pocet stlpcov matice (bez stlpca pravych stran)
    private double [][] pole;  // dvojrozmerne pole prvkov matice
    private int [] baza;       // baza - su tu ulozene indexy stlpcov, kde sa nachadza pivotovy prvok pre prislusny riadok 


    /**
     * Konstruktor triedy Matica - podiela sa na vytvarani instancii, inicializuje atributy
     * @param  paPocRia   maximalny (rezervovany) pocet riadkov matice
     * @param  paPocStl   maximalny (rezervovany) pocet stlpcov matice
     */
    public Matica(int paPocRia, int paPocStl)  // parametre - maximalny pocet riadkov a stlpcov matice
    {
        pocRia = paPocRia;                   // inicializuje pocet riadkov
        pocStl = paPocStl;                   // inicializuje pocet stlpcov
        pole = new double [pocRia][pocStl];  // vytvori pole = sustava rovnic - bude obsahovat 0.0
        baza = new int [pocRia];             // vytvori bazu - bude obsahovat 0
    }


    /**
     * Pocet riadkov matice
     * @return vrati aktualny pocet riadkov matice
     */
    public int pocetRiadkov()
    {
        return pocRia;
    }


    /**
     * Pocet stlpcov matice
     * @return vrati aktualny pocet stlpcov matice
     */
    public int pocetStlpcov()
    {
        return pocStl;
    }

    
    /**
     * Retazcova reprezentacia matice
     */
    public String toString()
    {
        String vysledok = "";
        for (int i=0; i<pocRia; i++)    //pre vsetky riadky
          {
          for (int j=0; j<pocStl; j++)  //pre vsetky stlpce
            vysledok = vysledok + String.format("%7.2f",pole[i][j]);
//          vysledok = vysledok + "\n";          // prida iba #10(LF) - nestaci pri ukladani do suboru
          vysledok = vysledok + '\015'+'\012';    // prida #13(CR) aj #10(LF)
          }
        return vysledok;
    }

    /**
     * Zobrazenie matice v terminalovom okne
     */
    public void zobraz()
    {
        for (int i=0; i<pocRia; i++)    //pre vsetky riadky
          {
          for (int j=0; j<pocStl+1; j++)  //pre vsetky stlpce
            System.out.printf("%7.2f",pole[i][j]);
          System.out.println();
          }
        System.out.println();
    }


    /**
     * Nacitanie rozmerov matice a prvkov matice z klavesnice
     */
    public void nacitajZKlavesnice()
    {
        java.util.Scanner citac = new java.util.Scanner(System.in);
        System.out.print("Pocet riadkov: ");
        pocRia = citac.nextInt();
        System.out.print("Pocet stlpcov (bez stlpca pravych stran): ");
        pocStl = citac.nextInt();
        for (int i=0; i<pocRia; i++)    //pre vsetky riadky
          for (int j=0; j<pocStl+1; j++)  //pre vsetky stlpce
            {
            System.out.print("Prvok["+i+","+j+"]=");
            pole[i][j] = citac.nextDouble();
            }
    }

    /**
     * Nacitanie rozmerov matice a prvkov matice zo suboru
     * @param  subor   nazov textoveho suboru, z ktoreho sa nacitavaju data
     */
    public void nacitajZoSuboru(String nazovSuboru) throws java.io.FileNotFoundException
    {
        // vytvorenie novej instancie triedy Scanner pre citanie z textoveho suboru
        java.util.Scanner citac = new java.util.Scanner(new java.io.File(nazovSuboru));
        pocRia = citac.nextInt();             // nacita cele cislo zo suboru
        pocStl = citac.nextInt();
        for (int i=0; i<pocRia; i++)          //pre vsetky riadky
          for (int j=0; j<pocStl+1; j++)      //pre vsetky stlpce
            pole[i][j] = citac.nextDouble();  // nacitanie realneho cisla zo suboru
        citac.close();
    }


    /**
     * Eliminacna metoda - postupujte podla navodu (nevymienajte riadky ani stlpce, uprava nad aj pod pivotom)
     */
    public void GJE()
    {
        for (int i = 0; i < pocetRiadkov(); i++) {
            boolean testPivotu = false;
            for (int j = 0; j < pocetStlpcov() + 1; j++) {
                if(pole[i][j] < 0.000000000001 )
                    pole[i][j] = 0;
                if(pole[i][j] != 0)
                {
                    testPivotu = true;
                    baza[i]=j;
                    if(j == pocetStlpcov())
                        return;
                    double hodnotaPivotu = pole[i][j];
                    for (int k = j; k < pocetStlpcov()+1; k++) {
                        pole[i][k] /= hodnotaPivotu;
                    }
                    for (int k = 0; k < pocetRiadkov(); k++) {
                        if( k != i) {
                            double tempHodnota = - pole[k][j];
                            for (int l = j; l < pocetStlpcov()+1; l++) {
                                pole[k][l] = pole[k][l] + tempHodnota*pole[i][l];
                            }
                        }
                    }
                    this.zobraz();
                    break;
                }
            }
            if(!testPivotu)
                baza[i]=-1;
        }
    
    }

    public int PocetNenulovych(int IndexRiadku)
    {
        int pocet = 0;
        for (int j=0; j<pocStl+1; j++)
        {
            if (pole[IndexRiadku][j]!=0) pocet++;
        }
        return pocet;
    }

   
    public void VyhodnotRiesenie()
    {
        int pocetNNR = 0;
        for (int i = 0; i < pocetRiadkov(); i++) {
            if(baza[i] == pocetStlpcov())
            {
                System.out.println("Nema riesenie!");
                return;
            }
            if(PocetNenulovych(i)!= 0)
                ++pocetNNR;
        }
        if(pocetNNR < pocetStlpcov())
            System.out.println("Sustava ma nekonecne vela rieseni!");
        else if (pocetNNR == pocetStlpcov())
        {
            System.out.println("Sustava ma prave jedno riesenie!");
            int stlpecVysledku = pocetStlpcov();
            for (int i = 0; i < pocetRiadkov(); i++) {
                System.out.println("x" + (i+1) + "= " + pole[i][stlpecVysledku]);
            }
        }
    }
    

}

