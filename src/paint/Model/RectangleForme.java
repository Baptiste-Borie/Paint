/**
 * Classe représentant un rectangle.
 * Cette classe permet de dessiner un rectangle à partir de deux points (coin supérieur gauche et coin inférieur droit)
 * et de vérifier si un point donné est contenu dans ce rectangle.
 *
 * @author Baptiste Borie
 */
package paint.Model;

import java.awt.*;

public class RectangleForme extends Forme {

    /**
     * Constructeur de la classe RectangleForme.
     * Initialise un rectangle avec ses points de départ et de fin, ainsi que sa
     * couleur.
     *
     * @param startX Coordonnée X du point de départ.
     * @param startY Coordonnée Y du point de départ.
     * @param endX   Coordonnée X du point de fin.
     * @param endY   Coordonnée Y du point de fin.
     * @param color  Couleur du rectangle.
     */
    public RectangleForme(int startX, int startY, int endX, int endY, Color color) {
        super(startX, startY, endX, endY, color);
    }

    /**
     * Dessine le rectangle en utilisant les coordonnées de départ et de fin.
     *
     * @param g Objet Graphics utilisé pour dessiner le rectangle.
     */
    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(1));
        int width = Math.abs(endX - startX);
        int height = Math.abs(endY - startY);
        int x = Math.min(startX, endX);
        int y = Math.min(startY, endY);
        g.drawRect(x, y, width, height);
    }

    /**
     * Vérifie si un point donné est contenu dans le rectangle.
     *
     * @param p Point à vérifier.
     * @return {@code true} si le point est contenu dans le rectangle, sinon
     *         {@code false}.
     */
    @Override
    public boolean contient(Point p) {
        Rectangle bounds = new Rectangle(Math.min(startX, endX), Math.min(startY, endY),
                Math.abs(endX - startX), Math.abs(endY - startY));
        return bounds.contains(p);
    }
}
