package utn.tacs.grupo5.externalClient.yugiohClient;

import java.util.List;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class YuGiOhCardDto {
    public int id;
    public String name;
    public int def;
    public List<YuGiOhCardImage> card_images;
}
