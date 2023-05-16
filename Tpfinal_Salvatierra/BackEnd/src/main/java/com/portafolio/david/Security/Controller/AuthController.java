/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portafolio.david.Security.Controller;


import com.portafolio.david.Security.Dto.JwtDto;
import com.portafolio.david.Security.Dto.LoginUsuario;
import com.portafolio.david.Security.Dto.NuevoUsuario;
import com.portafolio.david.Security.Entity.Rol;
import com.portafolio.david.Security.Entity.Usuario;
import com.portafolio.david.Security.Enums.RolNombre;
import com.portafolio.david.Security.Service.RolService;
import com.portafolio.david.Security.Service.UsuarioService;
import com.portafolio.david.Security.jwt.JwtProvider;
import java.util.HashSet;
import java.util.Set;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/auth"})
@CrossOrigin(
   origins = {"https://davidfrontend.web.app", "http://localhost:4200"}
)
public class AuthController {
   @Autowired
   PasswordEncoder passwordEncoder;
   @Autowired
   AuthenticationManager authenticationManager;
   @Autowired
   UsuarioService usuarioService;
   @Autowired
   RolService rolService;
   @Autowired
   JwtProvider jwtProvider;

   @PostMapping({"/nuevo"})
   public ResponseEntity<?> nuevo(@Valid @RequestBody NuevoUsuario nuevoUsuario, BindingResult bindingResult) {
      if (bindingResult.hasErrors()) {
         return new ResponseEntity(new Mensaje("Campos mal puestos o email invalido"), HttpStatus.BAD_REQUEST);
      } else if (this.usuarioService.existsByNombreUsuario(nuevoUsuario.getNombreUsuario())) {
          return new ResponseEntity(new Mensaje("Ese nombre de usuario ya existe"), HttpStatus.BAD_REQUEST);
      } else if (this.usuarioService.existsByEmail(nuevoUsuario.getEmail())) {
          return new ResponseEntity(new Mensaje("Ese email ya existe"), HttpStatus.BAD_REQUEST);
      } else {
          Usuario usuario = new Usuario(nuevoUsuario.getNombre(), nuevoUsuario.getNombreUsuario(), nuevoUsuario.getEmail(), this.passwordEncoder.encode(nuevoUsuario.getPassword()));
          Set<Rol> roles = new HashSet();
          roles.add((Rol)this.rolService.getByRolNombre(RolNombre.ROLE_USER).get());
          if (nuevoUsuario.getRoles().contains("admin")) {
              roles.add((Rol)this.rolService.getByRolNombre(RolNombre.ROLE_ADMIN).get());
          }
          
          usuario.setRoles(roles);
          this.usuarioService.save(usuario);
          return new ResponseEntity(new Mensaje("Usuario guardado"), HttpStatus.CREATED);
      }
   }

   @PostMapping({"/login"})
   public ResponseEntity<JwtDto> login(@Valid @RequestBody LoginUsuario loginUsuario, BindingResult bindingResult) {
      if (bindingResult.hasErrors()) {
         return new ResponseEntity(new Mensaje("Campos mal puestos"), HttpStatus.BAD_REQUEST);
      } else {
         Authentication authentication = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUsuario.getNombreUsuario(), loginUsuario.getPassword()));
         SecurityContextHolder.getContext().setAuthentication(authentication);
         String jwt = this.jwtProvider.generateToken(authentication);
         UserDetails userDetails = (UserDetails)authentication.getPrincipal();
         JwtDto jwtDto = new JwtDto(jwt, userDetails.getUsername(), userDetails.getAuthorities());
         return new ResponseEntity(jwtDto, HttpStatus.OK);
      }
   }
}