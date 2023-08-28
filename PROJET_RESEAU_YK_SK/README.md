# Projet Reseau 

Projet UE LU3IN033 : Réseaux réalisé par Yannis KEDADRY et Sharane K.MURALI (GROUPE 3)

## Introduction

Notre application s'appelle WireLite. Il s'agit d'un analyseur offline qui prend un ficher texte contenant des trames et renvoie un fichier texte contentant l'analyse complète des trames.

## Prise en charge
Le code conçu prend en charge les protocoles suivants : Ethernet, IPv4, UDP, DHCP et DNS.
Les protocoles autres que cités ci-dessus ne sont pas développés.

## Code
 
Le code est implémenté en Java, la raison est que la programmation objet nous permet de structurer en différentes classes les protocoles ainsi que les programme d'extraction des trames.

## Structure du code 

### Classe WireLite

Classe principale qui représente le "Wireshark Lite" et s'occupe de la gestion des fichiers entrée/sortie.

### Les classes d'extraction de trame

Les classes suivantes s'occupent du traitement du fichier texte en paramètre pour en extraire des trames adaptés à notre code.

#### Classe InputFileManager

Classe permettant de faire des opérations sur les fichiers passées en argument.

#### Interface ITrame

Interface d'une trame.

#### Interface ITrameBuilder

Interface permettant de construire une trame selon le Design Pattern Builder.

#### Classe StringUtility

Classe abstraite qui transforme une chaine de caractères représentant un héxadécimal en chaine de caractères représentant un binaire.

#### Classe Trame

Classe qui représente une trame.

#### Classe TrameBuilder

Classe qui utilise le Design Pattern Builder pour diviser la construction d'une trame en construisant un a un chaque entête et les compléter.

#### Classe TrameDirector

Classe qui initialise les TramesBuilder.

### Les exceptions

Les exceptions sont ramaenés au main, WireLite, qui s'occupe de la gestion.

#### Classe ErrorValueException

Classe qui lève une exception si une valeur incohérente est entrée dans un champ d'une entête de protocole.

#### Classe TrameTooShortException

Classe qui lève une exception face à une trame courte.

#### Classe UnsupportedProtocolException

Classe qui lève une exception si une valeur représentant un protocole non supporté est passéee dans un champ d'entête.

### Les protocoles

Les protocoles sont codés dans des classes qui héritent de la classe générique abstraite Header qui fournit une structure générique d'entête avec des champs conçus par la classe Field.

#### Classe Header

Classe qui s'occupe de la gestion des entêtes et comportant une liste de Field. 

#### Classe Field

Classe qui s'occupe de la gestion d'un champ d'entête.

#### Classe Ethernet

Classe qui construit une entête Ethernet à partie de la trame.
Elle prend en valeur les 14 premiers octets pour en extraire les différents champs.

Parmi les types de protocole prises en charge, seule IPv4 est concerné. Il détectera les protocoles IPv6 et ARP mais l'exception UnsupportedProtocolException sera levée ainsi que pour ceux qui ne sont pas connus.

#### Classe IP

Classe qui construit une entête IPv4 à partie de la trame.
Après le traitement par la classe Ethernet, la classe IP prend le relais.

La version IP est verifiée avec le champ Version et le protocole contenu également, auquel cas les exceptions seront levées.

Parmi les types de protocole pris en charge, seul UDP est concerné. Il détectera les protocoles ICMP, IGMP, TCP,EGP, IDP, XTP, RSVP mais l'exception UnsupportedProtocolException sera levée ainsi que pour ceux qui ne sont pas connus.

Les options pris en charge par le code pour ce protocole sont les suivants :
- End of Options List(EOOL)
- No Operation (NOP)
- Record Route
- Time Stamp

#### Classe UDP

Classe qui construit une entête UDP à partie de la trame.
Après le traitement par la classe IP, la classe UDP prend le relais.

En foction des valeurs des ports, le code identifie le protocole utilisé par le port source ou destination et en fonction de cela, le protocole en question sera utilisé à l'étape suivante.

Parmi les types de protocole pris en charge, DHCP et DNS sont concernés. Il détectera le protocole NAT mais l'exception UnsupportedProtocolException sera levée ainsi que pour ceux qui ne sont pas connus.

#### Classe DHCP

Classe qui construit un message DHCP à partie de la trame.
Après le traitement par la classe UDP, si le protocole encapsulé est DHCP, la classe DHCP prend le relais.

Cette classe identifie le type de message DHCP encapsulé et décrit les diférents champs du protocole. 

Parmi les options prises en charge par DHCP, 78 options (0-76 et 255) sont codés et pris en charges. Les options 78 à 255 sont identifiées mais non traitées. 

#### Classe DNS

Classe qui construit un message DNS à partie de la trame.
Après le traitement par la classe UDP, si le protocole encapsulé est DNS, la classe DNS prend le relais.

Cette classe identifie le type de message DNS encapsulé et décrit les différents champs du protocole. 

## Références/Sources

<br>RFC2132 : http://rfc-editor.org/rfc/rfc2132.html
<br>DHCP Options : https://www.iana.org/assignments/bootp-dhcp-parameters/bootp-dhcp-parameters.xhtml
<br>Cours Medium de Prométhée Spathis : https://spathis.medium.com/comprendre-internet-et-son-fonctionnement-9b2f63a07430
