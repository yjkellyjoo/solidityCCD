package org.yjkellyjoo.parser;

public class Token {
    public static Token VOID = new Token(new Object());

    final Object token;

    public Token(Object token) { this.token = token; }


}
