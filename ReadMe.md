# Fonctionnement du Paint

Cette application fonctionne comme un paint normal. Vous pouvez librement dessiner des formes dans des couleurs et des tailles multiples.

En mode dessin libre vous pouvez rester appuyer sur clic gauche est dessiner librement. Dans les autres mode de dessin vous devez restez appuyer afin de définir : Au clic enfoncé le coin supérieur gauche de la forme et au relachement du click la cooin inférieur droit. La prévisualisation vous aide dans tous les cas a voir la taille de la forme.

En appuyant sur `CTRL + clic gauche` ou `clic droit` vous pouvez supprimez une forme ou un trait libre. Cette fonctionnalité étant parfois sensible pour la détection des formes n'hésitez pas à appuyer plusieurs fois à l'intérieur ou sur le bord de votre forme.

Une fois que vous êtes fier de votre oeuvre pensez à l'exporter ou à l'enregistrez pour la reprendre plus tard !

# Menu

Chaque icone du menu à sa fonctionnalité :

- Dessin libre
- Dessin de ligne
- Dessin de rectangle
- Dessin de triangle
- Dessin de cercle
- Gomme
- Choix de la couleur du trait
- Réinitialisation du canva en cours.
- Sauvegarder le canva en cours
- Ouvrir un canva précédemment sauvegarder
- Bouton d'export du canva en cours en mode png

# Bonus réalisés :

- Sauvegarde / Chargement de projet
- Gomme
- Export du canva en un fichier png
- Prévisualisation lors du dessin d'une forme
- Changement d'icone en mode "gomme" ou "dessin libre"

# Commande ant :

- Pour (juste) compiler : `ant compile`

- Pour lancer le projet : `ant run`

- Pour générer la javadoc : `ant javadoc`

- Pour nettoyer les dossiers `build` et `doc` : `ant clean`

## Javadoc

Pour générer la javadoc, il faut utiliser la commande `ant javadoc`. Cette
commande génère la javadoc dans le dossier `doc`. Il suffit après de lancer le fichier `doc/index.html` pour naviguer dans la documentation du projet.

## Lancement

À l'aide de la commande `ant run` ou simplement `ant` l'application se lance.

# Si ant ne fonctionne pas/ n'est pas installé :

```
javac -d build src/paint/**/*.java
java -cp build paint.View.Fenetre
```
