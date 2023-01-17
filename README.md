# ExempleMavenResources

le principe est de prendre un fichier texte (liste_français.txt qui contient un mot par ligne), de le transformer en dictionnaire d'anagramme en enlevant les mots avec des espaces ou des tirêts. 
Il y a 3 classes : 
 * Dico qui maintient la liste et offre le calcul d'anagramme et la normalisation des mots
 * DicoTextToJSON pour charger un fichier texte ou exporter un dictionnaire vers un fichier JSON
 * ListAnagram pour avoir tous les mots possibles dont les lettres sont contenues dans un mot, par exemple : toutes -> Ouest, es, est, et, eu, eus, eut, eût, os, ose, osé, ou, ouest, out, où, sot, sou, soute, su, te, tes, test, tous, tout, toutes, tu, tue, tué, tués, têtu, tôt, us, use, usé

Il y a 2 "main" pour essayer, un dans DicoTextToJSON et un dans ListAnagram

L'exemple sert : 
 * pour ouvrir une ressource
 * pour l'utilisation de jackson pour l'export
 * pour les tests unitaires avec fichiers (et ressources) et aussi pour les mocks

Il n'y a pas d'exec de configurer
pour la javadoc : 
```
mvn javadoc:javadoc@javadoc
mvn javadoc:test-javadoc@test-javadoc
```