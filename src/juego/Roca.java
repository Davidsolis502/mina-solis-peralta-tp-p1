package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Roca {
	private int x;
	private int y;
	private Image roca;
	private double escala;
	private int ancho;

	public Roca(int x, int y) {
		this.x = x;
		this.y = y;
		this.escala = 0.05;
		this.roca = Herramientas.cargarImagen("imagen-roca.png");
		this.ancho = 1;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void dibujarse(Entorno entorno) {
		entorno.dibujarImagen(roca, x, y, 0, escala);
	}

	public int getAncho() {
		return ancho;
	}
}
