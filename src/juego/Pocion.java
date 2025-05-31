package juego;

import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;

public class Pocion {
	private int x;
	private int y;
	private Image pocion;
	private double escala;
	private int ancho;

	public Pocion() {
		this.escala = 0.05;
		this.pocion = Herramientas.cargarImagen("pocion.png");
		this.ancho = (int) (pocion.getWidth(null) * escala);

		//posicion aleatoria//
		this.x = (int)(Math.random() * (400 - ancho));
		this.y = (int)(Math.random() * (400 - ancho));
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getAncho() {
		return ancho;
	}

	public void dibujarse(Entorno entorno) {
		entorno.dibujarImagen(pocion, x, y, 0, escala);
	}

}
