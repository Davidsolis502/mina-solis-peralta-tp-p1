package juego;

import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;

public class HechizoOndaExpansiva {
    private Image imagen;
    private int centroX;
    private int centroY;
    private boolean activo;
    private double escala;
    
    public HechizoOndaExpansiva() {
        this.imagen = Herramientas.cargarImagen("testExplosion.png");
        this.escala = 1.0;
        this.activo = false;
    }
    
    public void activar(int centroX, int centroY) {
        if (!activo) {
            this.centroX = centroX;
            this.centroY = centroY;
            this.activo = true;
        }
    }
    
    public void aplicarEfecto(Enemigo[] enemigos) {
        if (activo) {
            for (int i = 0; i < enemigos.length; i++) {
                if (enemigos[i] != null) {
                    enemigos[i] = null;
                }
            }
            this.activo = false; // Se desactiva inmediatamente despues de aplicar el efecto
        }
    }
    
    public void dibujarse(Entorno entorno) {
            entorno.dibujarImagen(imagen, centroX, centroY, escala);
        }
    
    public boolean estaActivo() {
        return activo;
    }
}