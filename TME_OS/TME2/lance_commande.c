// Fichier lance_commande.c qui contient les fonctions lance_commande et lance_commande_deux
// Fait par Sharane K.Murali 3701883 Groupe 6

#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <sys/time.h>
#include <sys/times.h>
#include <time.h>



void lance_commande(char *commande){
    
    struct timeval start;
    struct timeval stop;
    
    gettimeofday(&start,NULL);

    int val = system(commande);
    
    if (val == -1){
        printf("Erreur");
    }

    gettimeofday(&stop,NULL);

    printf("Temps écoulé : %.5f sec \n", ((&stop)->tv_sec - (&start)->tv_sec + 0.000001*((&stop)->tv_usec - (&start)->tv_usec)));
 
}

void lance_commande_deux(char *commande){
    
    struct timeval start;
    struct timeval stop;
    struct tms stat;

    gettimeofday(&start,NULL);

    int val = system(commande);
    
    if (val == -1){
        printf("Erreur");
    }

    gettimeofday(&stop,NULL);
    times(&stat);


    printf("Statistiques de \"%s\" :\n", commande);
    printf("Temps total : %.6f  \n", ((&stop)->tv_sec - (&start)->tv_sec + 0.000001*((&stop)->tv_usec - (&start)->tv_usec)));
    printf("Temps utilisateur : %.6f\n",(float) (&stat)->tms_utime);
    printf("Temps système : %.6f\n", (float)(&stat)->tms_stime);
    printf("Temps utilisateur fils : %.6f\n", (float)(&stat)->tms_cutime);
    printf("Temps système fils : %.6f\n\n", (float)(&stat)->tms_cstime);
}

int main(int argc, char *argv[]){
    
    //for (int i = 1; i<argc; i++){
    //    lance_commande(argv[i]);
    //}

    for (int i = 1; i<argc; i++){
        lance_commande_deux(argv[i]);
    }

    return 0;

}