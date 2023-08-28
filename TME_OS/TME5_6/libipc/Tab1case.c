/* Diffusion tampon 1 case */

  #include <stdio.h> 
  #include <unistd.h> 
  #include <stdlib.h> 
  #include <signal.h> 
  #include <libipc.h>

/************************************************************/

/* definition des parametres */ 

  #define NE          2     /*  Nombre d'emetteurs         */ 
  #define NR          5     /*  Nombre de recepteurs       */ 

/************************************************************/

/* definition des semaphores */

  #define EMET 1            /*  Sémaphore qui bloque les émetteurs tant qu'il n'y a pas de case vide */      
  #define MutexR 1          /*  Mutex qui permet la protection du compteur  */ 
  #define RECEP 0           /*  Tableau de sémaphores qui bloque les récepteurs */

/************************************************************/

/* definition de la memoire partagee */ 

	int nb_recepteurs = 0

/************************************************************/

/* variables globales */ 
    int emet_pid[NE], recep_pid[NR]; 

/************************************************************/

/* traitement de Ctrl-C */ 

  void handle_sigint(int sig) { 
      int i;
      for (i = 0; i < NE; i++) kill(emet_pid[i], SIGKILL); 
      for (i = 0; i < NR; i++) kill(recep_pid[i], SIGKILL); 
      det_sem(); 
      det_shm((char *)sp); 
  } 

/************************************************************/

/* fonction EMETTEUR */ 

	// A completer - contient les instructions executees
        // par un emetteur
  void emetteur(char msg){
      P(EMET);
      T = msg;
      for(int i = 1; i<NR;i++){
          V(RECEP[i])
      }
  }

/************************************************************/

/* fonction RECEPTEUR */ 

	// A completer - contient les instructions executees
        // par un recepteur

  void recepteur(int idr,char *msg){
      P(RECEP[idr]);
      msg = T
      P(MutexR);
      nb_recepteurs++;
      if(nb_recepteurs==NR){
            nb_recepteurs = 0;
            V(EMET);
      }
      V(MutexR);
  }

/************************************************************/

int main() { 
    struct sigaction action;
    /* autres variables (a completer) */
    
    setbuf(stdout, NULL);

/* Creation du segment de memoire partagee */

	// A completer

/* creation des semaphores */ 
  
	// A completer

/* initialisation des semaphores */ 

	// A completer

/* creation des processus emetteurs */ 

	// A completer - les pid des processus crees doivent
        // etre stockes dans le tableau emet_pid

/* creation des processus recepteurs */ 

	// A completer - les pid des processus crees doivent
        // etre stockes dans le tableau recep_pid

/* redefinition du traitement de Ctrl-C pour arreter le programme */ 

    sigemptyset(&action.sa_mask);
    action.sa_flags = 0;
    action.sa_handler = handle_sigint;
    sigaction(SIGINT, &action, 0); 
    
    pause();                    /* attente du Ctrl-C  */
    return EXIT_SUCCESS;
} 
