/**
 * Classe représentant un panneau de dessin interactif permettant de créer, dessiner et
 * manipuler différentes formes géométriques. Le panneau gère des événements de souris
 * pour dessiner des formes comme des rectangles, lignes, triangles, ou du dessin à main levée.
 *
 * @author Baptiste Borie
 */
package paint.View;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.*;

import paint.Model.*;

public class PanneauDessin extends JPanel {

    protected int startX, startY, endX, endY;
    protected String forme = "FreeHand"; // Forme par défaut
    protected boolean isDrawing = false;
    protected BufferedImage canvas;
    protected Color color;

    private ArrayList<Forme> formes;
    private Forme currentForme;
    private FreeHandForme freeHandForme; // Instance pour gérer FreeHand

    /**
     * Constructeur du panneau de dessin.
     * Initialise le canvas, les paramètres de dessin, et ajoute les gestionnaires
     * d'événements pour les interactions utilisateur.
     */
    public PanneauDessin() {
        super();
        this.formes = new ArrayList<>();
        this.setBackground(Color.WHITE);

        Dimension tailleMoniteur = Toolkit.getDefaultToolkit().getScreenSize();
        int longueur = tailleMoniteur.width * 2 / 3;
        int hauteur = tailleMoniteur.height * 2 / 3;

        this.canvas = new BufferedImage(longueur, hauteur, BufferedImage.TYPE_INT_ARGB);
        this.color = Color.RED;

        this.addMouseListener(new MousePannelInfo(this));

        // Gérer le dessin en glissant la souris
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                endX = e.getX();
                endY = e.getY();

                if (isDrawing) {
                    if (forme.equals("FreeHand") && freeHandForme != null) {
                        changeCursor("FreeHand");
                        freeHandForme.addPoint(endX, endY); // Ajout de points pour FreeHand
                    } else if (forme.equals("Gomme")) {
                        changeCursor("Gomme");
                        // Dessiner en blanc avec la gomme
                        if (freeHandForme == null) {
                            freeHandForme = new FreeHandForme(endX, endY, Color.WHITE, 8); // Début du dessin de gomme
                        } else {
                            freeHandForme.addPoint(endX, endY); // Ajouter à la forme existante
                        }
                    } else {
                        // Autres formes géométriques comme Rectangle, Ligne, etc.
                        changeCursor("default");
                        switch (forme) {
                            case "Rectangle":
                                currentForme = new RectangleForme(startX, startY, endX, endY, color);
                                break;
                            case "Ligne":
                                currentForme = new LigneForme(startX, startY, endX, endY, color);
                                break;
                            case "Triangle":
                                currentForme = new TriangleForme(startX, startY, endX, endY, color);
                                break;
                            case "Cercle":
                                currentForme = new CircleForme(startX, startY, endX, endY, color);
                                break;
                        }
                    }
                }

                repaint();
            }
        });

        // Supprimer ou commencer une nouvelle forme avec la souris
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // Supprimer une forme avec clic droit ou Ctrl + clic gauche
                if (SwingUtilities.isRightMouseButton(e)
                        || (e.isControlDown() && SwingUtilities.isLeftMouseButton(e))) {
                    Point point = e.getPoint();
                    supprimerForme(point);
                } else {
                    isDrawing = true;
                    startX = e.getX();
                    startY = e.getY();

                    if (forme.equals("FreeHand")) {
                        freeHandForme = new FreeHandForme(startX, startY, color);
                        formes.add(freeHandForme); // Ajouter immédiatement à la liste
                    } else if (forme.equals("Gomme")) {
                        // Si c'est la gomme, créez une nouvelle forme FreeHand en blanc

                        freeHandForme = new FreeHandForme(startX, startY, Color.WHITE, 8);
                        formes.add(freeHandForme); // Ajouter la gomme au dessin
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                isDrawing = false;

                if (!forme.equals("FreeHand") && currentForme != null) {
                    formes.add(currentForme); // Ajout de la forme finale à la liste
                    currentForme = null;
                }

                if (forme.equals("FreeHand")) {
                    freeHandForme = null;
                }

                repaint();
            }
        });

        // Redimensionner le canvas si la fenêtre change de taille
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int newWidth = getWidth();
                int newHeight = getHeight();

                BufferedImage newCanvas = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);

                Graphics2D g2d = newCanvas.createGraphics();
                g2d.drawImage(canvas, 0, 0, null);
                g2d.dispose();

                canvas = newCanvas;
                repaint();
            }
        });
    }

    /**
     * Définit la forme à dessiner.
     *
     * @param forme Le type de forme à dessiner (Rectangle, Ligne, Triangle,
     *              FreeHand).
     */
    public void setForme(String forme) {
        this.forme = forme;
    }

    /**
     * Dessine la forme en cours sur le canvas.
     */
    public void drawOnCanvas() {
        if (forme.equals("FreeHand") || forme.equals("Gomme")) {
            return; // Le mode FreeHand est déjà géré via mouseDragged
        }

        if (currentForme != null) {
            Graphics2D g2d = this.canvas.createGraphics();
            g2d.setColor(currentForme.getColor());

            currentForme.draw(g2d);
            g2d.dispose();

            currentForme = null;
            repaint();
        }
    }

    /**
     * Réinitialise le canvas en le vidant de toutes les formes.
     */
    public void resetCanvas() {
        Graphics2D g2d = this.canvas.createGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, this.canvas.getWidth(), this.canvas.getHeight());
        g2d.dispose();

        this.formes.clear();
        repaint();
    }

    /**
     * Crée une nouvelle forme basée sur les coordonnées de la souris et le type de
     * forme sélectionné.
     */
    public void createForme() {
        switch (forme) {
            case "Rectangle":
                this.currentForme = new RectangleForme(startX, startY, endX, endY, color);
                break;
            case "Ligne":
                this.currentForme = new LigneForme(startX, startY, endX, endY, color);
                break;
            case "Triangle":
                this.currentForme = new TriangleForme(startX, startY, endX, endY, color);
                break;
            case "Cercle":
                this.currentForme = new CircleForme(startX, startY, endX, endY, color);
                break;
            case "FreeHand":
                this.currentForme = new FreeHandForme(startX, startY, color);
                break;
        }
        if (this.currentForme != null) {
            this.formes.add(this.currentForme);
            repaint();
        }
    }

    /**
     * Supprime une forme contenant un point donné.
     *
     * @param point Le point à vérifier pour suppression.
     */
    private void supprimerForme(Point point) {
        for (int i = formes.size() - 1; i >= 0; i--) {
            Forme forme = formes.get(i);
            if (forme.contient(point)) {
                this.formes.remove(i);
                repaint();
                return;
            }
        }
    }

    /**
     * Définit la couleur utilisée pour dessiner les formes.
     *
     * @param color La nouvelle couleur à utiliser.
     */
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (Forme forme : formes) {
            forme.draw(g); // Dessiner toutes les formes existantes
        }

        if (isDrawing && currentForme != null) {
            currentForme.draw(g); // Dessiner la forme actuelle
        }
    }

    public void changeCursor(String cursor) {
        switch (cursor) {
            case "FreeHand":
                setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
                break;
            case "Gomme":
                ImageIcon eraserIcon = new ImageIcon("assets/la-gomme.png");
                Image eraserImage = eraserIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
                Cursor eraserCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                        eraserImage, new Point(0, 0), "eraser");
                setCursor(eraserCursor);
                break;
            default:
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }
}
