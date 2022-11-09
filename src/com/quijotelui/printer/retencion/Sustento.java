package com.quijotelui.printer.retencion;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Jorge Luis
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {
    "codSustento",
    "codDocSustento",
    "numDocSustento",
    "fechaEmisionDocSustento",
    "numAutDocSustento",
    "pagoLocExt",
    "totalSinImpuestos",
    "importeTotal",
    "impuestos",
    "retenciones",
    "pagos"
})
public class Sustento {

    @XmlElement(required = true)
    String codSustento;

    @XmlElement(required = true)
    String codDocSustento;

    @XmlElement(required = true)
    String numDocSustento;

    @XmlElement(required = true)
    String fechaEmisionDocSustento;

    @XmlElement(required = true)
    String numAutDocSustento;

    @XmlElement(required = true)
    String pagoLocExt;

    @XmlElement(required = true)
    BigDecimal totalSinImpuestos;

    @XmlElement(required = true)
    BigDecimal importeTotal;

    @XmlElement(required = true, name = "impuestosDocSustento")
    Impuestos impuestos;

    @XmlElement(required = true)
    Retenciones retenciones;
    
    @XmlElement(required = true)
    Pago pagos;
    

    public String getCodSustento() {
        return codSustento;
    }

    public void setCodSustento(String codSustento) {
        this.codSustento = codSustento;
    }

    public String getCodDocSustento() {
        return codDocSustento;
    }

    public void setCodDocSustento(String codDocSustento) {
        this.codDocSustento = codDocSustento;
    }

    public String getNumDocSustento() {
        return numDocSustento;
    }

    public void setNumDocSustento(String numDocSustento) {
        this.numDocSustento = numDocSustento;
    }

    public String getFechaEmisionDocSustento() {
        return fechaEmisionDocSustento;
    }

    public void setFechaEmisionDocSustento(String fechaEmisionDocSustento) {
        this.fechaEmisionDocSustento = fechaEmisionDocSustento;
    }

    public String getNumAutDocSustento() {
        return numAutDocSustento;
    }

    public void setNumAutDocSustento(String numAutDocSustento) {
        this.numAutDocSustento = numAutDocSustento;
    }

    public String getPagoLocExt() {
        return pagoLocExt;
    }

    public void setPagoLocExt(String pagoLocExt) {
        this.pagoLocExt = pagoLocExt;
    }

    public BigDecimal getTotalSinImpuestos() {
        return totalSinImpuestos;
    }

    public void setTotalSinImpuestos(BigDecimal totalSinImpuestos) {
        this.totalSinImpuestos = totalSinImpuestos;
    }

    public BigDecimal getImporteTotal() {
        return importeTotal;
    }

    public void setImporteTotal(BigDecimal importeTotal) {
        this.importeTotal = importeTotal;
    }

    public Impuestos getImpuestos() {
        return impuestos;
    }

    public void setImpuestos(Impuestos impuestos) {
        this.impuestos = impuestos;
    }

    public Retenciones getRetenciones() {
        return retenciones;
    }

    public void setRetenciones(Retenciones retenciones) {
        this.retenciones = retenciones;
    }

    public Pago getPagos() {
        return pagos;
    }

    public void setPagos(Pago pagos) {
        this.pagos = pagos;
    }
    
    
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(propOrder = {"impuesto"})
    public static class Impuestos {

        @XmlElement(required = true, name = "impuestoDocSustento")
        protected List<Impuesto> impuesto;

        public List<Impuesto> getImpuesto() {
            if (this.impuesto == null) {
                this.impuesto = new ArrayList();
            }
            return this.impuesto;
        }
    }

    
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(propOrder = {"retencion"})
    public static class Retenciones {

        @XmlElement(required = true)
        protected List<Retencion> retencion;

        public List<Retencion> getRetencion() {
            if (this.retencion == null) {
                this.retencion = new ArrayList();
            }
            return this.retencion;
        }
    }

    
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"pago"})
    public static class Pago {

        @XmlElement(required = true)
        protected List<DetallePago> pago;

        public List<DetallePago> getPagos() {
            if (this.pago == null) {
                this.pago = new ArrayList();
            }
            return this.pago;
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {"formaPago", "total"})
        public static class DetallePago {

            @XmlElement(required = true)
            protected String formaPago;
            @XmlElement(required = true)
            protected BigDecimal total;

            public String getFormaPago() {
                return this.formaPago;
            }

            public void setFormaPago(String formaPago) {
                this.formaPago = formaPago;
            }

            public BigDecimal getTotal() {
                return this.total;
            }

            public void setTotal(BigDecimal total) {
                this.total = total;
            }
        }
    }
}
