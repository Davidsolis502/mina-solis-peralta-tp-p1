package juego;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Boton {
    
	
	private int x;
	private int y;
	private Image boton;
	private double escala;

	public Boton(int x,int y){
		this.x=x;
		this.y=y;
		this.escala= 0.2;
		this.boton=Herramientas.cargarImagen("boton-fire.png");
		
	}
	public void dibujarse(Entorno entorno) {
		entorno.dibujarImagen(boton, x, y,0,escala);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
