 //$Header: /as4/de/mendelson/util/IOFileFilterRegexpMatch.java 7     9/03/26 10:55 Heller $
package de.mendelson.util;

import java.nio.file.DirectoryStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */

/**
 * File filter that stores lists of wildcard pattern to match file lists. There
 * are positive and negative patterns possible (matching/nonmatching).
 *
 * @author S.Heller
 * @version $Revision: 7 $
 */
public class IOFileFilterRegexpMatch implements DirectoryStream.Filter {

    /**
     * List of matching conditions, the file will go through if it matches any
     * of these conditions
     */
    private final List<Pattern> matchingList = new ArrayList<Pattern>();
    /**
     * List of nonmatching conditions, the file will not go through if it
     * matches any of these conditions
     */
    private final List<Pattern> nonmatchingList = new ArrayList<Pattern>();

    /**
     * Creates a new instance of FileMatcher
     */
    public IOFileFilterRegexpMatch() {
    }

    /**
     * Adds a pattern the file will NOT go through if it matches. The pattern
     * could be any wildcard like "*.tmp", "jetty*.*", "*66.txt", "??xx.txt"
     */
    public void addNonMatchingPattern(String pattern) {
        this.addPattern(this.nonmatchingList, pattern);
    }

    /**
     * Adds a pattern the file will go through if it matches. The pattern could
     * be any wildcard like "*.tmp", "jetty*.*", "*66.txt", "??xx.txt"
     */
    public void addMatchingPattern(String pattern) {
        this.addPattern(this.matchingList, pattern);
    }

    /**
     * Adds a passed pattern to a passed list
     */
    private void addPattern(List<Pattern> patternList, String pattern) {
        pattern = pattern.replace(".", "\\.");
        pattern = pattern.replace("*", ".*");
        pattern = pattern.replace("?", ".");
        Pattern compiledPattern = Pattern.compile(pattern);
        patternList.add(compiledPattern);
    }

    /**
     * Returns if this file filer accepts the passed file
     */
    @Override
    public boolean accept(Object entry) {
        if( entry == null || !(entry instanceof Path) ){
            return( false );
        }
        Path path = (Path) entry;
        String filename = path.getFileName().toString();
        boolean accepted = this.matchingList.isEmpty();
        //check for matching patterns
        for (int i = 0; i < this.matchingList.size(); i++) {
            Matcher matcher = this.matchingList.get(i).matcher(filename);
            accepted = accepted | matcher.matches();
        }
        //check for nonmatching patterns
        for (int i = 0; i < this.nonmatchingList.size(); i++) {
            Matcher matcher = this.nonmatchingList.get(i).matcher(filename);
            if (matcher.matches()) {
                return (false);
            }
        }
        return (accepted);
    }

}
