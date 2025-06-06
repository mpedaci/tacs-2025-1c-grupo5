package utn.tacs.grupo5.telegrambot.dto;

public enum ConservationStatus {
    BAD,
    REGULAR,
    GOOD,
    EXCELLENT,
    ALMOST_PERFECT,
    PERFECT;

    public static ConservationStatus fromString(String status) {
        try {
            return ConservationStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Conservation status '" + status + "' is not valid");
        }
    }
}
