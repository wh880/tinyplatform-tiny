/** XML parser derived from ANTLR v4 ref guide book example */
parser grammar XMLParser;

options { tokenVocab=XMLLexer; }

document    :   prolog? declare* doctype* misc* element misc*;

prolog      :   XMLDeclOpen attribute* SPECIAL_CLOSE ;

declare     :   SPECIAL_OPEN attribute* SPECIAL_CLOSE ;

doctype     :   DOCTYPE_OPEN attribute* CLOSE ;

content     :   chardata?
                ((element | reference | CDATA | PI | COMMENT) chardata?)* ;

element     :   '<' Name attribute* '>' content '<' '/' Name '>'
            |   '<' Name attribute* '/>'
            ;

reference   :   EntityRef | CharRef ;

attribute   :   Name '=' STRING ; // Our STRING is AttValue in spec

/** ``All text that is not markup constitutes the character data of
 *  the document.''
 */
chardata    :   TEXT | SEA_WS ;

misc        :   COMMENT | PI | SEA_WS ;
