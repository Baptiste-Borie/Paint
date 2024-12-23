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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

import paint.Model.*;

public class PanneauDessin extends JPanel {

    /**
     * Coordonnées de début et de fin pour dessiner une forme.
     */
    protected int startX, startY, endX, endY;

    /**
     * Type de forme actuellement sélectionné. Par défaut : FreeHand.
     */
    protected String forme = "FreeHand";

    /**
     * Indique si l'utilisateur est en train de dessiner.
     */
    protected boolean isDrawing = false;

    /**
     * Image de fond du panneau de dessin.
     */
    protected BufferedImage canvas;

    /**
     * Couleur actuelle utilisée pour dessiner les formes.
     */
    protected Color color;

    /**
     * Liste des formes de dessin.
     */
    private ArrayList<Forme> formes;

    /**
     * Forme active de dessin
     */
    private Forme currentForme;

    /**
     * Instance pour gérer FreeHand
     */
    private FreeHandForme freeHandForme;

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
     * Retourne le type de forme actuellement sélectionné.
     * 
     * @return Le type de forme actuellement sélectionné.
     */
    public String getForme() {
        return forme;
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

    /**
     * Redessine les composants graphiques sur le panneau.
     *
     * @param g l'objet Graphics utilisé pour dessiner sur le composant.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (Forme forme : formes) {
            forme.draw(g);
        }

        if (isDrawing && currentForme != null) {
            currentForme.draw(g);
        }
    }

    /**
     * Change le curseur du panneau en fonction de l'outil sélectionné.
     *
     * @param cursor une chaîne représentant le type d'outil, par exemple
     *               "FreeHand" pour un curseur en croix ou "Gomme" pour une icône
     *               de gomme.
     */
    public void changeCursor(String cursor) {
        switch (cursor) {
            case "FreeHand":
                setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
                break;
            case "Gomme":
                ImageIcon eraserIcon = new ImageIcon("assets/eraser-solid-24.png");
                Cursor eraserCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                        eraserIcon.getImage(), new Point(0, 0), "eraser");
                setCursor(eraserCursor);
                break;
            default:
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }

    /**
     * Sauvegarde le projet actuel dans un fichier.
     *
     * @param file le fichier où les données du projet doivent être sauvegardées.
     */
    public void sauvegarderProjet(File file) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(formes); // Sérialisation de la liste des formes
            oos.writeObject(color); // Sauvegarde de la couleur actuelle
            oos.writeInt(canvas.getWidth()); // Sauvegarde de la largeur du canvas
            oos.writeInt(canvas.getHeight()); // Sauvegarde de la hauteur du canvas
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Charge un projet sauvegardé à partir d'un fichier.
     *
     * @param file le fichier contenant les données du projet à charger.
     */
    public void chargerProjet(File file) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {

            formes = (ArrayList<Forme>) ois.readObject(); // Récupérer la liste des formes
            color = (Color) ois.readObject(); // Récupérer la couleur sauvegardée

            // Récupérer les dimensions du canvas
            int width = ois.readInt();
            int height = ois.readInt();

            // Réinitialisation du canvas
            canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            repaint();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Exporte le contenu actuel du canvas en tant qu'image PNG.
     *
     * @param file Le fichier où l'image sera exportée.
     */
    public void exporterImage(File file) {
        try {
            // Crée une image contenant le rendu actuel du panneau de dessin
            BufferedImage exportImage = new BufferedImage(this.getWidth(), this.getHeight(),
                    BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = exportImage.createGraphics();

            // Dessine le panneau sur l'image
            this.paint(g2d);
            g2d.dispose();

            // Vérifie si l'extension est déjà présente, sinon ajoute ".png"
            if (!file.getName().toLowerCase().endsWith(".png")) {
                file = new File(file.getAbsolutePath() + ".png");
            }

            // Écrit l'image dans le fichier
            ImageIO.write(exportImage, "png", file);

            JOptionPane.showMessageDialog(this, "Image exportée avec succès : " + file.getAbsolutePath(),
                    "Export Réussi", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Erreur lors de l'exportation de l'image : " + ex.getMessage(),
                    "Erreur d'Export", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

}
