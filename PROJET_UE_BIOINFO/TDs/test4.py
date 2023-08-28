import re

motifs = ['ATG', 'TGT', 'CCC', 'GGA']
sequence = 'AATGTCGGACGGA'

def chercheMotif(sequence, motifs):
	dico = {}
	for motif in motifs :
		dico[motif] = [match.start() for match in re.finditer(motif, sequence)]
	for cle in dico.keys():
		if not dico[cle] : 
			dico[cle].append(-1)

	return dico

print(chercheMotif(sequence, motifs))


nucIndex = {'A':0, 'C':1, 'G':2, 'T':3 }

matrix = [[0, 0, 1, 0], [0, 0, 0, 1], [1, 0, 0, 0], [0, 1, 0, 0]]

def calculeScore(seq1, seq2):
	score = 0
	for i in range(len(seq1)) :
		score += matrix[nucIndex[seq1[i]]][nucIndex[seq2[i]]]
	return score


def hamming(u, v) :
	distance = 0
	for i in range(len(u)) : 
		if u[i] != v[i] : 
			distance += 1
	return distance

print(calculeScore('AAC', 'AAG'))
print(calculeScore('AACT', 'AAGC'))
print(calculeScore('ACGT', 'GTAC'))