package com.quijotelui.printer.notadebito;

import com.quijotelui.printer.adicional.*;
import java.util.ArrayList;
import java.util.List;

public class NotaDebitoReporte {
    private NotaDebito notaDebito;

    private List<DetallesAdicionalesReporte> detallesAdiciones;

    private List<InformacionAdicional> infoAdicional;

    private List<FormasPago> formasPago;

    public NotaDebitoReporte(NotaDebito notaDebito) {
        this.notaDebito = notaDebito;
    }

    public List<DetallesAdicionalesReporte> getDetallesAdiciones() {
        this.detallesAdiciones = new ArrayList<>();
        for (NotaDebito.Motivos.Motivo motivo : this.notaDebito.getMotivos().getMotivo()) {
            DetallesAdicionalesReporte detAd = new DetallesAdicionalesReporte();
            detAd.setRazonModificacion(motivo.getRazon());
            detAd.setValorModificacion(motivo.getValor().toString());
            detAd.setInfoAdicional(getInfoAdicional());
            if (getFormasPago() != null)
                detAd.setFormasPago(getFormasPago());
            this.detallesAdiciones.add(detAd);
        }
        return this.detallesAdiciones;
    }

    private String obtenerDetalleFormaPago(String codigo) {
            return "";
    }

    public void setDetallesAdiciones(List<DetallesAdicionalesReporte> detallesAdiciones) {
        this.detallesAdiciones = detallesAdiciones;
    }

    public NotaDebito getNotaDebito() {
        return this.notaDebito;
    }

    public void setNotaDebito(NotaDebito notaDebito) {
        this.notaDebito = notaDebito;
    }

    public List<InformacionAdicional> getInfoAdicional() {
        if (this.notaDebito.getInfoAdicional() != null && !this.notaDebito.getInfoAdicional().getCampoAdicional().isEmpty()) {
            this.infoAdicional = new ArrayList<>();
            for (NotaDebito.InfoAdicional.CampoAdicional info : this.notaDebito.getInfoAdicional().getCampoAdicional()) {
                InformacionAdicional ia = new InformacionAdicional(info.getValue(), info.getNombre());
                this.infoAdicional.add(ia);
            }
        }
        return this.infoAdicional;
    }

    public void setInfoAdicional(List<InformacionAdicional> infoAdicional) {
        this.infoAdicional = infoAdicional;
    }

    public List<FormasPago> getFormasPago() {
        System.out.println("--->" + getNotaDebito());
        if (getNotaDebito().getInfoNotaDebito().getPagos() != null) {
            this.formasPago = new ArrayList<>();
            if (getNotaDebito().getInfoNotaDebito().getPagos().getPagos() != null && !getNotaDebito().getInfoNotaDebito().getPagos().getPagos().isEmpty())
                for (NotaDebito.InfoNotaDebito.Pago.DetallePago pa : getNotaDebito().getInfoNotaDebito().getPagos().getPagos())
                    this.formasPago.add(new FormasPago(obtenerDetalleFormaPago(pa.getFormaPago()), pa.getTotal().setScale(2).toString()));
        }
        return this.formasPago;
    }

    public void setFormasPago(List<FormasPago> formasPago) {
        this.formasPago = formasPago;
    }
}
