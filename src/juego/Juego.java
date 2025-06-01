package juego;

//import java.awt.Color;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;
import entorno.InterfaceJuego;
import java.util.Timer;
import java.util.TimerTask;
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
	private EnemigoFinal[] enemigosFinales = new EnemigoFinal[1];
	private int enemigosGenerados = 0; // Contador de enemigos en la oleada actual
	private int totalEnemigosGenerados = 0; // Contador total de enemigos generados
	private final int MAX_ENEMIGOS = 50;
	private final int MAX_ENEMIGOS_EN_PANTALLA = 10;
	private int oleadaActual = 1;
	private int enemigosEnOleada = 5;
	private boolean entreOleadas = false;
	private Timer timerEntreOleadas = new Timer();
	private Image fondo;
	private Menu menu;
	private HechizoFuego hechizo1;
	private HechizoOndaExpansiva hechizo2;
	private boolean mouseClickAnterior = false;
	private GameOver gameover;
	private Pocion pocion;

	public void generarEnemigos() {
		// int x = entorno.ancho() / 2;
		// int y = 50;
		// enemigosFinales[0] = new EnemigoFinal(x, y);
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				synchronized (this) {
					if (menu.getPuntos() == MAX_ENEMIGOS) {
						// Generar enemigo final cuando se acaban los enemigos normales
						if (enemigosFinales[0] == null) {
							int x = entorno.ancho() / 2;
							int y = 50;
							enemigosFinales[0] = new EnemigoFinal(x, y);
						}
						return;
					}

					// Contar enemigos activos
					int activos = 0;
					for (Enemigo enemigo : enemigosActivos) {
						if (enemigo != null)
							activos++;
					}

					// Verifica si termino la oleada actual
					if (enemigosGenerados == enemigosEnOleada && activos == 0 && !entreOleadas
							&& totalEnemigosGenerados < MAX_ENEMIGOS - 3) {
						System.out.println("Enemigos generados en oleada: " + enemigosGenerados);
						entreOleadas = true;
						timerEntreOleadas.schedule(new TimerTask() {
							@Override
							public void run() {
								// Inicia nueva oleada despué del tiempo de espera
								entreOleadas = false;
								oleadaActual++;
								enemigosGenerados = 0; // Reinicia solo el contador de la oleada
								enemigosEnOleada += 3; // Aumentar enemigos para la próxima oleada
								if (enemigosEnOleada > 15)
									enemigosEnOleada = 15; // Límite de enemigos por oleada
							}
						}, 3000); // 3 segundos de espera
						return;
					}

					// Verificar si ya alcanzamos el máximo de enemigos
					if (totalEnemigosGenerados >= MAX_ENEMIGOS) {
						return;
					}

					// Generar nuevos enemigos si no estamos entre oleadas
					if (!entreOleadas && activos < MAX_ENEMIGOS_EN_PANTALLA && enemigosGenerados < enemigosEnOleada) {
						int borde = random.nextInt(4);
						int x = 0, y = 0;
						int anchoEnemigo = 25;
						int anchoMenu = 200;

						switch (borde) {
							case 0: // Borde superior
								x = random.nextInt(entorno.ancho());
								if (x > anchoMenu)
									x = x - anchoMenu;
								y = anchoEnemigo;
								break;
							case 1: // Borde inferior
								x = random.nextInt(entorno.ancho());
								if (x > anchoMenu)
									x = x - anchoMenu;
								y = entorno.alto() - anchoEnemigo;
								break;
							case 2: // Borde izquierdo
								x = anchoEnemigo;
								y = random.nextInt(entorno.alto() - anchoEnemigo);
								if (y < anchoMenu)
									y = y + anchoMenu;
								break;
							case 3: // Borde derecho
								x = entorno.ancho() - anchoMenu;
								y = random.nextInt(entorno.alto() - anchoEnemigo);
								if (y < anchoMenu)
									y = y + anchoMenu;
								break;
						}
						Enemigo enemigo = new Enemigo(x, y);
						for (int i = 0; i < enemigosActivos.length; i++) {
							if (enemigosActivos[i] == null) {
								enemigosActivos[i] = enemigo;
								enemigosGenerados++;
								totalEnemigosGenerados++; // Incrementamos el contador total
								System.out.println("Enemigos totales generados: " + totalEnemigosGenerados);
								break;
							}
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
		menu = new Menu();
		this.personaje = new Personaje(entorno.ancho() / 2, 220, 80, 63);
		pocion = new Pocion();

		// inicializa el gameover
		this.gameover = new GameOver();

		/* Crea las rocas en diferentes posiciones */
		this.rocas = new Roca[5];
		rocas[0] = new Roca(170, 120); // superior
		rocas[1] = new Roca(110, 250); // izquierda media
		rocas[2] = new Roca(475, 250); // derecha media
		rocas[3] = new Roca(250, 360); // izquierda base
		rocas[4] = new Roca(550, 400); // derecha base

		// inicia el objeto hechizo de fuego
		this.hechizo1 = new HechizoFuego(0, 0);

		// inicia el objeto hechizo de onda expansiva
		this.hechizo2 = new HechizoOndaExpansiva();

		// Inicia el juego!
		this.entorno.iniciar();
		this.menu.dibujar(entorno, personaje);
		this.generarEnemigos();

	}

	public void tick() {
		// Fondo
		this.entorno.dibujarImagen(this.fondo, this.entorno.ancho() / 2, this.entorno.alto() / 2, 0.0, 1.0);
		// Mostra informacion de la oleada
		entorno.cambiarFont("Arial", 15, java.awt.Color.white, entorno.NEGRITA);
		entorno.escribirTexto("Oleada: " + oleadaActual, 10, 20);
		if (entreOleadas) {
			entorno.escribirTexto("¡Oleada " + (oleadaActual) + " completada!", entorno.ancho() / 2 - 100, 30);
			entorno.escribirTexto("Preparando siguiente oleada...", entorno.ancho() / 2 - 100, 50);
		}

		// Muestra informacion de la oleada
		entorno.cambiarFont("Arial", 15, java.awt.Color.white, entorno.NEGRITA);
		entorno.escribirTexto("Oleada: " + oleadaActual, 10, 20);

		if (entreOleadas) {
			entorno.escribirTexto("¡Oleada " + (oleadaActual) + " completada!", entorno.ancho() / 2 - 100, 30);
			entorno.escribirTexto("Preparando siguiente oleada...", entorno.ancho() / 2 - 100, 50);
		}

		// inicia el game over
		if (personaje.getVida() == 0) {
			gameover.dibujarse(entorno);

			return; // detiene el tick
		}
		// texto ganaste//
		if (menu.getPuntos() >= 51) {
			entorno.cambiarFont("Arial", 100, java.awt.Color.green, entorno.NEGRITA);
			entorno.escribirTexto("GANASTE", 90, 300);
			return; // detiene el tick
		}

		// Dibujar objetos en pantalla
		this.dibujarObjetos();
		// Movimiento del personaje
		if (entorno.estaPresionada(entorno.TECLA_DERECHA)
				&& !personaje.colisionaPorDerecha(entorno)) {
			personaje.moverDerecha(entorno);
		}
		if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA)
				&& !personaje.colisionaPorIzquierda(entorno)) {
			personaje.moverIzquierda(entorno);
		}
		if (entorno.estaPresionada(entorno.TECLA_ABAJO)) {
			personaje.moverAbajo(entorno);
		}
		if (entorno.estaPresionada(entorno.TECLA_ARRIBA)) {
			personaje.moverArriba(entorno);
		}

		// Movimiento y actualizacion de enemigos normales
		for (int i = 0; i < enemigosActivos.length; i++) {
			if (enemigosActivos[i] != null) {
				enemigosActivos[i].seguirPersonaje(this.personaje);

				if (enemigosActivos[i].fueraDeLimite(entorno)) {
					enemigosActivos[i] = null;
					menu.sumarPuntos();
				} else if (this.personaje.colisionConEnemigo(enemigosActivos[i])) {
					enemigosActivos[i] = null;
					if (personaje.getVida() > 0) {
						personaje.restarVida();
					}
					menu.sumarPuntos();
				}
			}
		}
		if (enemigosFinales[0] != null && enemigosFinales[0].getVidas() > 0) {
			if (this.personaje.colisionConEnemigoFinal(enemigosFinales[0])) {
				if (personaje.getVida() > 0) {
					personaje.restarVida();
				}
			}
		}

		// Actualizar y dibujar hechizo de onda expansiva
		if (hechizo2.estaActivo()) {
			hechizo2.aplicarEfecto(enemigosActivos, menu);
			hechizo2.dibujarse(entorno);
			hechizo2.actualizar();
		}

		// Movimiento y actualizacion del enemigo final
		if (enemigosFinales[0] != null && enemigosFinales[0].getVidas() > 0) {
			EnemigoFinal enemigoFinal = enemigosFinales[0];
			enemigoFinal.seguirPersonaje(this.personaje);
			// Verifica la colision con hechizo de fuego
			if (hechizo1.estaActivo()) {
				// Verifica la colision simple basada en posicion
				double distancia = Math.sqrt(
						Math.pow(hechizo1.getX() - enemigoFinal.getX(), 2) +
								Math.pow(hechizo1.getY() - enemigoFinal.getY(), 2));
				if (distancia < 100) { // Radio de efecto del hechizo
					enemigoFinal.recibirDaño();
				}
			}

			// Verificar colisión con hechizo de onda expansiva
			if (hechizo2.estaActivo()) {
				// El hechizo de onda afecta a todos los enemigos en pantalla
				enemigoFinal.recibirDaño();
			}

			// Verifica si el enemigo final esta eliminado despues de todos los daños
			if (enemigoFinal.getVidas() == 0) {
				enemigosFinales[0] = null;
				menu.sumarPuntos();
			}
		}

		// colision entre personaje y las rocas
		for (Roca roca : rocas) {
			if (this.personaje.colisionConRoca(roca)) {
				manejarColisionesConRoca(roca);
			}
		}
		// disparo y colicion con enemigos
		// Verificar colisiones con enemigos
		for (int i = 0; i < enemigosActivos.length; i++) {
			Enemigo enemigo = enemigosActivos[i];
			if (enemigo != null && hechizo1.EnemigoEnRango(enemigo)) {
				enemigosActivos[i] = null;
				menu.sumarPuntos(); // sumás puntos si lo eliminás
			}
		}
		// Deteccion de click en el menu
		boolean mouseClickActual = entorno.sePresionoBoton(entorno.BOTON_IZQUIERDO);
		if (mouseClickActual && !mouseClickAnterior) {
			menu.detectarClick(entorno.mouseX(), entorno.mouseY());
		}
		mouseClickAnterior = mouseClickActual;

		// Uso de hechizos
		if (entorno.sePresionoBoton(entorno.BOTON_IZQUIERDO)) {

			// Al lanzar el hechizo
			if (menu.getBotonFuego() && entorno.sePresionoBoton(entorno.BOTON_IZQUIERDO)) {
				if (entorno.mouseX() < 550) {
					hechizo1.setPosicion(entorno.mouseX(), entorno.mouseY());
					hechizo1.reiniciar();
					menu.liberarSeleccion();
				}
			}

			// actualiza el esta de hechizo 1, si esta activo de dibuja
			hechizo1.actualizar();
			if (hechizo1.estaActivo()) {
				hechizo1.dibujarse(entorno);

			}

			// Hechizo Onda
			if (menu.getBotonOnda() && personaje.getPoder() > 0) {
				if (entorno.mouseX() < 550) {
					hechizo2.activar(entorno.mouseX(), entorno.mouseY());
					hechizo2.reiniciar();
					menu.liberarSeleccion();
					if (personaje.getPoder() > 0) {
						personaje.restarPoder();
					}
				}
			}
		}
		// Si hay poción y el personaje la toca
		if (pocion != null && personaje.colisionConPocion(pocion)) {
			personaje.sumarVida();
			pocion = null; // para que desaparezca
		}

		// actualizacion y dibujo de clicks
		if (hechizo1.estaActivo()) {
			hechizo1.actualizar();
			hechizo1.dibujarse(entorno);
		}

		if (hechizo2.estaActivo()) {
			hechizo2.actualizar();
			hechizo2.dibujarse(entorno);
		}

	}

	public void manejarColisionesConRoca(Roca roca) {

		// calcula la direccion y distancia de la roca y el pj
		int direcX = personaje.getX() - roca.getX();
		int direcY = personaje.getY() - roca.getY();

		// si la distancia entre x es mayor, quiere decir que primero va a tocar de
		// izquierda o derecha

		if (Math.abs(direcX) > Math.abs(direcY)) {
			if (direcX > 0) {
				personaje.moverDerecha(entorno);
			} else {
				personaje.moverIzquierda(entorno);
			}
		}
		// sino va a tocar de arriba o abajo primero
		else {
			if (direcY > 0) {
				personaje.moverAbajo(entorno);
			} else {
				personaje.moverArriba(entorno);
			}
		}

	}

	public void dibujarObjetos() {
		// Dibujar rocas
		for (Roca roca : rocas) {
			if (roca != null) {
				roca.dibujarse(entorno);
			}
		}

		// Dibujar enemigos normales
		for (Enemigo enemigo : enemigosActivos) {
			if (enemigo != null) {
				enemigo.dibujarse(entorno);
			}
		}

		// Dibujar enemigo final si existe
		if (enemigosFinales[0] != null && enemigosFinales[0].getVidas() > 0) {
			enemigosFinales[0].dibujarse(entorno);
			// Mostrar vidas del enemigo final
			entorno.cambiarFont("Arial", 15, java.awt.Color.white, entorno.NEGRITA);
			entorno.escribirTexto("Vidas: " + enemigosFinales[0].getVidas(),
					enemigosFinales[0].getX(), enemigosFinales[0].getY() - 40);
		}

		// Dibujar pocion//
		personaje.dibujar(entorno);
		if (pocion != null && menu.getPuntos() > 20 && personaje.getVida() <= 70) { // la pocion aparece si los puntos
																					// es mayor a 20 y la vida
																					// es igual o menor a 70
			pocion.dibujarse(entorno);
		}

		// Dibujar menu//
		menu.dibujar(entorno, personaje);
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}
}