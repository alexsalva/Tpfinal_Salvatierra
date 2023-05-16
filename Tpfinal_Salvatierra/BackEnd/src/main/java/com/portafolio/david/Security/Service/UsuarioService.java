/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portafolio.david.Security.Service;

import com.portafolio.david.Security.Entity.Usuario;
import com.portafolio.david.Security.Repository.iUsuarioRepository;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


 @Service
 @Transactional
public class UsuarioService {
    
 @Autowired
 iUsuarioRepository iusuarioRepository;
 
 public Optional<Usuario> getByNombreUsuario(String nombreUsuario){
     return iusuarioRepository.findByNombreUsuario(nombreUsuario);
     
 }
 
 public boolean exisysByNombreUsuario(String nombreUsuario){
     return iusuarioRepository.existsByNombreUsuario(nombreUsuario);
 }
 
 public boolean exisysByEmail(String email){
     return iusuarioRepository.existsByEmail(email);
 }
 
 public void save(Usuario usuario){
     iusuarioRepository.save(usuario);
 }

    public boolean existsByNombreUsuario(String nombreUsuario) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean existsByEmail(String email) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
