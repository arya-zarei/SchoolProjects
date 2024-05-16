import org.springframework.web.bind.annotation.*;

@RestController
public class GameController {

    private TicTacToeGame game;

    @PostMapping("/startGame")
    public GameResponse startGame(@RequestBody GameOptions options) {
        game = new TicTacToeGame(options.boardSize, options.winningCondition);
        return new GameResponse(game.getBoard());
    }

    @PostMapping("/makeMove")
    public GameResponse makeMove(@RequestBody Move move) {
        game.makeMove(move.row, move.col);
        if (game.isGameOver()) {
            return new GameResponse(game.getBoard(), true, game.getWinner() + " wins!");
        }
        return new GameResponse(game.getBoard());
    }

    static class GameOptions {
        public int boardSize;
        public int winningCondition;
    }

    static class Move {
        public int row;
        public int col;
    }

    static class GameResponse {
        public char[][] board;
        public boolean gameOver;
        public String message;

        public GameResponse(char[][] board) {
            this.board = board;
            this.gameOver = false;
            this.message = "";
        }

        public GameResponse(char[][] board, boolean gameOver, String message) {
            this.board = board;
            this.gameOver = gameOver;
            this.message = message;
        }
    }
}
