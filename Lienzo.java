package PixelApp;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Lienzo extends Canvas {
    
    private Colores colores;
    private int tamanoPixel;
    private Color[][] pixeles;
    private int lastPixelX = -1;
    private int lastPixelY = -1;
    
    public Lienzo(Colores colores, int tamanoPixel) {
        this.colores = colores;
        this.tamanoPixel = tamanoPixel;
        pixeles = new Color[getWidth() / tamanoPixel][getHeight() / tamanoPixel];
        
        addMouseListener(new MouseAdapter() {   //Pinta cuando se hace click
            public void mousePressed(MouseEvent e) {
                int x = e.getX() / tamanoPixel;
                int y = e.getY() / tamanoPixel;
                if (x < pixeles.length && y < pixeles[0].length) {
                    pixeles[x][y] = colores.getColorActual();
                    lastPixelX = x;
                    lastPixelY = y;
                    repaintPixel(x, y);
                }
            }
        });
        addMouseMotionListener(new MouseAdapter() { //Pinta cuando se mantiene el click
            public void mouseDragged(MouseEvent e) {
                int x = e.getX() / tamanoPixel;
                int y = e.getY() / tamanoPixel;
                if (x < pixeles.length && y < pixeles[0].length) {
                    pixeles[x][y] = colores.getColorActual();
                    if (x != lastPixelX || y != lastPixelY) {
                        repaintPixel(x, y);
                        lastPixelX = x;
                        lastPixelY = y;
                    }
                }
            }
        });
    }
    
    private void repaintPixel(int x, int y) { //Pinta el lienzo
        Graphics g = getGraphics();
        g.setColor(pixeles[x][y]);
        g.fillRect(x * tamanoPixel, y * tamanoPixel, tamanoPixel, tamanoPixel);
        g.setColor(Color.BLACK);
        for (int i = 0; i <= getWidth(); i += tamanoPixel) {
            g.drawLine(i, 0, i, getHeight());
        }
        for (int i = 0; i <= getHeight(); i += tamanoPixel) {
            g.drawLine(0, i, getWidth(), i);
        }
    }
    
    public void paint(Graphics g) { //Crea el lienzo
        g.setColor(Color.BLACK);
        for (int i = 0; i <= getWidth(); i += tamanoPixel) {
            g.drawLine(i, 0, i, getHeight());
        }
        for (int i = 0; i <= getHeight(); i += tamanoPixel) {
            g.drawLine(0, i, getWidth(), i);
        }
        if (lastPixelX >= 0 && lastPixelY >= 0 && pixeles[lastPixelX][lastPixelY] != null) {
            g.setColor(pixeles[lastPixelX][lastPixelY]);
            g.fillRect(lastPixelX * tamanoPixel, lastPixelY * tamanoPixel, tamanoPixel, tamanoPixel);
        }
    }

    public void dibujarSinCuadricula(Graphics g) {  //Crea el lienzo sin cuadricula
        for (int i = 0; i < pixeles.length; i++) {
            for (int j = 0; j < pixeles[i].length; j++) {
                if (pixeles[i][j] != null) {
                    g.setColor(pixeles[i][j]);
                    g.fillRect(i * tamanoPixel, j * tamanoPixel, tamanoPixel, tamanoPixel);
                }
            }
        }
    }
    
    public void limpiar() { //Limpia el lienzp
        pixeles = new Color[getWidth() / tamanoPixel][getHeight() / tamanoPixel];
        repaint();
    }
    
    public void setTamanoPixel(int tamanoPixel) {   //Cambia el tamaño del pixel
        this.tamanoPixel = tamanoPixel;
        pixeles = new Color[getWidth() / tamanoPixel][getHeight() / tamanoPixel];
        repaint();
    }
    
    public void setColores(Colores colores) {
        this.colores = colores;
        
    }
    
    public Color[][] getPixeles() {
        return pixeles;
    }
}