package com.github.zeromodsinc.cyanide.utility;

import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public enum Logger {
    INFO("INFO"),
    DEBUG("DEBUG"),
    WARNING("WARNING"),
    ERROR("ERROR");

    private final String level;

    private String getPrefix() {
        String time = DateTimeFormatter.ofPattern("HH:mm:ss").format(LocalDateTime.now());
        return "[" + time + "] [Cyanide/" + this.level + "] ";
    }

    public void out(String message, String suffix) {
        System.out.print(this.getPrefix() + message + suffix);
        System.out.flush();
    }

    public void out(String message) {
        this.out(message, "\n");
    }

    public void outf(String format, Object... replacements) {
        String message = Utility.fmt(format, replacements);
        this.out(message);
    }

    public void outnf(Object... objects) {
        List<String> strings = new ArrayList<>();
        for (Object obj : objects) {
            strings.add(obj.toString());
        }

        String message = String.join(" ", strings.toArray(new String[0]));
        this.out(message);
    }
}
