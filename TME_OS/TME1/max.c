// Fichier maxv2.c
// Contient le main implémentant la fonction max
// Fait par Sharane K.Murali 3701883 Groupe 6 

#include <stdio.h>
#include <stdlib.h>
#include <math.h>

int main(int argc, char *argv[]){

    int liste[argc];

    if (argc == 1){
        return 0;
    }

    for (int i = 1; i<argc; i++){
        liste[i-1] = atoi(argv[i]);
    }

    int max = liste[0];

    for (int i = 0; i<argc-1; i++){
        if (max<liste[i]){
            max = liste[i]; 
        }
    }

    printf("Maximum de liste : %.1d\n\n", max);

    return 0;
}