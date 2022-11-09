package com.quijotelui.printer.retencion;

import java.math.BigDecimal;
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
    "codigo",
    "codigoRetencion",
    "baseImponible",
    "porcentajeRetener",
    "valorRetenido"
})
public class Retencion {
    
    @XmlElement(required = true)
    String codigo;

    @XmlElement(required = true)
    String codigoRetencion;
    
    @XmlElement(required = true)
    BigDecimal baseImponible;

    @XmlElement(required = true)
    BigDecimal porcentajeRetener;

    @XmlElement(required = true)
    BigDecimal valorRetenido;
    

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigoRetencion() {
        return codigoRetencion;
    }

    public void setCodigoRetencion(String codigoRetencion) {
        this.codigoRetencion = codigoRetencion;
    }

    public BigDecimal getBaseImponible() {
        return baseImponible;
    }

    public void setBaseImponible(BigDecimal baseImponible) {
        this.baseImponible = baseImponible;
    }

    public BigDecimal getPorcentajeRetener() {
        return porcentajeRetener;
    }

    public void setPorcentajeRetener(BigDecimal porcentajeRetener) {
        this.porcentajeRetener = porcentajeRetener;
    }

    public BigDecimal getValorRetenido() {
        return valorRetenido;
    }

    public void setValorRetenido(BigDecimal valorRetenido) {
        this.valorRetenido = valorRetenido;
    }
}
