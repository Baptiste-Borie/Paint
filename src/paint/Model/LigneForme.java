/**
 * Classe représentant une ligne.
 * Cette classe permet de dessiner une ligne entre deux points 
 * et de vérifier si un point donné est proche de cette ligne.
 *
 * @author Baptiste Borie
 */
package paint.Model;

import java.awt.*;
import java.awt.geom.Line2D;

public class LigneForme extends Forme {

    /**
     * Constructeur de la classe LigneForme.
     * Initialise une ligne avec ses points de départ et de fin, ainsi que sa
     * couleur.
     *
     * @param startX Coordonnée X du point de départ.
     * @param startY Coordonnée Y du point de départ.
     * @param endX   Coordonnée X du point de fin.
     * @param endY   Coordonnée Y du point de fin.
     * @param color  Couleur de la ligne.
     */
    public LigneForme(int startX, int startY, int endX, int endY, Color color) {
        super(startX, startY, endX, endY, color);
    }

    /**
     * Dessine la ligne en utilisant les coordonnées de départ et de fin.
     *
     * @param g Objet Graphics utilisé pour dessiner la ligne.
     */
    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(1));
        g.drawLine(startX, startY, endX, endY);
    }

    /**
     * Vérifie si un point donné est proche de la ligne.
     * Utilise une tolérance de 5 pixels pour déterminer la proximité.
     *
     * @param p Point à vérifier.
     * @return {@code true} si le point est proche de la ligne, sinon {@code false}.
     */
    @Override
    public boolean contient(Point p) {
        double distance = Line2D.ptSegDist(startX, startY, endX, endY, p.getX(), p.getY());
        return distance < 5.0; // Tolérance de 5 pixels
    }
}
