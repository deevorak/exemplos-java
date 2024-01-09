package br.com.rocha.exemplosjava.model;

import java.util.List;

public record Catalogo(String genero, List<Filme> filmes) {

}
