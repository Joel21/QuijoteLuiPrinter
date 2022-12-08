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
        FacturaPDF pdf = new FacturaPDF(
                "/home/jogue/Projects/QuijoteLuiPrinter/recursos/reportes", 
                "/app/Quijotelui/recursos/imagenes/logo.jpeg", 
                "/app/Quijotelui/comprobante/pdf/2022/10");
        

        pdf.genera("/home/jogue/Documents/mrjoy/"
                + "0712202201179308292000110059990000000031234567817.xml",
                "0712202201179308292000110059990000000031234567817",
                "01/01/0001 00:00:00");
    }
}
