package juego;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Boton1 {
    
	
	private int x;
	private int y;
	private int ancho;
	private int alto;
	private Image boton1off;
	private Image boton1on;
	private double escala;
	private boolean sepresiono;

	public Boton1(int x,int y, int ancho, int alto){
		this.x=x;
		this.y=y;
		this.ancho = ancho;
		this.alto = alto;
		this.escala= 0.1;
		this.boton1on=Herramientas.cargarImagen("boton2-activado.png");
		this.boton1off=Herramientas.cargarImagen("boton2-desactivado.png");
		this.sepresiono=false;
		
	}
	public void dibujarse(Entorno entorno) {
		if (sepresiono) {
			entorno.dibujarImagen(boton1on, x, y, 0, escala); // Imagen cuando se presiona
		} else {
			entorno.dibujarImagen(boton1off, x, y, 0, escala);  // Imagen por defecto
		}
		//if (sepresiono) {
			//entorno.dibujarImagen(boton2on, x, y, 0, escala); // Imagen cuando se presiona
		//} else {
			//entorno.dibujarImagen(boton2off, x, y, 0, escala);  // Imagen por defecto
		//}
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

