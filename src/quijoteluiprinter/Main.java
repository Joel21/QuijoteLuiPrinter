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
        NotaDebitoPDF pdf = new NotaDebitoPDF(
                "/home/jorge/Projects/QuijoteLuiPrinter/recursos/reportes",
                "/app/QuijoteLui/logo.jpeg",
                "/app/QuijoteLui/comprobante/pdf");

        pdf.genera("/app/QuijoteLui/comprobante/generado/"
                + "1808202105109170775200110010040000000011234567813.xml",
                "1808202105109170775200110010040000000011234567813",
                "01/01/0001 00:00:00");
    }
}
