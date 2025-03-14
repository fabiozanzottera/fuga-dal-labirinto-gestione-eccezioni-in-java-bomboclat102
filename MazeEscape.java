
package org.example;
import java.util.Random;
import java.util.Scanner;

class OutOfBoundsException extends Exception {
    public OutOfBoundsException(String message) {
        super(message);
    }
}

class WallCollisionException extends Exception {
    public WallCollisionException(String message) {
        super(message);
    }
}

public class MazeEscape {
    private static char[][] LABIRINTO = {
        { 'P', '.', '#', '.', '.' },
        { '#', '.', '#', '.', '#' },
        { '.', '.', '.', '#', '.' },
        { '#', '#', '.', '.', '.' },
        { '#', '.', '#', '#', 'E' }
    };
    private static int playerX = 0;
    private static int playerY = 0;
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean escaped = false;
        int mosse = 0;

        generaLabirintoCasuale();
        System.out.println("Benvenuto in Maze Escape! Trova l'uscita ('E').");

        while (!escaped) {
            printMaze();
            System.out.print("Muoviti (W = su, A = sinistra, S = giù, D = destra): ");
            char move = scanner.next().toUpperCase().charAt(0);

            try {
                mosse++;
                movePlayer(move);
                if (playerX == LABIRINTO.length - 1 && playerY == LABIRINTO[0].length - 1) {
                    escaped = true;
                    System.out.println("Hai trovato l'uscita! Congratulazioni!");
                    System.out.println("Hai impiegato " + mosse + " mosse.");
                }
            } catch (OutOfBoundsException | WallCollisionException e) {
                System.out.println(e.getMessage());
            }
        }

        scanner.close();
    }
    public static void generaLabirintoCasuale() {
        LABIRINTO = new char[5][5];
        Random random = new Random();
        for (int i = 0; i < LABIRINTO.length; i++) {
            for (int j = 0; j < LABIRINTO[i].length; j++) {
                LABIRINTO[i][j] = '#';
            }
        }
        int x = 0;
        int y = 0;
        while (x < LABIRINTO.length - 1 || y < LABIRINTO[0].length - 1) {
            LABIRINTO[x][y] = '.';
            int direzione = random.nextInt(3);
            switch (direzione) {
                case 0:
                    if (x < LABIRINTO.length - 1) {
                        x++;
                    }
                    break;
                case 1:
                    if (y < LABIRINTO[0].length - 1) {
                        y++;
                    }
                    break;
                case 2:
                    if (x > 0) {
                        x--;
                    }
                    break;
            }
        }

        LABIRINTO[0][0] = 'P';
        LABIRINTO[LABIRINTO.length - 1][LABIRINTO[0].length - 1] = 'E';
    }

    private static void movePlayer(char direction) throws OutOfBoundsException, WallCollisionException {
        int newX = playerX;
        int newY = playerY;
        switch (direction) {
            case 'W':
                newX--;
                break;
            case 'A':
                newY--;
                break;
            case 'S':
                newX++;
                break;
            case 'D':
                newY++;
                break;
            default:
                return;
        }
        if (newX < 0 || newX >= LABIRINTO.length || newY < 0 || newY >= LABIRINTO[0].length) {
            throw new OutOfBoundsException("errore stai andando fuori dai limiti");
        }
        if (LABIRINTO[newX][newY] == '#') {
            throw new WallCollisionException("Bomboclat hai preso un muro");
        }
        LABIRINTO[playerX][playerY] = '.';
        LABIRINTO[newX][newY] = 'P';
        playerX = newX;
        playerY = newY;
    }
    private static void printMaze() {
        for (int i = 0; i < LABIRINTO.length; i++) {
            for (int j = 0; j < LABIRINTO[i].length; j++) {
                System.out.print(LABIRINTO[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}