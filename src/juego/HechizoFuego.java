package juego;

import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;

public class HechizoFuego {

    private int x;
    private int y;
    private Image hechizoFuego;
    private double escala;
    private int ancho;
    private int alto;
    private int fotogramas; // duración en frames

    public HechizoFuego(int x, int y) {
        this.x = x;
        this.y = y;
        this.hechizoFuego = Herramientas.cargarImagen("explosion.gif");
        this.escala = 0.5;
        this.ancho = 160;
        this.alto = 160;
        this.fotogramas = 0; // dura 30 frames ( medio segundo a 60 FPS)
    }

    public void actualizar() {
        this.fotogramas--;
    }

    public boolean estaActivo() {
        return this.fotogramas > 0;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void dibujarse(Entorno entorno) {
        if (estaActivo()) {
            entorno.dibujarImagen(hechizoFuego, x, y, escala);
        }
    }

    public boolean EnemigoEnRango(Enemigo p) {
        if (p == null) {
            return false;
        }

        int ladoIzquierdo = this.x - (this.ancho / 2);
        int ladoDerecho = this.x + (this.ancho / 2);
        int ladoSuperior = this.y - (this.alto / 2);
        int ladoInferior = this.y + (this.alto / 2);

        int xCercano = Math.max(ladoIzquierdo, Math.min(p.getX(), ladoDerecho));
        int yCercano = Math.max(ladoSuperior, Math.min(p.getY(), ladoInferior));

        int deltaX = xCercano - p.getX();
        int deltaY = yCercano - p.getY();
        int distancia = (int) Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));

        return distancia <= (p.getDiametro() / 2);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void reiniciar() {
		this.fotogramas = 30;
	}
	
}
