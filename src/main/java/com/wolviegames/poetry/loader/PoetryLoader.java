package com.wolviegames.poetry.loader;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.parser.lexparser.Lexicon;
import edu.stanford.nlp.process.Tokenizer;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreebankLanguagePack;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;

@Component
public class PoetryLoader {

    LexicalizedParser lp;

    TreebankLanguagePack tlp;

    PoetryLoader(){
        String parserModel = "edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz";
        lp = LexicalizedParser.loadModel(parserModel);
        tlp = lp.getOp().langpack();

    }

    Map<String, List<Tree>> loadPoems(List<String> listOfPoems){
        ClassLoader classLoader = getClass().getClassLoader();
        Map<String, List<Tree>> poemTreeMap = new HashMap<String, List<Tree>>();

        for (String poemFileName : listOfPoems) {
            File file = new File(classLoader.getResource(poemFileName).getFile());
            List<String> poemLines = new ArrayList<String>();
            try {
                poemLines = FileUtils.readLines(file, "UTF-8");
            }
            catch (IOException ioe){
                // Do something better here soon, wolvie. geez.
                ioe.printStackTrace();
            }

            List<Tree> treeList = new ArrayList<Tree>();

            List<CoreLabel> rawWords;
            Tree parsedTree;
            List<? extends HasWord> newSentence;
            Tokenizer<? extends HasWord> tokens;

            // Tokenize each line of the poem
            for(String line: poemLines){
                tokens = tlp.getTokenizerFactory().getTokenizer(new StringReader(line));
                newSentence = tokens.tokenize();

                rawWords = Sentence.toCoreLabelList(newSentence);
                parsedTree = lp.apply(rawWords);
                treeList.add(parsedTree);
            }

            poemTreeMap.put(poemFileName, treeList);
        }

        return poemTreeMap;
    }
}
