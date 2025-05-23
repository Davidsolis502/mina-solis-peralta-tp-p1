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
	private Enemigo[] enemigosActivos = new Enemigo[10];
	private int enemigosGenerados = 0;
	private final int MAX_ENEMIGOS = 50;
	private final int MAX_ENEMIGOS_EN_PANTALLA = 10;
	private int puntos;
	private Image fondo;
	private Boton boton;

public void generarEnemigos() {
    timer.scheduleAtFixedRate(new TimerTask() {
        @Override
        public void run() {
            if (enemigosGenerados >= MAX_ENEMIGOS) {
                timer.cancel();
                return;
            }
            int activos = 0;
            for (Enemigo enemigo : enemigosActivos) {
                if (enemigo != null) activos++;
            }

            if (activos < MAX_ENEMIGOS_EN_PANTALLA) {
                int borde = random.nextInt(4); 
                int x = 0, y = 0;
                int anchoEnemigo = 25;
                int anchoMenu = 200;

                switch (borde) {
                    case 0: // Borde superior
                        x = random.nextInt(entorno.ancho());
						if (x > anchoMenu) x = x - anchoMenu;
                        y = anchoEnemigo;
                        break;
                    case 1: // Borde inferior
                        x = random.nextInt(entorno.ancho());
						if (x > anchoMenu) x = x - anchoMenu;
                        y = entorno.alto() - anchoEnemigo;
                        break;
                    case 2: // Borde izquierdo
                        x = anchoEnemigo;
                        y = random.nextInt(entorno.alto() - anchoEnemigo);
						if(y<anchoMenu) y=y+anchoMenu;
                        break;
                    case 3: // Borde derecho
                        x = entorno.ancho() - anchoMenu;
                        y = random.nextInt(entorno.alto() - anchoEnemigo);
                        if(y<anchoMenu) y=y+anchoMenu;
                        break;
                }
                Enemigo enemigo = new Enemigo(x, y);
                for (int i = 0; i < enemigosActivos.length; i++) {
                    if (enemigosActivos[i] == null) {
                        enemigosActivos[i] = enemigo;
						enemigo.setIndice(i);
                        enemigosGenerados++;
                        break;
                    }
                }
            }
        }
    }, 0, 100);
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
        rocas[0] = new Roca(170, 120); // superior
        rocas[1] = new Roca(110, 250); // izquierda media
        rocas[2] = new Roca(475, 250); // derecha media
        rocas[3] = new Roca(250, 360); // izquierda base
        rocas[4] = new Roca(550, 400); // derecha base
        
        //crea el boton de hechizo de fuego
        this.boton = new Boton(720,200);

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

		// Movimiento y actualización de enemigos
		for (int i = 0; i < enemigosActivos.length; i++) {
		    if (enemigosActivos[i] != null) {
		        enemigosActivos[i].seguirPersonaje(this.personaje);
		        
		        if (this.personaje.colisionConEnemigo(enemigosActivos[i])) {
		            enemigosActivos[i] = null;
		            this.puntos++;
		        }
		        else if (enemigosActivos[i].fueraDeLimite(entorno)) {
		            enemigosActivos[i] = null;
		        }
		    }
		}

		//colision entre personaje y las rocas
		for (Roca roca : rocas) {
			if (this.personaje.colisionConRoca(roca)) {
				manejarColisionesConRoca(roca);
			}
		}

		
	}
	public void manejarColisionesConRoca(Roca roca) {
		
		//calcula la direccion y distancia de la roca y el pj
		int direcX= personaje.getX()-roca.getX();
		int direcY= personaje.getY()-roca.getY();
		
		//si la distancia entre x es mayor, quiere decir que primero va a tocar de izquierda o derecha
		
		if(Math.abs(direcX)>Math.abs(direcY)) {
			if (direcX>0) {
				personaje.moverDerecha();
			}
			else {
				personaje.moverIzquierda();
			}
		}
		//sino va a tocar de arriba o abajo primero
		else {
			if (direcY >0) {
				personaje.moverAbajo(entorno);
			}
			else {
				personaje.moverArriba();
			}
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
		
		this.boton.dibujarse(entorno);
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}
}
