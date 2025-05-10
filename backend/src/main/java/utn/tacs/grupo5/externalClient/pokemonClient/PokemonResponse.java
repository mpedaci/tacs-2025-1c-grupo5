package utn.tacs.grupo5.externalClient.pokemonClient;

import java.util.List;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PokemonResponse {
    public List<PokemonCardDto> data;
    public Integer totalCount;
}
