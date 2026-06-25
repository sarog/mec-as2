//$Header: /as4/de/mendelson/util/clientserver/SerializationDummy.java 1     11/06/25 12:12 Heller $
package de.mendelson.util.clientserver;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software. Other product
 * and brand names are trademarks of their respective owners.
 */
/**
 * Annotation for the serialization dummy functions and methods. Marks
 * constructors or methods that exist solely to support Jackson
 * serialization/deserialization.
 *
 * Default usage: - Constructors: reason = "This is a dummy constructor for JSON
 * serialization only - do not use in production." - Methods: reason = "This is
 * a dummy method for JSON serialization only - do not use in production."
 *
 * @author S.Heller
 * @version $Revision: 1 $
 */
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.CONSTRUCTOR, ElementType.METHOD})
public @interface SerializationDummy {

    String reason() default "";
}
