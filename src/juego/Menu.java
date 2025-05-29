package juego;
//import java.awt.Image;
import entorno.Entorno;
//import java.awt.Color;

public class Menu {
    private Boton botonFuego;
    private Boton1 botonOnda;
    private boolean hechizoSeleccionado;
    private int puntos;
    private Personaje personaje;

    public Menu() {
        this.botonFuego = new Boton(720, 150, 100, 50);
        this.botonOnda = new Boton1(720, 270, 100, 50);
        this.hechizoSeleccionado = false;
        this.puntos=0;
    }

    public void detectarClick(int mouseX, int mouseY) {
        // Si no hay hechizo seleccionado, permite seleccionar uno nuevo
        if (!hechizoSeleccionado) {
            if (botonFuego.mouseEstaSobreElBoton(mouseX, mouseY)) {
                botonFuego.presionar();
                botonOnda.soltar();
                //hechizoSeleccionado = true;
            } 
            else if (botonOnda.mouseEstaSobreElBoton(mouseX, mouseY)) {
                botonOnda.presionar();
                botonFuego.soltar();
                //hechizoSeleccionado = true;
            }
        }
    }

    public void liberarSeleccion() {
        botonFuego.soltar();
        botonOnda.soltar();
        hechizoSeleccionado = false;
    }

    public void dibujar(Entorno entorno, Personaje personaje) {
        // Dibujar botones
        botonFuego.dibujarse(entorno);
        botonOnda.dibujarse(entorno);
    
        // Dibujar puntos
        entorno.cambiarFont("Arial", 20, java.awt.Color.BLACK, entorno.NEGRITA);
        entorno.escribirTexto("Puntos: " + this.puntos, entorno.ancho() - 120, 30);
    
        // Dibuja texto de vida
        entorno.cambiarFont("Arial", 20, java.awt.Color.BLACK, entorno.NEGRITA);
        entorno.escribirTexto("VIDA", entorno.ancho() - 133, 400);
    
        // Cambiar color según la vida
        if (personaje.getVida() >= 30) {
            entorno.cambiarFont("Arial", 20, java.awt.Color.BLACK, entorno.NEGRITA);
        } else {
            entorno.cambiarFont("Arial", 20, java.awt.Color.RED, entorno.NEGRITA);
        }
        entorno.escribirTexto(personaje.getVida() + "/100", entorno.ancho() - 80, 400);

        // dibuja texto de poder
        entorno.cambiarFont("Arial", 20, java.awt.Color.BLACK, entorno.NEGRITA);
        entorno.escribirTexto("PODER " +personaje.getPoder()+"/100",entorno.ancho()-155,450);

    }
    

    //public void actualizar(Entorno entorno) {
        // Detectar si se hace clic con ENTER o alguna tecla
        //if (entorno.sePresiono(entorno.TECLA_DERECHA)) {
        //    botonFuego.presionar();
      //  } //else {
            //botonHechizo.soltar();
        //}
   // }

  
	public Boolean getBotonFuego() {
		return botonFuego.estaPresionado();
	}

	public Boolean getBotonOnda() {
		return botonOnda.estaPresionado();
	}
    public void sumarPuntos(){
        puntos++;
    }
    
}



	
