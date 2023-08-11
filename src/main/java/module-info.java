module chess {
    requires transitive javafx.graphics;
    requires transitive javafx.controls;

    opens chess to javafx.fxml;
    exports chess;
    exports chess.Pieces;
    exports chess.GUI;
}
