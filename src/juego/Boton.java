package juego;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Boton {
    
	
	private int x;
	private int y;
	private int ancho;
	private int alto;
	private Image botonOff;
	private Image botonOn;
	private double escala;
	private boolean sepresiono;

	public Boton(int x,int y, int ancho, int alto){
		this.x=x;
		this.y=y;
		this.ancho = ancho;
		this.alto = alto;
		this.escala= 0.1;
		this.botonOn=Herramientas.cargarImagen("boton1-activado.png");
		this.botonOff=Herramientas.cargarImagen("boton1-desactivado.png");
		this.sepresiono=false;
		
	}
	public void dibujarse(Entorno entorno) {
		if (sepresiono) {
			entorno.dibujarImagen(botonOn, x, y, 0, escala); /* Imagen cuando se presiona*/
		} else {
			entorno.dibujarImagen(botonOff, x, y, 0, escala);  /*Imagen sin estar presionado*/
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
	public boolean mouseEstaSobreElBoton(int mouseX, int mouseY) {
		int ladoIzquierdo = this.x - this.ancho/2;
		int ladoDerecho = this.x + this.ancho/2;
		int ladoSuperior = this.y - this.alto/2;
		int ladoInferior = this.y + this.alto/2;
		
		if(mouseX >= ladoIzquierdo && mouseX <= ladoDerecho && mouseY >= ladoSuperior && mouseY <= ladoInferior) {
			return true;
		}
		else {
			return false;
		}
		
	}
}

