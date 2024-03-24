/*
 * Copyright (C) 2014 jorjoluiso
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.quijotelui.printer.pdf;

import com.quijotelui.printer.adicional.DetallesAdicionalesReporte;
import com.quijotelui.printer.utilidades.TipoImpuestoEnum;
import com.quijotelui.printer.utilidades.TipoImpuestoIvaEnum;
import com.quijotelui.printer.adicional.TotalComprobante;
import com.quijotelui.printer.factura.Factura;
import com.quijotelui.printer.factura.FacturaReporte;
import com.quijotelui.printer.parametros.Parametros;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * @author jorjoluiso
 */
public class FacturaPDF {

    String rutaArchivo;
    String directorioReportes;
    String directorioLogo;
    String directorioDestino;

    public FacturaPDF(String directorioReportes, String directorioLogo, String directorioDestino) {
        this.directorioReportes = directorioReportes;
        this.directorioLogo = directorioLogo;
        this.directorioDestino = directorioDestino;
    }

    public void genera(String rutaArchivo, String numeroAutorizacion, String fechaAutorizacion) {

        this.rutaArchivo = rutaArchivo;

        Factura f = xmlToObject();

        FacturaReporte fr = new FacturaReporte(f);
        generarReporte(fr, numeroAutorizacion, fechaAutorizacion);
    }

