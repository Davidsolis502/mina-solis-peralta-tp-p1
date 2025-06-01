package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Enemigo{
	private int x;
	private int y;
	private Image imagenenemigo;
	private int diametro;
	private double escala;
	
	public Enemigo(int x, int y) {
		this.x = x;
		this.y = y;
		this.imagenenemigo= Herramientas.cargarImagen("gif-murcielago.gif");
		this.escala= 0.40;
	}
	
	public void dibujarse(Entorno entorno) {
		entorno.dibujarImagen(imagenenemigo, x, y,0,escala);
	}
	
	public boolean fueraDeLimite(Entorno e) {
		if(this.y-(this.diametro/2) >= e.alto()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public void seguirPersonaje(Personaje personaje) {
		double velocidad = 1.5;
		
		// Calcular la dirección hacia el personaje
		double dx = personaje.getX() - this.x;
		double dy = personaje.getY() - this.y;
		
		// Normalizar la dirección (convertir a vector unitario)
		double distancia = Math.sqrt(dx * dx + dy * dy);
		if (distancia > 0) {
			dx = dx / distancia * velocidad;
			dy = dy / distancia * velocidad;
		}
		
		// Mover al enemigo
		this.x += dx;
		this.y += dy;
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
		return diametro;
	}

	public void setDiametro(int diametro) {
		this.diametro = diametro;
	}
	
	
	
}
