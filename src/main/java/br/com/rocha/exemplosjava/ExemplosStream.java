package br.com.rocha.exemplosjava;

import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import br.com.rocha.exemplosjava.model.Catalogo;
import br.com.rocha.exemplosjava.model.Filme;

public class ExemplosStream {

    public void run() {
	List<Filme> filmes = List.of(new Filme("Um Tira da Pesada", "Comédia", 9.0),
		new Filme("Goonies", "Comédia", 9.6), new Filme("De Volta para o Futuro", "Aventura", 9.8),
		new Filme("O Poderoso Chefão", "Drama", 9.1),
		new Filme("Um Tira no Jardim de Infância", "Comédia", 8.0), new Filme("Tron", "Ficção", 7.9),
		new Filme("Matrix", "Ficção", 8.5));

	System.out.println("** Filmes ordenados por nome:");
	// ordena por nome e exibe
	filmes.stream().sorted(Comparator.comparing(Filme::nome)).forEach(System.out::println);

	System.out.println("** TOP 3:");
	// exibe os 3 filmes mais bem avaliados
	filmes.stream().sorted(Comparator.comparing(Filme::nota).reversed()).limit(3).forEach(System.out::println);

	System.out.println("** Primeiros 5 caracteres nome:");
	filmes.stream().map(f -> f.nome().length() > 4 ? f.nome().substring(0, 5) : f.nome())
		.forEach(System.out::println);

	System.out.println("** Criando Catálogo de filmes por genero, ordenando da nota mais alta para mais baixa:");
	List<Catalogo> catalogos =  filmes.stream().map(f -> f.genero()).distinct()
		.map(g -> new Catalogo(g,
			filmes.stream().filter(fm -> fm.genero().equals(g))
				.sorted(Comparator.comparing(Filme::nota).reversed()).collect(Collectors.toList())))
				.collect(Collectors.toList());
		
	catalogos.forEach(System.out::println);
	
	// Média de nota de dos filmes por genero
	Map<String, Double> mediaNotaFilmePorGenero = catalogos.stream()
		.flatMap(c -> c.filmes().stream())
			.collect(
				Collectors.groupingBy(Filme::genero, Collectors.averagingDouble(Filme::nota))
			);
	
	System.out.println(mediaNotaFilmePorGenero);
	
	// DoubleSummaryStatistics
	DoubleSummaryStatistics estatisticasFilmes = filmes.stream().collect(Collectors.summarizingDouble(Filme::nota));
	System.out.println("Maior nota = " + estatisticasFilmes.getMax());
	System.out.println("Menor nota = " + estatisticasFilmes.getMin());
	System.out.println("Média notas = " + estatisticasFilmes.getAverage());
	System.out.println("Total notas computadas = " + estatisticasFilmes.getCount());

    }

}
