package lesson4;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductView {

    private Long id;

    private String title;

    private String image;

    private String imageType;

}
