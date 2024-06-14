package com.ds.personneldepartment;

import javafx.scene.image.Image;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class Utils {
    @Contract("_ -> new")
    public static @NotNull Image getImage(String path){
        return new Image(Objects.requireNonNull(Main.class.getResourceAsStream(path)));
    }
}
