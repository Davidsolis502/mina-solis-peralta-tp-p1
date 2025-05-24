package juego;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Boton {
    
	
	private int x;
	private int y;
	private Image boton;
	private Image boton1;
	private double escala;
	private boolean sepresiono;

	public Boton(int x,int y){
		this.x=x;
		this.y=y;
		this.escala= 0.1;
		this.boton=Herramientas.cargarImagen("Hechizo-desactivado.png");
		this.boton1=Herramientas.cargarImagen("Hechizo-activado.png");
		this.sepresiono=false;
		
	}
	public void dibujarse(Entorno entorno) {
		if (sepresiono) {
			entorno.dibujarImagen(boton1, x, y, 0, escala); // Imagen cuando se presiona
		} else {
			entorno.dibujarImagen(boton, x, y, 0, escala);  // Imagen por defecto
		}
	}
	public void presionar() {
		sepresiono = true;
	}
	
	public void soltar() {
		sepresiono = false;
	}
	
	public boolean estaPresionado() {
		return sepresiono;
	}
	
	

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
