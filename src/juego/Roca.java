package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Roca {
	private int x;
	private int y;
	private Image roca;
	private double escala;
	
public Roca(int x, int y) {
	this.x = x;
	this.y = y;
	this.escala= 0.05;
	this.roca=Herramientas.cargarImagen("imagen-roca.png");
}
public void dibujarse(Entorno entorno) {
	entorno.dibujarImagen(roca, x, y,0,escala);
}
public int getX() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getX'");
}
public int getY() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getY'");
}
public int getAncho() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getAncho'");
}
}
