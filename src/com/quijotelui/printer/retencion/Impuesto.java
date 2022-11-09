package com.quijotelui.printer.retencion;

/**
 *
 * @author Jorge Luis
 */
import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {
    "codImpuestoDocSustento",
    "codigoPorcentaje",
    "baseImponible",
    "tarifa",
    "valorImpuesto"
})
public class Impuesto {

    @XmlElement(required = true)
    String codImpuestoDocSustento;

    @XmlElement(required = true)
    String codigoPorcentaje;

    @XmlElement(required = true)
    BigDecimal baseImponible;

    @XmlElement(required = true)
    BigDecimal tarifa;

    @XmlElement(required = true)
    BigDecimal valorImpuesto;    
    

    public String getCodImpuestoDocSustento() {
        return codImpuestoDocSustento;
    }

    public void setCodImpuestoDocSustento(String codImpuestoDocSustento) {
        this.codImpuestoDocSustento = codImpuestoDocSustento;
    }

    public String getCodigoPorcentaje() {
        return codigoPorcentaje;
    }

    public void setCodigoPorcentaje(String codigoPorcentaje) {
        this.codigoPorcentaje = codigoPorcentaje;
    }

    public BigDecimal getBaseImponible() {
        return baseImponible;
    }

    public void setBaseImponible(BigDecimal baseImponible) {
        this.baseImponible = baseImponible;
    }

    public BigDecimal getTarifa() {
        return tarifa;
    }

    public void setTarifa(BigDecimal tarifa) {
        this.tarifa = tarifa;
    }

    public BigDecimal getValorImpuesto() {
        return valorImpuesto;
    }

    public void setValorImpuesto(BigDecimal valorImpuesto) {
        this.valorImpuesto = valorImpuesto;
    }        
}
