package com.quijotelui.printer.retencion;

/**
 *
 * @author jorjoluiso
 */
import com.quijotelui.printer.adicional.InfoTributaria;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {
    "infoTributaria",
    "infoCompRetencion",
    "sustentos",
    "infoAdicional"
})
@XmlRootElement(name = "comprobanteRetencion")
public class ComprobanteRetencion {

    @XmlElement(required = true)
    InfoTributaria infoTributaria;

    @XmlElement(required = true)
    InfoCompRetencion infoCompRetencion;

    @XmlElement(required = true, name = "docsSustento")
    Sustentos sustentos;
    
    InfoAdicional infoAdicional;

    @XmlAttribute
    String id;

    @XmlAttribute(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NMTOKEN")
    String version;

    public InfoTributaria getInfoTributaria() {
        return this.infoTributaria;
    }

    public void setInfoTributaria(InfoTributaria value) {
        this.infoTributaria = value;
    }

    public InfoCompRetencion getInfoCompRetencion() {
        return this.infoCompRetencion;
    }

    public void setInfoCompRetencion(InfoCompRetencion value) {
        this.infoCompRetencion = value;
    }

    public Sustentos getSustentos() {
        return sustentos;
    }

    public void setSustentos(Sustentos sustentos) {
        this.sustentos = sustentos;
    }

    public InfoAdicional getInfoAdicional() {
        return this.infoAdicional;
    }

    public void setInfoAdicional(InfoAdicional value) {
        this.infoAdicional = value;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String value) {
        this.id = value;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String value) {
        this.version = value;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"fechaEmision", "dirEstablecimiento", "contribuyenteEspecial", "obligadoContabilidad", "tipoIdentificacionSujetoRetenido", "razonSocialSujetoRetenido", "identificacionSujetoRetenido", "periodoFiscal"})
    public static class InfoCompRetencion {

        @XmlElement(required = true)
        String fechaEmision;
        String dirEstablecimiento;
        String contribuyenteEspecial;
        String obligadoContabilidad;

        @XmlElement(required = true)
        String tipoIdentificacionSujetoRetenido;

        @XmlElement(required = true)
        String razonSocialSujetoRetenido;

        @XmlElement(required = true)
        String identificacionSujetoRetenido;

        @XmlElement(required = true)
        String periodoFiscal;

        public String getFechaEmision() {
            return this.fechaEmision;
        }

        public void setFechaEmision(String value) {
            this.fechaEmision = value;
        }

        public String getDirEstablecimiento() {
            return this.dirEstablecimiento;
        }

        public void setDirEstablecimiento(String value) {
            this.dirEstablecimiento = value;
        }

        public String getContribuyenteEspecial() {
            return this.contribuyenteEspecial;
        }

        public void setContribuyenteEspecial(String value) {
            this.contribuyenteEspecial = value;
        }

        public String getObligadoContabilidad() {
            return this.obligadoContabilidad;
        }

        public void setObligadoContabilidad(String value) {
            this.obligadoContabilidad = value;
        }

        public String getTipoIdentificacionSujetoRetenido() {
            return this.tipoIdentificacionSujetoRetenido;
        }

        public void setTipoIdentificacionSujetoRetenido(String value) {
            this.tipoIdentificacionSujetoRetenido = value;
        }

        public String getRazonSocialSujetoRetenido() {
            return this.razonSocialSujetoRetenido;
        }

        public void setRazonSocialSujetoRetenido(String value) {
            this.razonSocialSujetoRetenido = value;
        }

        public String getIdentificacionSujetoRetenido() {
            return this.identificacionSujetoRetenido;
        }

        public void setIdentificacionSujetoRetenido(String value) {
            this.identificacionSujetoRetenido = value;
        }

        public String getPeriodoFiscal() {
            return this.periodoFiscal;
        }

        public void setPeriodoFiscal(String value) {
            this.periodoFiscal = value;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"campoAdicional"})
    public static class InfoAdicional {

        @XmlElement(required = true)
        List<CampoAdicional> campoAdicional;

        public List<CampoAdicional> getCampoAdicional() {
            if (this.campoAdicional == null) {
                this.campoAdicional = new ArrayList();
            }
            return this.campoAdicional;
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {"value"})
        public static class CampoAdicional {

            @XmlValue
            String value;

            @XmlAttribute
            String nombre;

            public String getValue() {
                return this.value;
            }

            public void setValue(String value) {
                this.value = value;
            }

            public String getNombre() {
                return this.nombre;
            }

            public void setNombre(String value) {
                this.nombre = value;
            }
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(propOrder = {"sustento"})
    public static class Sustentos {

        @XmlElement(required = true, name = "docSustento")
        List<Sustento> sustento;

        public List<Sustento> getSustento() {
            if (this.sustento == null) {
                this.sustento = new ArrayList();
            }
            return this.sustento;
        }
    }
}
