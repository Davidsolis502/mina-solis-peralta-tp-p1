package juego;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class HechizoFuego {


	private int x;
	private int y;
	private Image HechizoFuego;
	private double escala;
	private int ancho;
	private int alto;
	
	
	public HechizoFuego(int x, int y){
		
		this.x=x;
		this.y=y;
		this.HechizoFuego=Herramientas.cargarImagen("HechizoBasico.gif");
		this.escala=0.5;
		this.alto=160;
		this.alto=160;
	}
	
	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void dibujarse(Entorno entorno) {
		entorno.dibujarImagen(HechizoFuego, x, y, escala);
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

		int alto = yCercano - p.getY();
		int ancho = xCercano - p.getX();
		int distancia = (int) Math.sqrt(Math.pow(alto, 2) + Math.pow(ancho, 2));

		return distancia <= (p.getDiametro() / 2);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
}
