package base;

import java.util.List;

public record NewPet(
        double id,
        Category category,
        String name,
        List<String> photoUrls,
        List<Tag> tags,
        String status

) {
    public record Category(
            int id,
            String name
    ) {
    }

    public record Tag(
            int id,
            String name
    ) {
    }
}
