function startGame() {
    var difficulty = document.getElementById('difficulty').value;
    var boardSize = document.getElementById('boardSize').value;
    var winningCondition = document.getElementById('winningCondition').value;

    // Send options to backend
    fetch('/startGame', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            difficulty: difficulty,
            boardSize: boardSize,
            winningCondition: winningCondition
        })
    })
    .then(response => response.json())
    .then(data => {
        // Display game board
        var gameBoard = document.getElementById('gameBoard');
        gameBoard.innerHTML = '';
        for (var i = 0; i < data.board.length; i++) {
            for (var j = 0; j < data.board[i].length; j++) {
                var square = document.createElement('div');
                square.classList.add('square');
                square.dataset.row = i;
                square.dataset.col = j;
                square.textContent = data.board[i][j];
                square.addEventListener('click', makeMove);
                gameBoard.appendChild(square);
            }
        }
    })
    .catch(error => console.error('Error:', error));
}

function makeMove(event) {
    var row = event.target.dataset.row;
    var col = event.target.dataset.col;

    // Send move to backend
    fetch('/makeMove', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            row: row,
            col: col
        })
    })
    .then(response => response.json())
    .then(data => {
        // Update game board
        var square = document.querySelector(`.square[data-row="${row}"][data-col="${col}"]`);
        square.textContent = data.symbol;
        if (data.gameOver) {
            alert(data.message);
            // Optionally reset the game board or redirect to a new game
        }
    })
    .catch(error => console.error('Error:', error));
}
