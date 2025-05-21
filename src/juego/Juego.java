package juego;

import java.awt.Color;
import java.awt.Image;
import javax.swing.ImageIcon;

import entorno.Entorno;
import entorno.Herramientas;
import entorno.InterfaceJuego;
import java.util.Timer;
import java.util.TimerTask;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Juego extends InterfaceJuego {
	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;
	Timer timer = new Timer();
	Random random = new Random();
	// Variables del juego
	private Roca[] rocas;
	private Personaje personaje;
	private Enemigo enemigo;
	private List<Enemigo> enemigosActivos = new ArrayList<>();
	private int enemigosGenerados = 0;
	private int MAX_ENEMIGOS = 50;
	private int MAX_ENEMIGOS_EN_PANTALLA = 10;
	private int puntos;
	private Image fondo;


public void generarEnemigos() {
    timer.scheduleAtFixedRate(new TimerTask() {
        @Override
        public void run() {
            if (enemigosGenerados >= MAX_ENEMIGOS) {
                timer.cancel();
                return;
            }

            if (enemigosActivos.size() < MAX_ENEMIGOS_EN_PANTALLA) {
                Enemigo enemigo = new Enemigo(
                    random.nextInt(entorno.ancho()),
                    random.nextInt(entorno.alto())
                );
                enemigosActivos.add(enemigo);
                enemigosGenerados++;
            }
        }
    }, 0, 500); // Intenta generar cada 500ms
}

	Juego() {
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Proyecto para TP", 800, 600);
		this.fondo = Herramientas.cargarImagen("fondo-juego.jpg");

		// Inicializar variables del juego
		this.personaje = new Personaje(entorno.ancho() / 2, 500, 200, 50);
		// this.enemigo = new Enemigo( entorno.ancho() , 35);
		
		this.puntos = 0;
		
		// Crear las rocas en posiciones aleatorias
		this.rocas = new Roca[5];
        rocas[0] = new Roca(170, 150); // superior
        rocas[1] = new Roca(325, 250); // izquierda media
        rocas[2] = new Roca(475, 250); // derecha media
        rocas[3] = new Roca(250, 350); // izquierda base
        rocas[4] = new Roca(550, 350); // derecha base


		// Inicia el juego!
		this.entorno.iniciar();
		this.generarEnemigos();
	}

	public void tick() {
		// Fondo
		this.entorno.dibujarImagen(this.fondo, this.entorno.ancho() / 2, this.entorno.alto() / 2, 0.0, 1.0);

		// Dibujar objetos en pantalla
		this.dibujarObjetos();

		// Movimiento del personaje
		if (entorno.estaPresionada(entorno.TECLA_DERECHA)
				&& !personaje.colisionaPorDerecha(entorno)) {
			personaje.moverDerecha();
		}
		if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA)
				&& !personaje.colisionaPorIzquierda(entorno)) {
			personaje.moverIzquierda();
		}
		if (entorno.estaPresionada(entorno.TECLA_ABAJO)) {
			personaje.moverAbajo(entorno);
		}
		if (entorno.estaPresionada(entorno.TECLA_ARRIBA)) {
			personaje.moverArriba();
		}

		// Movimiento del enemigo
		if (this.enemigo != null) {
			this.enemigo.moverAbajo();
		}

		// Colisión personaje vs enemigo
		if (this.personaje.colisionConEnemigo(enemigo)) {
			enemigo = null;
			this.puntos++;
		}

		// Eliminar enemigo si sale de la pantalla
		if (this.enemigo != null && this.enemigo.fueraDeLimite(entorno)) {
			this.enemigo = null;
		}
	}

	public void dibujarObjetos() {
		// Dibujar enemigos
		if (this.enemigosActivos != null) {
			for (Enemigo enemigo : this.enemigosActivos) {
				if (enemigo != null) {
					enemigo.dibujarse(entorno);
				}
			}
		}

		// Dibujar rocas
		for (Roca roca : rocas) {
			roca.dibujarse(entorno);
		}

		// Dibujar personaje
		this.personaje.dibujar(entorno);

		// Dibujar puntuación
		this.entorno.cambiarFont("Arial", 20, Color.black);
		this.entorno.escribirTexto("Puntos " + this.puntos, entorno.ancho() - 120, 30);
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}
}
