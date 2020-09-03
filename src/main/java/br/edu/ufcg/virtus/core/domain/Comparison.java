package br.edu.ufcg.virtus.core.domain;

/**
 * Domain for Comparison operations.
 * 
 * @author Virtus
 *
 */
public enum Comparison {
    // equal
    EQ,
    // multiple values in a WHERE clause
    IN,
    // contains
    LIKE
}
