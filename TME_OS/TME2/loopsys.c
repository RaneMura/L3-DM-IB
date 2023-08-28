// Fichier loopsys.c qui fait 5*10e7 itérations avec un appel système 
// Fait par Sharane K.Murali 3701883 Groupe 6

#include <stdio.h>
#include <stdlib.h>
#include <sys/time.h>
#include <sys/types.h>
#include <unistd.h>


int main(){

    for (long i=0; i<50000000; i++){
        getpid();
    }

    return 0;
}

