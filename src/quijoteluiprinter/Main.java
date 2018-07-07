package quijoteluiprinter;

import com.quijotelui.printer.pdf.FacturaPDF;

/**
 *
 * @author jorgequiguango
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        imprimirFacturaPDF();
    }

    static void imprimirFacturaPDF() {
        FacturaPDF pdf = new FacturaPDF("./recursos/reportes",
                "/data/jaque_mate/1002704334001/imagen/logo.jpeg",
                "/data/jaque_mate/1002704334001/pdf");

        pdf.genera("/data/jaque_mate/1002704334001/xml/"
                + "2006201801100270433400120010020000002341234567819.xml",
                "2006201801100270433400120010020000002341234567819",
                "01/01/0001 00:00:00");
    }
    
}
