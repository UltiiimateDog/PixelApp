package PixelApp;

import java.awt.Color;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Colores {
    
    private Color[] coloresPredeterminados;
    private Color colorActual;
    private int rojo;
    private int verde;
    private int azul;
    private PropertyChangeSupport propertySupport;

    public Colores() {
        coloresPredeterminados = new Color[] { Color.WHITE, Color.BLACK, Color.RED, Color.GREEN, Color.BLUE };
        colorActual = Color.BLACK;
        rojo = colorActual.getRed();
        verde = colorActual.getGreen();
        azul = colorActual.getBlue();
        propertySupport = new PropertyChangeSupport(this);
    }

    // Getters y setters para los colores y los valores RGB
    public Color[] getColoresPredeterminados() {
        return coloresPredeterminados;
    }

    public void setColorActual(Color colorActual) {
        Color oldValue = this.colorActual;
        this.colorActual = colorActual;
        rojo = colorActual.getRed();
        verde = colorActual.getGreen();
        azul = colorActual.getBlue();
        propertySupport.firePropertyChange("colorActual", oldValue, colorActual);
    }

    public Color getColorActual() {
        return colorActual;
    }

    public int getRojo() {
        return rojo;
    }

    public void setRojo(int rojo) {
        int oldValue = this.rojo;
        this.rojo = rojo;
        colorActual = new Color(rojo, verde, azul);
        propertySupport.firePropertyChange("rojo", oldValue, rojo);
    }

    public int getVerde() {
        return verde;
    }

    public void setVerde(int verde) {
        int oldValue = this.verde;
        this.verde = verde;
        colorActual = new Color(rojo, verde, azul);
        propertySupport.firePropertyChange("verde", oldValue, verde);
    }

    public int getAzul() {
        return azul;
    }

    public void setAzul(int azul) {
        int oldValue = this.azul;
        this.azul = azul;
        colorActual = new Color(rojo, verde, azul);
        propertySupport.firePropertyChange("azul", oldValue, azul);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.removePropertyChangeListener(listener);
    }

}
