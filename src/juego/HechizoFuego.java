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
    private int fotogramas;
    private boolean efectoAplicado;
    private final int DURACION_TOTAL = 30; // 0.5 segundos a 60 FPS

    public HechizoFuego(int x, int y) {
        try {
            this.hechizoFuego = Herramientas.cargarImagen("explosion.gif");
            System.out.println("Imagen de hechizo de fuego cargada correctamente");
        } catch (Exception e) {
            System.out.println("Error al cargar imagen de hechizo: " + e.getMessage());
        }
        this.escala = 0.5;
        this.ancho = 160;
        this.alto = 160;
        this.fotogramas = 0;
        this.efectoAplicado = false;
        // la pocicion fuera de la pantalla
        this.x = -100;
        this.y = -100;
    }

    public void actualizar() {
        if (estaActivo()) {
            fotogramas--;
            // se resetea cuando termina la animacion
            if (fotogramas <= 0) {
                resetear();
            }
        }
    }

    public boolean estaActivo() {
        return fotogramas > 0;
    }

    public void setPosicion(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void dibujarse(Entorno entorno) {
        if (estaActivo()) {
            entorno.dibujarImagen(hechizoFuego, x, y, 0, escala);
        }
    }

    public boolean EnemigoEnRango(Enemigo p) {
        if (p == null || !estaActivo() || efectoAplicado) {
            return false;
        }

        // calcula la colision
        int ladoIzquierdo = this.x - (this.ancho / 2);
        int ladoDerecho = this.x + (this.ancho / 2);
        int ladoSuperior = this.y - (this.alto / 2);
        int ladoInferior = this.y + (this.alto / 2);

        int xCercano = Math.max(ladoIzquierdo, Math.min(p.getX(), ladoDerecho));
        int yCercano = Math.max(ladoSuperior, Math.min(p.getY(), ladoInferior));

        int deltaX = xCercano - p.getX();
        int deltaY = yCercano - p.getY();
        double distancia = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        efectoAplicado = distancia <= (p.getDiametro() / 2);
        return efectoAplicado;
    }

    public void reiniciar() {
        this.fotogramas = DURACION_TOTAL;
        this.efectoAplicado = false;
    }

    public void resetear() {
        this.fotogramas = 0;
        this.efectoAplicado = true;
        // Mover fuera de pantalla
        this.x = -100;
        this.y = -100;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}