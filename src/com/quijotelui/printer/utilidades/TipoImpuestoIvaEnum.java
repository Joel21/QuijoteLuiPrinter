/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quijotelui.printer.utilidades;

/**
 *
 * @author jorjoluiso
 */
public enum TipoImpuestoIvaEnum
{
  IVA_VENTA_0("0"),
  IVA_VENTA_12("2"),
  IVA_VENTA_14("3"),
  IVA_VENTA_15("4"),
  IVA_VENTA_5("5"),
  IVA_VENTA_13("10"),
  IVA_NO_OBJETO("6"),
  IVA_EXCENTO("7"),
  IVA_DIFERENCIADO("8");


  private String code;

  private TipoImpuestoIvaEnum(String code) {
    this.code = code;
  }

  public String getCode()
  {
    return this.code;
  }
}