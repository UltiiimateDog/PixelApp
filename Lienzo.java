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
    
    public Lienzo(Colores colores, int tamanoPixel) {
        this.colores = colores;
        this.tamanoPixel = tamanoPixel;
        pixeles = new Color[getWidth() / tamanoPixel][getHeight() / tamanoPixel];
        
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                int x = e.getX() / tamanoPixel;
                int y = e.getY() / tamanoPixel;
                if (x < pixeles.length && y < pixeles[0].length) {
                    pixeles[x][y] = colores.getColorActual();
                    repaint();
                }
            }
        });
    }
	
	public void paint(Graphics g) {
        for (int i = 0; i < pixeles.length; i++) {
            for (int j = 0; j < pixeles[i].length; j++) {
                if (pixeles[i][j] != null) {
                    g.setColor(pixeles[i][j]);
                    g.fillRect(i * tamanoPixel, j * tamanoPixel, tamanoPixel, tamanoPixel);
                }
            }
        }
    }
    
    public void limpiar() {
        pixeles = new Color[getWidth() / tamanoPixel][getHeight() / tamanoPixel];
        repaint();
    }
    
    public void setTamanoPixel(int tamanoPixel) {
        this.tamanoPixel = tamanoPixel;
        pixeles = new Color[getWidth() / tamanoPixel][getHeight() / tamanoPixel];
        repaint();
    }
    
    public void setColores(Colores colores) {
        this.colores = colores;
        repaint();
    }
    
    public Color[][] getPixeles() {
        return pixeles;
    }
}