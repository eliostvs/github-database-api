package com.hackerrank.github.core.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.Value;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude = {"streak", "latestEvent", "events"})
@Getter
@NoArgsConstructor
@Setter
@ToString
public class Actor {
    private Identity id;
    private String login;
    private String avatar;
    private int streak;
    private LocalDateTime latestEvent;
    private List<Event> events = new ArrayList<>();

    public void calculateStreak() {
        int maxStreak = 0;
        int currentStreak = 0;

        for (int i = events.size() - 1; i > 0; i--) {
            LocalDateTime currentDate = events.get(i).getCreatedAt().truncatedTo(ChronoUnit.DAYS);
            LocalDateTime nextDate = events.get(i - 1).getCreatedAt().truncatedTo(ChronoUnit.DAYS);

            if (currentDate.plusDays(1).equals(nextDate)) {
                currentStreak++;
                if (currentStreak > maxStreak)
                    maxStreak = currentStreak;
            } else {
                currentStreak = 0;
            }
        }

        this.streak = maxStreak;

        latestEvent = events.get(0).getCreatedAt();
    }

    public UpdateValidation validateUpdate(Actor other) {
        if (login.equals(other.getLogin())) {
            return UpdateValidation.success();
        } else {
            String errorMessage = String.format("Update of the Actor with ID '%d' failed because only field 'avatar'" +
                    " can be change. Tried to change the field 'login'", other.getId().getNumber());
            return UpdateValidation.error(errorMessage);
        }
    }

    @Value
    public static class UpdateValidation {
        private final boolean success;
        private final String reason;

        public static UpdateValidation success() {
            return new UpdateValidation(true, "");
        }

        public static UpdateValidation error(String reason) {
            return new UpdateValidation(false, reason);
        }
    }
}

