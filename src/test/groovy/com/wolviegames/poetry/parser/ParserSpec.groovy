package com.wolviegames.poetry.parser

import edu.stanford.nlp.ling.CoreLabel
import edu.stanford.nlp.ling.HasWord
import edu.stanford.nlp.ling.Sentence
import edu.stanford.nlp.parser.lexparser.LexicalizedParser
import edu.stanford.nlp.parser.lexparser.Lexicon
import edu.stanford.nlp.process.Tokenizer
import edu.stanford.nlp.trees.Tree
import edu.stanford.nlp.trees.TreebankLanguagePack
import spock.lang.Specification


class ParserSpec extends Specification {

    def "Parser can call the stanford parser library"() {
        setup:
        String parserModel = "edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz";
        LexicalizedParser lp = LexicalizedParser.loadModel(parserModel)
        Lexicon lexicon = lp.getLexicon()
        TreebankLanguagePack tlp = lp.getOp().langpack();

        String originalString = ("This is a test string.");

        when:
        Tokenizer<? extends HasWord> tokens =
                tlp.getTokenizerFactory().getTokenizer(new StringReader(originalString));
        List<? extends HasWord> newSentence = tokens.tokenize();

        List<CoreLabel> rawWords = Sentence.toCoreLabelList(newSentence);
        Tree parse = lp.apply(rawWords);

        then:
        lexicon instanceof Lexicon
        println(newSentence)
        parse.pennPrint();
    }

}