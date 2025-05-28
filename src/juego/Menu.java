package juego;
import java.awt.Image;
import entorno.Entorno;

public class Menu {
    private Boton botonFuego;
    private Boton1 botonOnda;
    private boolean hechizoSeleccionado;

    public Menu() {
        this.botonFuego = new Boton(720, 150, 100, 50);
        this.botonOnda = new Boton1(720, 270, 100, 50);
        this.hechizoSeleccionado = false;
    }

    public void detectarClick(int mouseX, int mouseY) {
        // Si no hay hechizo seleccionado, permite seleccionar uno nuevo
        if (!hechizoSeleccionado) {
            if (botonFuego.mouseEstaSobreElBoton(mouseX, mouseY)) {
                botonFuego.presionar();
                botonOnda.soltar();
                hechizoSeleccionado = true;
            } 
            else if (botonOnda.mouseEstaSobreElBoton(mouseX, mouseY)) {
                botonOnda.presionar();
                botonFuego.soltar();
                hechizoSeleccionado = true;
            }
        }
    }

    public void liberarSeleccion() {
        botonFuego.soltar();
        botonOnda.soltar();
        hechizoSeleccionado = false;
    }

    public void dibujar(Entorno entorno) {
        botonFuego.dibujarse(entorno);
        botonOnda.dibujarse(entorno);
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
    
}



	
