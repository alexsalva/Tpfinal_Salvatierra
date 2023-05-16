/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portafolio.david.Security.Controller;


public class Mensaje {
   private String mensaje;

   public Mensaje() {
   }

   public Mensaje(String mensaje) {
      this.mensaje = mensaje;
   }

   public String getMensaje() {
      return this.mensaje;
   }

   public void setMensaje(String mensaje) {
      this.mensaje = mensaje;
   }
}