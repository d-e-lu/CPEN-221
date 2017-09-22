grammar Query;

or : add('||'add)*;
add : atom('&&'atom)*;
atom : (in | category | price | name | rating| '(' or ')');

in : 'in(' STRINGS ')';
category : 'category(' STRINGS ')';
price : 'price(' numbers ')'; // maybe take away singleprice and just use INT
rating : 'rating(' numbers ')';
name : 'name(' STRINGS ')';

numbers: INT ('..' INT)?;

INT : ('0'..'5');
STRINGS : STRING(' 'STRING)*;
STRING : ('a'..'z'|'0'..'9'|'A'..'Z')+;
WS : [ \r\n\t]+ -> skip;