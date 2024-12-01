/**
 * Classe représentant une forme dessinée à main levée.
 * Elle permet de stocker une liste de points correspondant au tracé 
 * et de gérer son rendu graphique.
 *
 * @author Baptiste Borie
 */
package paint.Model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FreeHandForme extends Forme {
    /**
     * Liste des points représentant le tracé de la forme.
     */
    private List<Point> points;

    /**
     * Constructeur de la classe FreeHandForme.
     * Initialise la forme avec un point de départ et une couleur.
     *
     * @param startX Coordonnée X du point de départ.
     * @param startY Coordonnée Y du point de départ.
     * @param color  Couleur de la forme.
     */
    public FreeHandForme(int startX, int startY, Color color) {
        super(startX, startY, startX, startY, color);
        this.points = new ArrayList<>();
        this.points.add(new Point(startX, startY));
    }

    /**
     * Ajoute un nouveau point au tracé de la forme.
     * Met également à jour les coordonnées de fin.
     *
     * @param x Coordonnée X du nouveau point.
     * @param y Coordonnée Y du nouveau point.
     */
    public void addPoint(int x, int y) {
        points.add(new Point(x, y));
        this.endX = x;
        this.endY = y;
    }

    /**
     * Dessine la forme en reliant les points du tracé.
     * Si la liste de points contient moins de deux éléments, le dessin est ignoré.
     *
     * @param g Objet Graphics utilisé pour dessiner la forme.
     */
    @Override
    public void draw(Graphics g) {
        if (points.size() < 2) {
            return;
        }

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(color);

        for (int i = 1; i < points.size(); i++) {
            Point p1 = points.get(i - 1);
            Point p2 = points.get(i);
            g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
        }
    }

    /**
     * Vérifie si un point donné est proche du tracé de la forme.
     * La tolérance pour la sélection est fixée à 5 pixels.
     *
     * @param p Point à vérifier.
     * @return {@code true} si le point est proche du tracé, sinon {@code false}.
     */
    @Override
    public boolean contient(Point p) {
        for (int i = 1; i < points.size(); i++) {
            Point p1 = points.get(i - 1);
            Point p2 = points.get(i);

            // Calcul de la distance d'un point au segment [p1, p2]
            double distance = distanceToSegment(p, p1, p2);
            if (distance <= 5.0) { // Tolérance pour la sélection
                return true;
            }
        }
        return false;
    }

    /**
     * Méthode utilitaire pour calculer la distance entre un point et un segment
     * défini par deux points. Cette méthode utilise la projection orthogonale pour
     * trouver la distance minimale.
     *
     * @param p  Point à analyser.
     * @param p1 Premier point du segment.
     * @param p2 Deuxième point du segment.
     * @return La distance minimale entre le point et le segment.
     */
    private double distanceToSegment(Point p, Point p1, Point p2) {
        double px = p.x, py = p.y;
        double x1 = p1.x, y1 = p1.y, x2 = p2.x, y2 = p2.y;

        double dx = x2 - x1;
        double dy = y2 - y1;
        if (dx == 0 && dy == 0) {

            return p.distance(p1);
        }

        double t = ((px - x1) * dx + (py - y1) * dy) / (dx * dx + dy * dy);
        t = Math.max(0, Math.min(1, t));

        double closestX = x1 + t * dx;
        double closestY = y1 + t * dy;

        return p.distance(closestX, closestY);
    }
}
