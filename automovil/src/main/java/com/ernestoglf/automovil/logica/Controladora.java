package com.ernestoglf.automovil.logica;

import com.ernestoglf.automovil.persistencia.ControladoraPersistencia;
import java.util.List;

public class Controladora {
    
    ControladoraPersistencia controlPersis = new ControladoraPersistencia();

    public void agregarAutomovil(String modelo, String marca, String motor, String color, String patente, int cantPuertas) {
        
        
        Automov auto = new Automov();
        
        auto.setModelo(modelo);
        auto.setMarca(marca);
        auto.setMotor(motor);
        auto.setColor(color);
        auto.setPatente(patente);
        auto.setCantPuertas(cantPuertas);
        
        controlPersis.agregarAutomovil(auto);
        
    }


    public List<Automov> traerAutomovilControladora() {
        return controlPersis.traerAutomovilPersistencia();
    }

    public void borrarAuto(int idAuto) {
        controlPersis.eliminarAutomovil(idAuto);
    }

    public Automov traerAuto(int idAuto) {
       
        return controlPersis.traerAuto(idAuto);
        
    }

    public void modificarAuto(Automov auto, String modelo, String marca, String motor, String color, String patente, int cantPuertas) {
        
        auto.setColor(color);
        auto.setModelo(modelo);
        auto.setMarca(marca);
        auto.setPatente(patente);
        auto.setMotor(motor);
        auto.setCantPuertas(cantPuertas);
        
        //Pedimos a persistencia que lo modifique
        controlPersis.modificarAuto(auto);
        
    }


    

    
    
    
}
