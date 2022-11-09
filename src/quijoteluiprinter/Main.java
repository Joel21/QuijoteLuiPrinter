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
        RetencionPDF pdf = new RetencionPDF(
                "/home/jogue/Projects/QuijoteLuiPrinter/recursos/reportes", 
                "/app/Quijotelui/recursos/imagenes/logo.jpeg", 
                "/app/Quijotelui/comprobante/pdf/2022/10");
        

        pdf.genera("/app/Quijotelui/comprobante/generado/2022/10/"
                + "3110202207109170775200110010020000106161234567815.xml",
                "3110202207109170775200110010020000106161234567815",
                "01/01/0001 00:00:00");
    }
}
