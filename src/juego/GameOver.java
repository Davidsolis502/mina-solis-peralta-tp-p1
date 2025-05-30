package juego;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class GameOver {
		private int x;
		private int y;
		private Image Gameover;
		
		public GameOver(){
			
			this.x=400;
			this.y=300;
			this.Gameover= Herramientas.cargarImagen("GameOver.jpg");
			
		}
		
		public void dibujarse (Entorno entorno){
			
			entorno.dibujarImagen(Gameover, x, y, 0);
		}
		
}
		
