CC = gcc
CFLAGS = -Wall
LDFLAGS =
EXEC= loopsys loopcpu mytimes

all: $(EXEC)

mytimes : lance_commande.o
	$(CC) -o $@ $^ $(LDFLAGS)

lance_commande.o : lance_commande.c
	$(CC) -o $@ -c $< $(CFLAGS)

loopcpu : loopcpu.o
	$(CC) -o $@ $^ $(LDFLAGS)

loopcpu.o : loopcpu.c
	$(CC) -o $@ -c $< $(CFLAGS)
	
loopsys : loopsys.o
	$(CC) -o $@ $^ $(LDFLAGS)
	
loopsys.o : loopsys.c
	$(CC) -o $@ -c $< $(CFLAGS)

clean:
	rm -rf *.o

rmproper: clean
	rm -rf $(EXEC)
