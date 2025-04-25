package utn.tacs.grupo5.entity.card;

import lombok.Data;

@Data
public class Game {

    private Long id;
    private String title;
    private String description;
    private Name name;
    // private String apiUrl;

    public enum Name { // Supported games
        MAGIC,
        POKEMON,
        YUGIOH;

        public static Name fromString(String name) {
            try {
                return Name.valueOf(name.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException(
                        "Game name '" + name + "' is not valid");
            }
        }
    }

}
