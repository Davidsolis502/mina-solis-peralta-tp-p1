package juego;

import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;

public class EnemigoFinal {
    private int x;
    private int y;
    private Image imagenEnemigo;
    private double escala;
    private int vidas;
    
    public EnemigoFinal(int x, int y) {
        this.x = x;
        this.y = y;
        this.imagenEnemigo = Herramientas.cargarImagen("gif-murcielago.gif");
        this.escala = 0.80;
        this.vidas = 60; // Requiere 3 golpes de hechizo
    }
    
    public void dibujarse(Entorno entorno) {
            entorno.dibujarImagen(imagenEnemigo, x, y, 0, escala);
    }
    
    public void seguirPersonaje(Personaje personaje) {
        
        int dx = personaje.getX() - this.x;
        int dy = personaje.getY() - this.y;
        double distancia = Math.sqrt(dx * dx + dy * dy);
        
        if (distancia > 0) {
            dx = (int) (dx / distancia * 2); 
            dy = (int) (dy / distancia * 2);
        }
        
        this.x += dx;
        this.y += dy;
    }
    
    public void recibirDaño() {
            vidas--;
        }
    
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    public int getDiametro() {
        return (int)(50 * escala); // Ajustar según el tamaño de la imagen
    }
    
    public int getVidas() {
        return vidas;
    }
}
