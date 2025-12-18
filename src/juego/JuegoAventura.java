package juego;

import processing.core.PApplet;
import processing.core.PImage;

public class JuegoAventura extends PApplet {

    // --- IMÁGENES ---
    PImage imgFondo, imgHeroe, imgEnemigo, imgTesoro;

    // --- POSICIONES ---
    float xHeroe = 100, yHeroe = 100;
    float xEnemigo = 700, yEnemigo = 500;
    float xTesoro = 700, yTesoro = 100;

    // --- MOVIMIENTO SUAVE (Interruptores) ---
    boolean arriba, abajo, izquierda, derecha;
    float velocidad = 4;

    // --- ESTADO DEL JUEGO ---
    // 0 = Jugando, 1 = Ganaste, 2 = Perdiste
    int estadoJuego = 0; 

    public static void main(String[] args) {
        PApplet.main("juego.JuegoAventura");
    }

    @Override
    public void settings() {
        size(800, 600);
    }

    @Override
    public void setup() {
        // Carga las imágenes (recuerda ponerlas en la carpeta del proyecto)
        imgFondo = loadImage("./imagenes/fondo.png");
        imgHeroe = loadImage("./imagenes/heroe.png");
        imgEnemigo = loadImage("./imagenes/fantasma.png");
        imgTesoro = loadImage("./imagenes/cofre.png");

        // Redimensionar para que encajen
        imgFondo.resize(width, height);
        imgHeroe.resize(50, 50);
        imgEnemigo.resize(50, 50);
        imgTesoro.resize(40, 40);
    }

    @Override
    public void draw() {
        
        if (estadoJuego == 0) {
            // --- PARTE A: JUGANDO ---
            
            // 1. Dibujar Fondo
            image(imgFondo, 0, 0);

            // 2. Dibujar Objetos
            image(imgTesoro, xTesoro, yTesoro);
            image(imgHeroe, xHeroe, yHeroe);
            image(imgEnemigo, xEnemigo, yEnemigo);

            // 3. Mover Héroe (Basado en los interruptores)
            if (arriba) yHeroe -= velocidad;
            if (abajo) yHeroe += velocidad;
            if (izquierda) xHeroe -= velocidad;
            if (derecha) xHeroe += velocidad;

            // 4. IA del Enemigo (Perseguir al héroe)
            // El fantasma se mueve un poco más lento (velocidad 2.5) hacia el jugador
            if (xHeroe > xEnemigo) xEnemigo += 2.5;
            if (xHeroe < xEnemigo) xEnemigo -= 2.5;
            if (yHeroe > yEnemigo) yEnemigo += 2.5;
            if (yHeroe < yEnemigo) yEnemigo -= 2.5;

            // 5. DETECTAR COLISIONES (La magia matemática)
            // dist(x1, y1, x2, y2) calcula la distancia en píxeles entre dos puntos
            
            // ¿Tocó el tesoro? (A menos de 50 píxeles)
            if (dist(xHeroe, yHeroe, xTesoro, yTesoro) < 50) {
                estadoJuego = 1; // GANAR
            }

            // ¿Tocó al fantasma?
            if (dist(xHeroe, yHeroe, xEnemigo, yEnemigo) < 50) {
                estadoJuego = 2; // PERDER
            }

        } else if (estadoJuego == 1) {
            // --- PARTE B: GANASTE ---
            background(0, 255, 0); // Fondo Verde
            textSize(50);
            textAlign(CENTER);
            fill(0);
            text("¡HAS GANADO!", width/2, height/2);
            textSize(20);
            text("Pulsa R para reiniciar", width/2, height/2 + 50);

        } else if (estadoJuego == 2) {
            // --- PARTE C: PERDISTE ---
            background(255, 0, 0); // Fondo Rojo
            textSize(50);
            textAlign(CENTER);
            fill(255);
            text("¡GAME OVER!", width/2, height/2);
            textSize(20);
            text("Pulsa R para reiniciar", width/2, height/2 + 50);
        }
      
    }

    // --- CONTROL DE TECLADO (EVENTOS) ---
    // Esto se ejecuta AUTOMÁTICAMENTE cuando pulsas una tecla
    @Override
    public void keyPressed() {
        if (keyCode == UP || key == 'w') arriba = true;
        if (keyCode == DOWN || key == 's') abajo = true;
        if (keyCode == LEFT || key == 'a') izquierda = true;
        if (keyCode == RIGHT || key == 'd') derecha = true;
        
        // Reiniciar juego con 'r'
        if (key == 'r' || key == 'R') {
            xHeroe = 100; yHeroe = 100;
            xEnemigo = 700; yEnemigo = 500;
            estadoJuego = 0;
        }
    }

    // Esto se ejecuta AUTOMÁTICAMENTE cuando sueltas una tecla
    @Override
    public void keyReleased() {
        if (keyCode == UP || key == 'w') arriba = false;
        if (keyCode == DOWN || key == 's') abajo = false;
        if (keyCode == LEFT || key == 'a') izquierda = false;
        if (keyCode == RIGHT || key == 'd') derecha = false;
    }
}