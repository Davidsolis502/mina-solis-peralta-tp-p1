package juego;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class HechizoFuego {


	private int x;
	private int y;
	private Image HechizoFuego;
	private double escala;
	
	
	public HechizoFuego(){
		
		this.x=x;
		this.y=y;
		this.HechizoFuego=Herramientas.cargarImagen("HechizoBasico.gif");
		this.escala=0.4;
	}
	
	public void dibujarse(Entorno entorno) {
		entorno.dibujarImagen(HechizoFuego, x, y, escala);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
