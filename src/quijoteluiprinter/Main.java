package quijoteluiprinter;

import com.quijotelui.printer.pdf.*;

/**
 *
 * @author jorgequiguango
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        imprimir();
    }

    static void imprimir() {
        System.out.println("QuijoteLui Printer");
        
        ClassLoader classLoader = Main.class.getClassLoader();

        String pathXmlFile = classLoader.getResource("xml/0304202401999999999999920010040001166661234567813.xml").getPath();        
        String pathReportsFolders = classLoader.getResource("reportes").getPath();   
        String pathPdf = classLoader.getResource("pdf").getPath();  
        String pathLogo = classLoader.getResource("imagenes/logo.jpeg").getPath(); 
        
        System.out.println(pathLogo);
        
        
        FacturaPDF pdf = new FacturaPDF(
                pathReportsFolders, 
                pathLogo, 
                pathPdf);


        pdf.genera(pathXmlFile,
                "0304202401999999999999920010040001166661234567813",
                "01/01/0001 00:00:00");
    }
}
