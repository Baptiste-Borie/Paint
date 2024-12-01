/**
 * Classe représentant un cercle.
 * Cette classe permet de dessiner un cercle à partir de deux points
 * (point central et point définissant le rayon)
 * et de vérifier si un point donné est contenu dans ce cercle.
 *
 * @author Baptiste Borie
 */
package paint.Model;

import java.awt.*;

public class CircleForme extends Forme {

    /**
     * Constructeur de la classe CircleForme.
     * Initialise un cercle avec ses points de départ et de fin, ainsi que sa
     * couleur.
     *
     * @param startX Coordonnée X du point central.
     * @param startY Coordonnée Y du point central.
     * @param endX   Coordonnée X du point définissant le rayon.
     * @param endY   Coordonnée Y du point définissant le rayon.
     * @param color  Couleur du cercle.
     */
    public CircleForme(int startX, int startY, int endX, int endY, Color color) {
        super(startX, startY, endX, endY, color);
    }

    /**
     * Dessine le cercle en utilisant les coordonnées du centre et un rayon.
     *
     * @param g Objet Graphics utilisé pour dessiner le cercle.
     */
    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        int radius = (int) Math.sqrt(Math.pow(endX - startX, 2) + Math.pow(endY - startY, 2));
        int diameter = radius * 2;
        int x = startX - radius;
        int y = startY - radius;
        g.drawOval(x, y, diameter, diameter);
    }

    /**
     * Vérifie si un point donné est contenu dans le cercle.
     *
     * @param p Point à vérifier.
     * @return {@code true} si le point est contenu dans le cercle, sinon
     *         {@code false}.
     */
    @Override
    public boolean contient(Point p) {
        // Calcul du rayon du cercle
        int radius = (int) Math.sqrt(Math.pow(endX - startX, 2) + Math.pow(endY - startY, 2));

        // Calcul de la distance entre le centre du cercle (startX, startY) et le point
        // donné (p)
        double distance = Math.sqrt(Math.pow(p.x - startX, 2) + Math.pow(p.y - startY, 2));

        // Si la distance est inférieure ou égale au rayon, le point est à l'intérieur
        // ou sur le cercle
        return distance <= radius;
    }

}
