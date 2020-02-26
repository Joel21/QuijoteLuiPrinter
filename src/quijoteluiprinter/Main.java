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
        imprimirFacturaPDF();
    }

    static void imprimirFacturaPDF() {
        LiquidacionCompraPDF pdf = new LiquidacionCompraPDF("/data/git/QuijoteLuiPrinter/recursos/reportes",
                "/data/git/QuijoteLuiPrinter/recursos/imagenes/logo.jpeg",
                "/app/QuijoteLui/comprobante/pdf");

        pdf.genera("/app/QuijoteLui/comprobante/generado/"
                + "2502202003100245687700110010020000000021234567815.xml",
                "2502202003100245687700110010020000000021234567815",
                "01/01/0001 00:00:00");
    }
    
}
