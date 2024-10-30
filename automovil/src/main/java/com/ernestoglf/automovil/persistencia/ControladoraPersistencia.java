package com.ernestoglf.automovil.persistencia;

import com.ernestoglf.automovil.Automovil;
import com.ernestoglf.automovil.logica.Automov;
import com.ernestoglf.automovil.persistencia.exceptions.NonexistentEntityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControladoraPersistencia {
    
    AutomovJpaController autoJpa = new AutomovJpaController();

    public void agregarAutomovil(Automov auto) {
        
        autoJpa.create(auto);
        
    }

    public List<Automov> traerAutomovilPersistencia() {
        return autoJpa.findAutomovEntities();
    }

    public void eliminarAutomovil(int idAuto) {
        try {
            autoJpa.destroy(idAuto);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Automov traerAuto(int idAuto) {
        return autoJpa.findAutomov(idAuto);
    }

    public void modificarAuto(Automov auto) {
        try {
            autoJpa.edit(auto);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }








    
    
    
}
