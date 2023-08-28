CC = gcc
CFLAGS = -Wall
LDFLAGS =
EXEC= mgrep mgrep2 zombie

all: $(EXEC)

mgrep : mgrep.o
	$(CC) -o $@ $^ $(LDFLAGS)

mgrep.o : mgrep.c
	$(CC) -o $@ -c $< $(CFLAGS)

mgrep2 : mgrep2.o
	$(CC) -o $@ $^ $(LDFLAGS)

mgrep2.o : mgrep.c
	$(CC) -o $@ -c $< $(CFLAGS)
	
zombie : zombie.o
	$(CC) -o $@ $^ $(LDFLAGS)
	
zombie.o : zombie.c
	$(CC) -o $@ -c $< $(CFLAGS)

clean:
	rm -rf *.o

rmproper: clean
	rm -rf $(EXEC)