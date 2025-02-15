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

import com.quijotelui.printer.adicional.DetallesAdicionalesReporte;
import com.quijotelui.printer.utilidades.TipoImpuestoEnum;
import com.quijotelui.printer.utilidades.TipoImpuestoIvaEnum;
import com.quijotelui.printer.adicional.TotalComprobante;
import com.quijotelui.printer.liquidacion.LiquidacionCompra;
import com.quijotelui.printer.liquidacion.LiquidacionCompraReporte;
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
 *
 * @author Jorge Luis
 */
public class LiquidacionCompraPDF {

    String rutaArchivo;//"/app/quijotelu/generado/1912201401100245687700110010030000000031234567810.xml"  
    String directorioReportes;
    String directorioLogo;
    String directorioDestino;

    public LiquidacionCompraPDF(String directorioReportes, String directorioLogo, String directorioDestino) {
        this.directorioReportes = directorioReportes;
        this.directorioLogo = directorioLogo;
        this.directorioDestino = directorioDestino;
    }

    public void genera(String rutaArchivo, String numeroAutorizacion, String fechaAutorizacion) {
        
        this.rutaArchivo = rutaArchivo;
        
        LiquidacionCompra f = xmlToObject();

        LiquidacionCompraReporte fr = new LiquidacionCompraReporte(f);
        generarReporte(fr, numeroAutorizacion, fechaAutorizacion);
    }

    private LiquidacionCompra xmlToObject() {
        LiquidacionCompra liquidacionCompra = null;
        try {
            File file = new File(this.rutaArchivo);
            JAXBContext jaxbContext = JAXBContext.newInstance(LiquidacionCompra.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            liquidacionCompra = (LiquidacionCompra) jaxbUnmarshaller.unmarshal(file);

        } catch (JAXBException ex) {
            Logger.getLogger(LiquidacionCompraPDF.class.getName()).log(Level.SEVERE, null, ex);
        }
        return liquidacionCompra;

    }

    private void generarReporte(LiquidacionCompraReporte xml, String numAut, String fechaAut) {

        generarReporte(this.directorioReportes + File.separator + "liquidacionCompra.jasper", xml, numAut, fechaAut);

    }

    private void generarReporte(String urlReporte, LiquidacionCompraReporte fact, String numAut, String fechaAut) {
        Parametros p = new Parametros(this.directorioReportes, this.directorioLogo);
        FileInputStream is = null;
        try {
            JRDataSource dataSource = new JRBeanCollectionDataSource(fact.getDetallesAdiciones());
            is = new FileInputStream(urlReporte);
            JasperPrint reporte_view = JasperFillManager.fillReport(is, 
                    obtenerMapaParametrosReportes(
                            p.obtenerParametrosInfoTriobutaria(fact.getLiquidacionCompra().getInfoTributaria(), 
                                    numAut, 
                                    fechaAut), 
                            obtenerInfoFactura(fact.getLiquidacionCompra().getInfoLiquidacionCompra(), 
                                    fact)), 
                    dataSource);
            savePdfReport(reporte_view, fact.getLiquidacionCompra().getInfoTributaria().claveAcceso);
        } catch (FileNotFoundException | JRException /*| JRException */ex) {
            Logger.getLogger(LiquidacionCompraPDF.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(LiquidacionCompraPDF.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(LiquidacionCompraPDF.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LiquidacionCompraPDF.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Map<String, Object> obtenerInfoFactura(LiquidacionCompra.InfoLiquidacionCompra infoFactura, LiquidacionCompraReporte fact) {
        Map param = new HashMap();
        param.put("DIR_SUCURSAL", infoFactura.getDirEstablecimiento());
        param.put("CONT_ESPECIAL", infoFactura.getContribuyenteEspecial());
        param.put("LLEVA_CONTABILIDAD", infoFactura.getObligadoContabilidad());
        param.put("RS_COMPRADOR", infoFactura.getRazonSocialProveedor());
        param.put("RUC_COMPRADOR", infoFactura.getIdentificacionProveedor());
        param.put("FECHA_EMISION", infoFactura.getFechaEmision());
        TotalComprobante tc = getTotales(infoFactura);
        param.put("VALOR_TOTAL", infoFactura.getImporteTotal());
        param.put("DESCUENTO", infoFactura.getTotalDescuento());
        param.put("IVA", tc.getIva12());
        param.put("IVA_0", tc.getSubtotal0());
        param.put("IVA_12", tc.getSubtotal12());
        param.put("ICE", tc.getTotalIce());
        param.put("IRBPNR", 0);
        param.put("EXENTO_IVA", "0");
        param.put("NO_OBJETO_IVA", tc.getSubtotalNoSujetoIva());
        param.put("SUBTOTAL", infoFactura.getTotalSinImpuestos().toString());        
        param.put("TOTAL_DESCUENTO", calcularDescuento(fact));
        return param;
    }

    private String calcularDescuento(LiquidacionCompraReporte fact) {
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

    private TotalComprobante getTotales(LiquidacionCompra.InfoLiquidacionCompra infoFactura) {
        BigDecimal totalIva12 = new BigDecimal(0.0D);
        BigDecimal totalIva0 = new BigDecimal(0.0D);
        BigDecimal iva12 = new BigDecimal(0.0D);
        BigDecimal totalICE = new BigDecimal(0.0D);
        BigDecimal totalSinImpuesto = new BigDecimal(0.0D);
        TotalComprobante tc = new TotalComprobante();
        for (LiquidacionCompra.InfoLiquidacionCompra.TotalConImpuestos.TotalImpuesto ti : infoFactura.getTotalConImpuestos().getTotalImpuesto()) {
            Integer cod = new Integer(ti.getCodigo());
            /*
                Modificado para IVA 12% y 14%
             */
            if ((TipoImpuestoEnum.IVA.getCode() == cod.intValue()) && ((TipoImpuestoIvaEnum.IVA_VENTA_12.getCode().equals(ti.getCodigoPorcentaje())) || (TipoImpuestoIvaEnum.IVA_VENTA_14.getCode().equals(ti.getCodigoPorcentaje())))) {
                totalIva12 = totalIva12.add(ti.getBaseImponible());
                iva12 = iva12.add(ti.getValor());
            }
            if ((TipoImpuestoEnum.IVA.getCode() == cod.intValue()) && (TipoImpuestoIvaEnum.IVA_VENTA_0.getCode().equals(ti.getCodigoPorcentaje()))) {
                totalIva0 = totalIva0.add(ti.getBaseImponible());
            }
            if ((TipoImpuestoEnum.IVA.getCode() == cod.intValue()) && (TipoImpuestoIvaEnum.IVA_EXCENTO.getCode().equals(ti.getCodigoPorcentaje()))) {
                totalSinImpuesto = totalSinImpuesto.add(ti.getBaseImponible());
            }
            if (TipoImpuestoEnum.ICE.getCode() == cod.intValue()) {
                totalICE = totalICE.add(ti.getValor());
            }
        }
        tc.setIva12(iva12.toString());
        tc.setSubtotal0(totalIva0.toString());
        tc.setSubtotal12(totalIva12.toString());
        tc.setTotalIce(totalICE.toString());
        tc.setSubtotal(totalIva0.add(totalIva12));
        tc.setSubtotalNoSujetoIva(totalSinImpuesto.toString());
        return tc;
    }
}
