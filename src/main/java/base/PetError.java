package base;

public record PetError(
        int code,
        String type,
        String message
) {
}
