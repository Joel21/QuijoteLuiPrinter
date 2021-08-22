/*
 * Copyright (C) 2020 Jorge Luis
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

import com.quijotelui.printer.adicional.TotalComprobante;
import com.quijotelui.printer.notadebito.Impuesto;
import com.quijotelui.printer.notadebito.NotaDebito;
import com.quijotelui.printer.notadebito.NotaDebitoReporte;
import com.quijotelui.printer.parametros.Parametros;
import com.quijotelui.printer.utilidades.TipoImpuestoEnum;
import com.quijotelui.printer.utilidades.TipoImpuestoIvaEnum;
import com.quijotelui.printer.utilidades.Tipos;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jorge Luis
 */
public class NotaDebitoPDF {

    String rutaArchivo;
    String directorioReportes;
    String directorioLogo;
    String directorioDestino;

    public NotaDebitoPDF(String directorioReportes, String directorioLogo, String directorioDestino) {
        this.directorioReportes = directorioReportes;
        this.directorioLogo = directorioLogo;
        this.directorioDestino = directorioDestino;
    }

    public void genera(String rutaArchivo, String numeroAutorizacion, String fechaAutorizacion) {
        
        this.rutaArchivo = rutaArchivo;

        NotaDebito f = xmlToObject();

        NotaDebitoReporte fr = new NotaDebitoReporte(f);
        generarReporte(fr, numeroAutorizacion, fechaAutorizacion);
    }

    private NotaDebito xmlToObject() {
        NotaDebito notaDebito = null;
        try {
            File file = new File(this.rutaArchivo);
            JAXBContext jaxbContext = JAXBContext.newInstance(NotaDebito.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            notaDebito = (NotaDebito) jaxbUnmarshaller.unmarshal(file);

        } catch (JAXBException ex) {
            Logger.getLogger(NotaDebitoPDF.class.getName()).log(Level.SEVERE, null, ex);
        }
        return notaDebito;

    }

    private void generarReporte(NotaDebitoReporte xml, String numAut, String fechaAut) {
        generarReporte(this.directorioReportes + File.separator + "notaDebito.jasper", xml, numAut, fechaAut);
    }

    private void generarReporte(String urlReporte, NotaDebitoReporte fact, String numAut, String fechaAut) {
        Parametros p = new Parametros(this.directorioReportes, this.directorioLogo);
        FileInputStream is = null;
        try {
            JRDataSource dataSource = new JRBeanCollectionDataSource(fact.getDetallesAdiciones());
            is = new FileInputStream(urlReporte);
            JasperPrint reporte_view = JasperFillManager.fillReport(is, 
                    obtenerMapaParametrosReportes(
                            p.obtenerParametrosInfoTriobutaria(fact.getNotaDebito().getInfoTributaria(),
                                    numAut, 
                                    fechaAut), 
                            obtenerInfoFactura(fact.getNotaDebito().getInfoNotaDebito(),
                                    fact)), 
                    dataSource);
            savePdfReport(reporte_view, fact.getNotaDebito().getInfoTributaria().claveAcceso);
        } catch (FileNotFoundException | JRException ex) {
            Logger.getLogger(NotaDebitoPDF.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(NotaDebitoPDF.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(NotaDebitoPDF.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(NotaDebitoPDF.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Map<String, Object> obtenerInfoFactura(NotaDebito.InfoNotaDebito notaDebito, NotaDebitoReporte fact) {
        Map param = new HashMap();
        param.put("DIR_SUCURSAL", notaDebito.getDirEstablecimiento());
        param.put("CONT_ESPECIAL", notaDebito.getContribuyenteEspecial());
        param.put("LLEVA_CONTABILIDAD", notaDebito.getObligadoContabilidad());
        param.put("RS_COMPRADOR", notaDebito.getRazonSocialComprador());
        param.put("RUC_COMPRADOR", notaDebito.getIdentificacionComprador());
        param.put("FECHA_EMISION", notaDebito.getFechaEmision());
        param.put("NUM_DOC_MODIFICADO", notaDebito.getNumDocModificado());
        param.put("FECHA_EMISION_DOC_SUSTENTO", notaDebito.getFechaEmisionDocSustento());
        param.put("DOC_MODIFICADO", Tipos.obtenerDocumentoModificado(notaDebito.getCodDocModificado()));

        TotalComprobante tc = getTotales(notaDebito);

        param.put("VALOR_TOTAL", notaDebito.getValorTotal());

        return param;
    }

    private Map<String, Object> obtenerMapaParametrosReportes(Map<String, Object> mapa1, Map<String, Object> mapa2) {
        mapa1.putAll(mapa2);
        return mapa1;
    }

    private TotalComprobante getTotales(NotaDebito.InfoNotaDebito infoNotaDebito) {

        BigDecimal totalIva = new BigDecimal(0.0D);
        BigDecimal totalIva0 = new BigDecimal(0.0D);
        BigDecimal totalICE = new BigDecimal(0.0D);
        BigDecimal totalExentoIVA = new BigDecimal(0.0D);
        BigDecimal totalSinImpuesto = new BigDecimal(0.0D);

        TotalComprobante tc = new TotalComprobante();
        for (Impuesto ti : infoNotaDebito.getImpuestos().getImpuesto()) {
            Integer cod = new Integer(ti.getCodigo());
            if (TipoImpuestoEnum.IVA.getCode() == cod.intValue() && ti.getValor().doubleValue() > 0.0D) {

            }
            if (TipoImpuestoEnum.IVA.getCode() == cod.intValue() && TipoImpuestoIvaEnum.IVA_VENTA_0.getCode().equals(ti.getCodigoPorcentaje()))
                totalIva0 = totalIva0.add(ti.getBaseImponible());
            if (TipoImpuestoEnum.IVA.getCode() == cod.intValue() && TipoImpuestoIvaEnum.IVA_NO_OBJETO.getCode().equals(ti.getCodigoPorcentaje()))
                totalSinImpuesto = totalSinImpuesto.add(ti.getBaseImponible());
            if (TipoImpuestoEnum.IVA.getCode() == cod.intValue() && TipoImpuestoIvaEnum.IVA_EXCENTO.getCode().equals(ti.getCodigoPorcentaje()))
                totalExentoIVA = totalExentoIVA.add(ti.getBaseImponible());
            if (TipoImpuestoEnum.ICE.getCode() == cod.intValue())
                totalICE = totalICE.add(ti.getValor());
        }
        tc.setSubtotal0(totalIva0.toString());
        tc.setSubtotal(totalIva.add(totalIva0).add(totalExentoIVA).add(totalSinImpuesto).setScale(2));
        tc.setTotalIce(totalICE.toString());
        tc.setSubtotalNoSujetoIva(totalSinImpuesto.toString());
        tc.setIva12("0.12");
        return tc;
    }
}