
def listecodon(seq):
    return [seq[(i*3):(i*3)+3] for i in range(len(seq)//3)]

def trouver_positions_orfs(codons):
 
    starts = [i for i, codon in enumerate(codons) if codon in {'ATG', 'GTG', 'TTG'}] #positions de tous les starts dans codons
    stops =  [i for i, codon in enumerate(codons) if codon in {'TAA', 'TAG', 'TGA'}] #positions de tous les stops  dans codons
    
    orf_starts = [] #renvoie la liste de positions de codons start
    orf_stops = []  #renvoie la liste de positions de codons stop, les deux liste ont la meme taille et sont de pair start/stop 
    
    if starts != [] and stops != []: 

        i_start = 1
        i_stop = 0
        seq_codante = True
        orf_starts.append(starts[0])

        while not((seq_codante and i_stop >= len(stops)) or (not seq_codante and i_start >= len(starts))):
        
            if seq_codante: #Nous sommes dans une sequence codante

                while (stops[i_stop] < orf_starts[len(orf_starts)-1]) : #On cherche le stop apres le dernier start ajoute
                    i_stop += 1
                    if i_stop == len(stops) : #Test a l'interieur de la boucle sinon indice out of range dans le test du while
                        del orf_starts[len(orf_starts)-1] #On supprime le dernier start ajoute et fin de la fonction
                        return orf_starts, orf_stops
                
                orf_stops.append(stops[i_stop]) #On ajoute le stop a la liste et on sort de la sequence codante
                i_stop+=1
                seq_codante = False
            
            else :
                while (starts[i_start] < orf_stops[len(orf_stops)-1]) :
                    i_start += 1
                    if i_start == len(starts) :
                        return orf_starts, orf_stops

                orf_starts.append(starts[i_start]) #On ajoute le start a la liste et on entre dans une sequence codante
                i_start+=1
                seq_codante = True
    if (len(orf_starts) > len(orf_stops))  :
        del orf_starts[len(orf_starts)-1] #On supprime le dernier start ajoute et fin de la fonction  
    return orf_starts, orf_stops

def liste_orfs_sens(seq):

    orfs = []
    
    phaseLect = [listecodon(seq), listecodon(seq[1:len(seq)]), listecodon(seq[2:len(seq)])]
    listORFSS = [trouver_positions_orfs(k) for k in phaseLect]
    
    
    
    for phase, (orf_starts, orf_stops) in enumerate(listORFSS) : 
        for i in range(len(orf_starts)) :
            s = ''
            
            for j in range(orf_starts[i], orf_stops[i]+1):
                for nuc in phaseLect[phase][j]:
                    s+=nuc

            orfs.append(s)

    return orfs

def reversecompl(seq):
  
    compl = {'A': 'T', 'C': 'G', 'G': 'C', 'T':'A'}
    nvSeq = ""
    for i in range(len(seq)-1,-1,-1) :
        nvSeq = nvSeq + compl[seq[i]]
    
    return nvSeq

def liste_orfs(seq):
    orfs = []
    for orf in liste_orfs_sens(seq):
        orfs.append(orf)
    for orf in liste_orfs_sens(reversecompl(seq)):
        orfs.append(orf)

    return orfs


TESTSEQCLEAN = "ATGAAACGCATTAGCACCACCATTACCACCACCATCACCATTACCACAGGTAACGGTGCGGGCTGA"

CODEGENETIQUE = {
    "TTT": "F", "TTC": "F","TTA": "L","TTG": "L","TCT": "S","TCC": "S","TCA": "S","TCG": "S","TAT": "Y","TAC": "Y",
    "TAA": "*","TAG": "*","TGT": "C","TGC": "C","TGA": "*","TGG": "W","CTT": "L","CTC": "L","CTA": "L","CTG": "L",
    "CCT": "P","CCC": "P","CCA": "P","CCG": "P","CAT": "H","CAC": "H","CAA": "Q","CAG": "Q","CGT": "R","CGC": "R",
    "CGA": "R","CGG": "R","ATT": "I","ATC": "I","ATA": "I","ATG": "M","ACT": "T","ACC": "T","ACA": "T","ACG": "T",
    "AAT": "N","AAC": "N","AAA": "K","AAG": "K","AGT": "S","AGC": "S","AGA": "R","AGG": "R","GTT": "V","GTC": "V",
    "GTA": "V","GTG": "V","GCT": "A","GCC": "A","GCA": "A","GCG": "A","GAT": "D","GAC": "D","GAA": "E","GAG": "E",
    "GGT": "G","GGC": "G","GGA": "G","GGG": "G"
}

def traduit_prot(seq, codegenetique=CODEGENETIQUE):
    return [codegenetique[k] for k in listecodon(seq)]

def read_fasta(filename):
    seqs = {}
    with open(filename, 'r') as file:
        seqid = ''
        seq = ''
        for line in file:
            if line.startswith('>'):
                if seqid != '':
                    seqs[seqid] = seq
                else:
                    seq = ''
                seqid = line.replace('>', '').strip()
            else:
                seq = seq + line.strip()
        seqs[seqid] = seq

    return seqs

genome = read_fasta('GCF_000026345.1_ASM2634v1_genomic.fna')['NC_011750.1 Escherichia coli IAI39, complete sequence']
orfs = liste_orfs_sens(genome[:1000])
codons = listecodon(genome[:1000])
codons2 = listecodon(genome[1:1000])
codons3 = listecodon(genome[2:1000])

starts = [i for i, codon in enumerate(codons) if codon in {'ATG', 'GTG', 'TTG'}] #positions de tous les starts dans codons
stops =  [i for i, codon in enumerate(codons) if codon in {'TAA', 'TAG', 'TGA'}] #positions de tous les stops  dans codons
print(starts)
print(stops)

print(trouver_positions_orfs(codons))

print("\n")
starts = [i for i, codon in enumerate(codons2) if codon in {'ATG', 'GTG', 'TTG'}] #positions de tous les starts dans codons
stops =  [i for i, codon in enumerate(codons2) if codon in {'TAA', 'TAG', 'TGA'}] #positions de tous les stops  dans codons
#print(starts)
#print(stops)
print(trouver_positions_orfs(codons2))

print("\n")
starts = [i for i, codon in enumerate(codons3) if codon in {'ATG', 'GTG', 'TTG'}] #positions de tous les starts dans codons
stops =  [i for i, codon in enumerate(codons3) if codon in {'TAA', 'TAG', 'TGA'}] #positions de tous les stops  dans codons
#print(starts)
#print(stops)
print(trouver_positions_orfs(codons3))

print(len(orfs))


for i in range(len(orfs)) :
    print(i)
    print(orfs[i])
    print(len(orfs[i]))
    print("\n")




