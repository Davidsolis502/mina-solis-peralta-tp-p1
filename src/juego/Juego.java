package juego;

import java.awt.Color;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego
{
	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;
	
	// Variables y métodos propios de cada grupo
	// ...
	private Personaje personaje;
	private Enemigo enemigo;
	private int puntos;
	private Image fondo;

	
	
	Juego()
	{
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Proyecto para TP", 800, 600);
		this.fondo = Herramientas.cargarImagen("fondo-juego.jpg");
		// Inicializar lo que haga falta para el juego
		// ...
		this.personaje = new Personaje(entorno.ancho()/2, 500, 200, 50);
		this.enemigo= new Enemigo(entorno.ancho()/2, 25);
		this.puntos=0;
		// Inicia el juego!
		this.entorno.iniciar();
	}

	/**
	 * Durante el juego, el método tick() será ejecutado en cada instante y 
	 * por lo tanto es el método más importante de esta clase. Aquí se debe 
	 * actualizar el estado interno del juego para simular el paso del tiempo 
	 * (ver el enunciado del TP para mayor detalle).
	 */
	public void tick()
	{
		// Procesamiento de un instante de tiempo
		// ...
		this.entorno.dibujarImagen(this.fondo, this.entorno.ancho() / 2, this.entorno.alto() / 2, 0.0, 1.0);
		this.dibujarObjetos();
		
		if(entorno.estaPresionada(entorno.TECLA_DERECHA)
				&& !personaje.colisionaPorDerecha(entorno)){
			personaje.moverDerecha();
		}
		if(entorno.estaPresionada(entorno.TECLA_IZQUIERDA)
				&& !personaje.colisionaPorIzquierda(entorno)) {
			personaje.moverIzquierda();
		}
		if(entorno.estaPresionada(entorno.TECLA_ABAJO)) {
			personaje.moverAbajo(entorno);
		}
		if(entorno.estaPresionada(entorno.TECLA_ARRIBA)) {
			personaje.moverArriba();
		}
		
		if(this.enemigo!=null) {
			this.enemigo.moverAbajo();			
		}
		
		
		
		if(this.personaje.colisionConEnemigo(enemigo)) {
			enemigo=null;
			this.puntos++;
		}
		
		
		if(this.enemigo!=null && this.enemigo.fueraDeLimite(entorno)) {
			this.enemigo=null;
		}
		
	}
	
	public void dibujarObjetos() {
		if(this.enemigo!=null) {
			this.enemigo.dibujarse(entorno);			
		}
		this.personaje.dibujar(entorno);
		
		this.entorno.cambiarFont("Arial", 20,Color.black);
		this.entorno.escribirTexto("Puntos "+this.puntos, entorno.ancho()-120, 30);
	}
	
	
	
	
	

	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
}
