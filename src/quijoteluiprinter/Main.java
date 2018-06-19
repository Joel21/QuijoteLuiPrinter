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
                "/data/git/QuijoteLui/app/QuijoteLuiPrinter/recursos/imagenes/logo.jpeg",
                "/data/Quijotelui/comprobante/pdf");

        pdf.genera("/data/Quijotelui/comprobante/generado/"
                + "1712201701100245687700110010030030048911234567813.xml",
                "1712201701100245687700110010030030048911234567813",
                "01/01/0001 00:00:00");
    }
    
}
