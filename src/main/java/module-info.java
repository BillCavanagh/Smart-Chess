module chess {
    requires transitive javafx.graphics;
    requires transitive javafx.controls;
    requires org.junit.jupiter.api;
    opens chess to javafx.fxml;
    exports chess.Pieces;
    exports chess.GUI;
}