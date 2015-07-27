package com.wolviegames.poetry.parser

import edu.stanford.nlp.parser.lexparser.LexicalizedParser
import spock.lang.Specification


class ParserSpec extends Specification {

    def "Parser can call the stanford parser library"() {
        setup:
        String parserModel = "edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz";
        LexicalizedParser lp = LexicalizedParser.loadModel(parserModel)

        when:

        then:

    }

}