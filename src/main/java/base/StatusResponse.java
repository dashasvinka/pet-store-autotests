package base;

public record StatusResponse(
        int code,
        String type,
        String message
) {
}
