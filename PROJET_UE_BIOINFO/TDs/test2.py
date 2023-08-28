import string

startCodons = ('ATG')
stopCodons = ('TAA', 'TAG', 'TAG')

def getCodons(sequence) :
	return [sequence[i*3:i*3+3] for i in range(len(sequence)//3)]

def cherchePlusGrandeORF(sequence, startCodons, stopCodons) :
	listeCodons = getCodons(sequence)
	start = None;
	stop = None;
	for i in range(len(listeCodons)) : 
		if (not start) :
 			if listeCodons[i] in startCodons :
				start = i
		if listeCodons[i] in stopCodons :
			stop = i;

	if start == None or stop == None :
		return (None, None)
	
	if start < stop :
		return (start, stop)

	return (None, None)

def ReverseCompl(sequence) : 
	nvSeq = ""

	for i in range(len(sequence)-1, -1, -1):
		if (sequence[i] == 'A'):
			nvSeq += 'T'
		if (sequence[i] == 'T'):
			nvSeq += 'A'
		if (sequence[i] == 'C'):
			nsSeq += 'G'
		if (sequence[i] == 'G'):
			nvSeq += 'C'
	return nvSeq



def afficheORF(sequence, startCodons, stopCodons) :

	frameUn = sequence.upper()
	frameDeux = sequence[1:].upper()
	frameTrois = sequence[2:].upper()

	(start1, stop1) = cherchePlusGrandeORF(frameUn, startCodons, stopCodons)
	(start2, stop2) = cherchePlusGrandeORF(frameDeux, startCodons, stopCodons)
	(start3, stop3) = cherchePlusGrandeORF(frameTrois, startCodons, stopCodons)

	frame = None

	if start1 == None :
		if start2 == None :
			if start3 == None :
				return None
			else :
				frame = 3
		else :
			if (stop3 - start3) < (stop2 - start2) :
				frame = 2
			else :
				frame = 3
	
	else : 
		if start2 == None :
			if start3 == None : 
				frame = 1
			else : 
				if (stop1 - start1) < (stop3 - start3) :
					frame = 3
				else :
					frame = 1
		else :

			if start3 == None :
				if (stop1 - start1) < (stop2 - start2) :
					frame = 2
				else :
					frame = 1

			else :

				if (stop3 - start3) < (stop2 - start2) :
					if (stop2 - start2) < (stop1 - start1) :
						frame = 1
					else : 
						frame = 2
				else :
					if (stop1 - start1) < (stop3 - start3) :
						frame = 3
					else : 
						frame = 1

	orf = ""
	if frame == 1 :
		listeCodons = getCodons(frameUn)
		for i in range(start1, stop1) : 
			for j in listeCodons[i] :
				orf += j

	if frame == 2 :
		listeCodons = getCodons(frameDeux)
		for i in range(start2, stop2) : 
			for j in listeCodons[i] :
				orf += j

	if frame == 2 :
		listeCodons = getCodons(frameTois)
		for i in range(start3, stop3) : 
			for j in listeCodons[i] :
				orf += j
	print orf


	
background = {'A' : 0.26, 'C' : 0.24, 'G' : 0.23, 'T' : 0.27}

def probBack(sequence, background) :
	seq = sequence.upper()
	proba = 1
	for char in seq: 
		proba = proba * background[char]

	print proba


def countCodons(sequence, listeCodons) :
	occurences = [0]*len(listeCodons)
	codonsUn = getCodons(sequence)
	codonsDeux = getCodons(sequence[1:])
	codonsTrois = getCodons(sequence[2:])

	for codon in codonsUn : 
		if codon in listeCodons :
			occurences[listeCodons.index(codon)]+=1
	for codon in codonsDeux : 
		if codon in listeCodons :
			occurences[listeCodons.index(codon)]+=1
	for codon in codonsTrois : 
		if codon in listeCodons :
			occurences[listeCodons.index(codon)]+=1

	print occurences


countCodons('atgtaccgtcgatcgtagcttgatcgatcg', ('atg','ccg','gat'))

background = {'AAC' : 0.0026, 'ACG' : 0.0024, 'CGT' : 0.023, 'CAT' : 0.0027}

def probBack2(sequence, background) : 
	seq = sequence.upper()
	listeCodons = [seq[i:i+3] for i in range(len(sequence)-2)]
	proba = 1
	for codon in listeCodons: 
		proba = proba * background[codon]

	print proba

probBack2('aacgt', background)






