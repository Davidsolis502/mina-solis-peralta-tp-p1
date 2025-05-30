package juego;

import java.awt.Image;
import entorno.Herramientas;
import entorno.Entorno;

public class Personaje {
	private int x;
	private int y;
	private int ancho;
	private int alto;
	private Image imagenIzq;
	private Image imagenDer;
	private double escala;
	private boolean direccionDerecha;
	private int vida;
	private int poder;

	public Personaje(int x, int y, int ancho, int alto) {
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
		this.imagenDer = Herramientas.cargarImagen("mago1-der.png");
		this.imagenIzq = Herramientas.cargarImagen("mago1-izq.png");
		this.escala = 0.1;
		this.vida=100;
		this.poder=100;
		this.direccionDerecha = true; // Por defecto mira a la derecha
	}

	public void dibujar(Entorno entorno) {
		if (direccionDerecha) {
			entorno.dibujarImagen(imagenDer, x, y, 0, escala);
		} else {
			entorno.dibujarImagen(imagenIzq, x, y, 0, escala);
		}
	}

	public void moverDerecha() {
		this.x +=2;
		this.direccionDerecha = true;
	}

	public void moverIzquierda() {
		this.x -=2;
		this.direccionDerecha = false;
	}

	public void moverArriba() {
		if (!colisionaPorArriba()) {
			this.y -= 2;
		}
	}

	public void moverAbajo(Entorno entorno) {
		if (!colisionaPorAbajo(entorno)) {
			this.y += 2;
		}
	
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

	public int getAlto() {
		return alto;
	}
	public int getVida() {
		return this.vida;
	}
	public int getPoder() {
		return this.poder;
	}

	public boolean colisionaPorDerecha(Entorno entorno) {
		return this.x + ((this.ancho / 2)) >= (entorno.ancho() -159);
	}

	public boolean colisionaPorIzquierda(Entorno entorno) {
		return (this.x - (this.ancho / 2 ))<= 0;
	}
	public boolean colisionaPorArriba() {
		return (this.y - (this.alto / 2)) <= 0;
	}

	public boolean colisionaPorAbajo(Entorno entorno) {
		return (this.y + (this.alto / 2)) >= entorno.alto();
	}


	public boolean colisionConEnemigo(Enemigo p) {
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
	public boolean colisionConRoca(Roca r) {
		if (r == null) {
			return false;
		}
	
		int ladoIzquierdo = this.x - (this.ancho / 2);
		int ladoDerecho = this.x + (this.ancho / 2);
		int ladoSuperior = this.y - (this.alto / 2);
		int ladoInferior = this.y + (this.alto / 2);
	
		int rocaIzq = r.getX() - (r.getAncho() / 2);
		int rocaDer = r.getX() + (r.getAncho() / 2);
		int rocaSup = r.getY() - (r.getAncho() / 2);
		int rocaInf = r.getY() + (r.getAncho() / 2);
	
		boolean colisiona = ladoDerecho > rocaIzq && ladoIzquierdo < rocaDer &&
							ladoInferior > rocaSup && ladoSuperior < rocaInf;
	
		return colisiona;
	}
	public void restarVida() {
		this.vida-=5;
	}
	public void restarPoder(){
		this.poder-=25;
	}
	
}
