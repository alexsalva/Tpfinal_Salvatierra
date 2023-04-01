
package com.portafolio.david.Interface;

import com.portafolio.david.Entity.Persona;
import java.util.List;


public interface IPersonaService {
    //Traer un persona
    public List<Persona> getPersona();
    
    //Guarar un objeto de tipo persona
    
    public void savePersona(Persona persona);
    //Eliminar un objeto peero lo buscamos por ID
     public void deletePersona(Long id);
     
     //Buscar una persona por ID
     public Persona findPersona(Long id);
}
