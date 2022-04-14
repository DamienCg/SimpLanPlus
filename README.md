# SimpLanPlus

Semantic errors found               
Variable a already declared         OK
Argument id x already declared      OK
Function g already declared         KO --> C'è una variabile con stesso nome. La funzione non dovrebbe dare errore
Argument id dd already declared     OK

KO --> La doppia variabilie g nell'ultima funzione non da errore
Deccomentando il checksemantic del block in DecFunNode viene segnalata la doppia g.
Al momento è commentato perché la parte del check sugli statament va in errore


Credo vada rivisto un po' il sistema di annidamente delle varia symTable.
Quando si entra dentro una funzione e si fa il check degli id questo dovrebbe andare indietro
fino alla dichiarazione della funzione e non oltre.
