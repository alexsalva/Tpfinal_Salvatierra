/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portafolio.david.Security.Dto;

import javax.validation.constraints.NotBlank;

public class LoginUsuario {
   @NotBlank
   private String nombreUsuario;
   @NotBlank
   private String password;

   public String getNombreUsuario() {
      return this.nombreUsuario;
   }

   public void setNombreUsuario(String nombreUsuario) {
      this.nombreUsuario = nombreUsuario;
   }

   public String getPassword() {
      return this.password;
   }

   public void setPassword(String password) {
      this.password = password;
   }
}