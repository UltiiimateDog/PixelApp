package PixelApp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Interfaz extends JFrame {
    
    private Lienzo lienzo;
    private Colores colores;
    private JSlider rojoSlider;
    private JSlider verdeSlider;
    private JSlider azulSlider;
    
    public Interfaz() {
        super("Pixel Art Maker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        colores = new Colores();
        
        lienzo = new Lienzo(colores, 20);
        lienzo.setPreferredSize(new Dimension(600, 600));
        lienzo.setBackground(Color.WHITE);
        
        JPanel coloresPanel = new JPanel(new GridLayout(2, 6));
        for (Color color : colores.getColoresPredeterminados()) {
            JButton botonColor = new JButton();
            botonColor.setBackground(color);
            botonColor.addActionListener(e -> {
                colores.setColorActual(color);
                lienzo.setColores(colores);
            });
            coloresPanel.add(botonColor);
        }
        
        rojoSlider = new JSlider(0, 255, colores.getRojo());
        rojoSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                colores.setRojo(rojoSlider.getValue());
                lienzo.setColores(colores);
            }
        });
        
        verdeSlider = new JSlider(0, 255, colores.getVerde());
        verdeSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                colores.setVerde(verdeSlider.getValue());
                lienzo.setColores(colores);
            }
        });
        
        azulSlider = new JSlider(0, 255, colores.getAzul());
        azulSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                colores.setAzul(azulSlider.getValue());
                lienzo.setColores(colores);
            }
        });
        
        JButton limpiarBoton = new JButton("Limpiar");
        limpiarBoton.addActionListener(e -> lienzo.limpiar());
        
        JButton guardarBoton = new JButton("Guardar");
        guardarBoton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "Archivos de imagen", "jpg", "jpeg", "png", "bmp", "gif");
            chooser.setFileFilter(filter);
            int returnVal = chooser.showSaveDialog(Interfaz.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                try {
                    BufferedImage imagen = new BufferedImage(lienzo.getWidth(), lienzo.getHeight(),
                            BufferedImage.TYPE_INT_ARGB);
                    lienzo.paint(imagen.getGraphics());
                    File archivo = chooser.getSelectedFile();
                    ImageIO.write(imagen, "png", archivo);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
			}
        });
        
        JButton cargarBoton = new JButton("Cargar");
        cargarBoton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "Archivos de imagen", "jpg", "jpeg", "png", "bmp", "gif");
            chooser.setFileFilter(filter);
            int returnVal = chooser.showOpenDialog(Interfaz.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                try {
                    File archivo = chooser.getSelectedFile();
                    BufferedImage imagen = ImageIO.read(archivo);
                    Image imagenEscalada = imagen.getScaledInstance(lienzo.getWidth(), lienzo.getHeight(), Image.SCALE_SMOOTH);
                    lienzo.getGraphics().drawImage(imagenEscalada, 0, 0, lienzo.getWidth(), lienzo.getHeight(), null);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        
        JPanel slidersPanel = new JPanel(new GridLayout(3, 1));
        slidersPanel.add(rojoSlider);
        slidersPanel.add(verdeSlider);
        slidersPanel.add(azulSlider);
        
        JPanel opcionesPanel = new JPanel(new GridLayout(1, 3));
        opcionesPanel.add(limpiarBoton);
        opcionesPanel.add(guardarBoton);
        opcionesPanel.add(cargarBoton);
        
        JLabel colorActualLabel = new JLabel("  ");
        colorActualLabel.setBackground(colores.getColorActual());
        colorActualLabel.setOpaque(true);
        colorActualLabel.setHorizontalAlignment(SwingConstants.CENTER);
        colorActualLabel.setPreferredSize(new Dimension(50, 50));
        colores.addPropertyChangeListener(e -> {
            if (e.getPropertyName().equals("colorActual")) {
                colorActualLabel.setBackground(colores.getColorActual());
            }
        });
        
        JPanel herramientasPanel = new JPanel(new BorderLayout());
        herramientasPanel.add(coloresPanel, BorderLayout.NORTH);
        herramientasPanel.add(slidersPanel, BorderLayout.CENTER);
        herramientasPanel.add(opcionesPanel, BorderLayout.SOUTH);
        
        Container contenedor = getContentPane();
        contenedor.add(lienzo, BorderLayout.CENTER);
        contenedor.add(herramientasPanel, BorderLayout.EAST);
        contenedor.add(colorActualLabel, BorderLayout.SOUTH);
        
        setVisible(true);
    }
    
}
        