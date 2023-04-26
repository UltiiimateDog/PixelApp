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
import javax.swing.JOptionPane;
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
        setSize(1200, 800);
        setLocationRelativeTo(null);
        
        colores = new Colores();
        
        lienzo = new Lienzo(colores, 30);
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
        JLabel rojoLabel = new JLabel("Rojo: " + colores.getRojo());
        JLabel rojoValor = new JLabel(Integer.toString(colores.getRojo()));
        rojoSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                colores.setRojo(rojoSlider.getValue());
                lienzo.setColores(colores);
                rojoValor.setText(Integer.toString(rojoSlider.getValue()));
            }
        });
                
        verdeSlider = new JSlider(0, 255, colores.getVerde());
        JLabel verdeLabel = new JLabel("Verde: " + colores.getVerde());
        JLabel verdeValor = new JLabel(Integer.toString(colores.getVerde()));
        verdeSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                colores.setVerde(verdeSlider.getValue());
                lienzo.setColores(colores);
                verdeValor.setText(Integer.toString(verdeSlider.getValue()));
            }
        });
                
        azulSlider = new JSlider(0, 255, colores.getAzul());
        JLabel azulLabel = new JLabel("Azul: " + colores.getAzul());
        JLabel azulValor = new JLabel(Integer.toString(colores.getAzul()));
        azulSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                colores.setAzul(azulSlider.getValue());
                lienzo.setColores(colores);
                azulValor.setText(Integer.toString(azulSlider.getValue()));
            }
        });
        
        JButton limpiarBoton = new JButton("Limpiar");
        limpiarBoton.addActionListener(e -> lienzo.limpiar());
        
        JButton guardarBoton = new JButton("Guardar");
        guardarBoton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser(new File(System.getProperty("user.dir")));
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int returnVal = chooser.showSaveDialog(Interfaz.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                try {
                    BufferedImage imagen = new BufferedImage(lienzo.getWidth(), lienzo.getHeight(),
                            BufferedImage.TYPE_INT_ARGB);
                    lienzo.dibujarSinCuadricula(imagen.getGraphics());
                    File directorio = chooser.getSelectedFile();
                    String[] opciones = {"png", "jpg", "gif"};
                    String extension = (String) JOptionPane.showInputDialog(
                            Interfaz.this,
                            "Selecciona la extensión del archivo:",
                            "Guardar imagen",
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            opciones,
                            "png"
                    );
                    if (extension != null) {
                        String nombreArchivo = JOptionPane.showInputDialog(
                                Interfaz.this,
                                "Ingresa el nombre del archivo:",
                                "Guardar imagen",
                                JOptionPane.PLAIN_MESSAGE
                        );
                        if (nombreArchivo != null && !nombreArchivo.isEmpty()) {
                            File archivo = new File(directorio, nombreArchivo + "." + extension);
                            ImageIO.write(imagen, extension, archivo);
                        }
                    }
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
        
        JPanel slidersPanel = new JPanel(new GridLayout(3, 2));
        slidersPanel.add(rojoLabel);
        slidersPanel.add(rojoValor);
        slidersPanel.add(rojoSlider);
        slidersPanel.add(verdeLabel);
        slidersPanel.add(verdeValor);
        slidersPanel.add(verdeSlider);
        slidersPanel.add(azulLabel);
        slidersPanel.add(azulValor);
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
        lienzo.limpiar();
    }
    
}
        