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

	public Personaje(int x, int y, int ancho, int alto) {
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
		this.imagenDer = Herramientas.cargarImagen("personaje-der.png");
		this.imagenIzq = Herramientas.cargarImagen("personaje-izq.png");
		this.escala = 0.08;
		this.direccionDerecha = true; // Por defecto mira a la derecha
	}

	public void dibujar(Entorno entorno) {
		if (direccionDerecha) {
			entorno.dibujarImagen(imagenDer, x, y, 0, escala);
		} else {
			entorno.dibujarImagen(imagenIzq, x, y, 0, escala);
		}
		// entorno.dibujarRectangulo(x, y, ancho, alto, 0, color);
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

	public boolean colisionaPorDerecha(Entorno entorno) {
		return this.x + ((this.ancho / 2)) >= entorno.ancho() -90;
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
	
}
