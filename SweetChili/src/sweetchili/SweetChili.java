package sweetchili;

import sweetchili.Board_Pieces.Board;

import sweetchili.GUI.AddGameWindow;

public class SweetChili {

    public static void main(String[] args) {
        AddGameWindow a = new AddGameWindow(new Board());
        a.run();
    }
}
