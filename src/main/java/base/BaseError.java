package base;

public record BaseError(
        int code,
        String type,
        String message
) {
}