    private Factura xmlToObject() {
        Factura factura = null;
        try {
            File file = new File(this.rutaArchivo);
            JAXBContext jaxbContext = JAXBContext.newInstance(Factura.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            factura = (Factura) jaxbUnmarshaller.unmarshal(file);
//            System.out.println(factura);

        } catch (JAXBException ex) {
            Logger.getLogger(FacturaPDF.class.getName()).log(Level.SEVERE, null, ex);
        }
        return factura;

    }

    private void generarReporte(FacturaReporte xml, String numAut, String fechaAut) {

        generarReporte(this.directorioReportes + File.separator + "factura.jasper", xml, numAut, fechaAut);

    }

    private void generarReporte(String urlReporte, FacturaReporte fact, String numAut, String fechaAut) {
        Parametros p = new Parametros(this.directorioReportes, this.directorioLogo);
        FileInputStream is = null;
        try {
            JRDataSource dataSource = new JRBeanCollectionDataSource(fact.getDetallesAdiciones());
            is = new FileInputStream(urlReporte);
            JasperPrint reporte_view = JasperFillManager.fillReport(is,
                    obtenerMapaParametrosReportes(
                            p.obtenerParametrosInfoTriobutaria(fact.getFactura().getInfoTributaria(),
                                    numAut,
                                    fechaAut),
                            obtenerInfoFactura(fact.getFactura().getInfoFactura(),
                                    fact)),
                    dataSource);
            savePdfReport(reporte_view, fact.getFactura().getInfoTributaria().claveAcceso);
        } catch (FileNotFoundException | JRException /*| JRException */ex) {
            Logger.getLogger(FacturaPDF.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(FacturaPDF.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void savePdfReport(JasperPrint jp, String nombrePDF) {
        try {
            OutputStream output = new FileOutputStream(new File(this.directorioDestino + File.separatorChar + nombrePDF + ".pdf"));
            JasperExportManager.exportReportToPdfStream(jp, output);
            output.close();
            System.out.println("PDF: Guardado en " + this.directorioDestino + File.separatorChar + nombrePDF + ".pdf");
        } catch (JRException | FileNotFoundException ex) {
            Logger.getLogger(FacturaPDF.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FacturaPDF.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Map<String, Object> obtenerInfoFactura(Factura.InfoFactura infoFactura, FacturaReporte fact) {
        Map param = new HashMap();
        param.put("DIR_SUCURSAL", infoFactura.getDirEstablecimiento());
        param.put("CONT_ESPECIAL", infoFactura.getContribuyenteEspecial());
        param.put("LLEVA_CONTABILIDAD", infoFactura.getObligadoContabilidad());
        param.put("RS_COMPRADOR", infoFactura.getRazonSocialComprador());
        param.put("RUC_COMPRADOR", infoFactura.getIdentificacionComprador());
        param.put("FECHA_EMISION", infoFactura.getFechaEmision());
        param.put("GUIA", infoFactura.getGuiaRemision());
        TotalComprobante tc = getTotales(infoFactura);
        param.put("VALOR_TOTAL", infoFactura.getImporteTotal());
        param.put("DESCUENTO", infoFactura.getTotalDescuento());
        param.put("IVA_VALOR", tc.getIvaPorcentaje());
        param.put("IVA", tc.getIva12());
        param.put("IVA_0", tc.getSubtotal0());
        param.put("IVA_12", tc.getSubtotal12());
        param.put("ICE", tc.getTotalIce());
        param.put("IRBPNR", tc.getTotalIRBPNR());
        param.put("EXENTO_IVA", tc.getSubtotalExentoIVA());
        param.put("NO_OBJETO_IVA", tc.getSubtotalNoSujetoIva());
        param.put("SUBTOTAL", infoFactura.getTotalSinImpuestos().toString());
        if (infoFactura.getPropina() != null) {
            param.put("PROPINA", infoFactura.getPropina().toString());
        }
        param.put("TOTAL_DESCUENTO", calcularDescuento(fact));
        return param;
    }

    private String calcularDescuento(FacturaReporte fact) {
        BigDecimal descuento = new BigDecimal(0);
        for (DetallesAdicionalesReporte detalle : fact.getDetallesAdiciones()) {
            descuento = descuento.add(new BigDecimal(detalle.getDescuento()));
        }
        return descuento.toString();
    }

    private Map<String, Object> obtenerMapaParametrosReportes(Map<String, Object> mapa1, Map<String, Object> mapa2) {
        mapa1.putAll(mapa2);
        return mapa1;
    }

    private TotalComprobante getTotales(Factura.InfoFactura infoFactura) {
        BigDecimal totalIvaDiferenteDe0 = new BigDecimal(0.0D);
        BigDecimal totalIva0 = new BigDecimal(0.0D);
        BigDecimal ivaDiferenteDe0 = new BigDecimal(0.0D);
        BigDecimal totalICE = new BigDecimal(0.0D);
        BigDecimal totalExentoIVA = new BigDecimal(0.0D);
        BigDecimal totalIRBPNR = new BigDecimal(0.0D);
        BigDecimal totalSinImpuesto = new BigDecimal(0.0D);
        String ivaPorcentaje = "";
        TotalComprobante tc = new TotalComprobante();
        
        for (Factura.InfoFactura.TotalConImpuestos.TotalImpuesto ti : infoFactura.getTotalConImpuestos().getTotalImpuesto()) {
            Integer cod = new Integer(ti.getCodigo());

            if ((TipoImpuestoEnum.IVA.getCode() == cod.intValue()) &&
                    (
                            (TipoImpuestoIvaEnum.IVA_VENTA_12.getCode().equals(ti.getCodigoPorcentaje()))
                                    || (TipoImpuestoIvaEnum.IVA_VENTA_13.getCode().equals(ti.getCodigoPorcentaje()))
                                    || (TipoImpuestoIvaEnum.IVA_VENTA_14.getCode().equals(ti.getCodigoPorcentaje()))
                                    || (TipoImpuestoIvaEnum.IVA_VENTA_15.getCode().equals(ti.getCodigoPorcentaje()))
                                    || (TipoImpuestoIvaEnum.IVA_VENTA_5.getCode().equals(ti.getCodigoPorcentaje()))
                                    || (TipoImpuestoIvaEnum.IVA_DIFERENCIADO.getCode().equals(ti.getCodigoPorcentaje()))
                    )) {
                totalIvaDiferenteDe0 = totalIvaDiferenteDe0.add(ti.getBaseImponible());
                ivaDiferenteDe0 = ivaDiferenteDe0.add(ti.getValor());
                
                BigDecimal value = ti.getTarifa() == null ? new BigDecimal("-1") : ti.getTarifa();
                ivaPorcentaje = ivaPorcentaje + value.toBigInteger().toString() + "%";
            }
            if ((TipoImpuestoEnum.IVA.getCode() == cod.intValue()) && (TipoImpuestoIvaEnum.IVA_VENTA_0.getCode().equals(ti.getCodigoPorcentaje()))) {
                totalIva0 = totalIva0.add(ti.getBaseImponible());
            }
            if ((TipoImpuestoEnum.IVA.getCode() == cod.intValue()) && (TipoImpuestoIvaEnum.IVA_EXCENTO.getCode().equals(ti.getCodigoPorcentaje()))) {
                totalExentoIVA = totalExentoIVA.add(ti.getBaseImponible());
            }
            if (TipoImpuestoEnum.ICE.getCode() == cod.intValue()) {
                totalICE = totalICE.add(ti.getValor());
            }
            if (TipoImpuestoEnum.IRBPNR.getCode() == cod.intValue()) {
                totalIRBPNR = totalIRBPNR.add(ti.getValor());
            }
        }
        tc.setIvaPorcentaje(ivaPorcentaje);
        tc.setIva12(ivaDiferenteDe0.toString());
        tc.setSubtotal0(totalIva0.toString());
        tc.setSubtotal12(totalIvaDiferenteDe0.toString());
        tc.setTotalIce(totalICE.toString());
        tc.setTotalIRBPNR(totalIRBPNR.toString());
        tc.setSubtotalExentoIVA(totalExentoIVA.toString());
        tc.setSubtotal(totalIva0.add(totalIvaDiferenteDe0));
        tc.setSubtotalNoSujetoIva(totalSinImpuesto.toString());
        return tc;
    }
}
