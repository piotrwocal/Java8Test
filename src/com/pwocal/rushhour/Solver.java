package com.pwocal.rushhour;


import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Solver {

    private static final int SPACE = Character.valueOf( ' ' );

    public static String firstBoard =
        "AA   O" +
        "P  Q O" +
        "PXXQ O" +
        "P  Q  " +
        "B   CC" +
        "B RRR ";

    public static String fourtyBoard =
        "OAA B " +
        "OCD BP" +
        "OCDXXP" +
        "QQQE P" +
        "  FEGG" +
        "HHFII ";


    public static final List<List<Integer>> IDX_RANGES = generateIdxRanges();

    public static class Move {
        private final int from;
        private final int to;
        private final Character cartType;
        private final String newBoard;
        private final Move previousMove;
        private final int numberOfMoves;

        public Move(String newBoard ) {
            this( -1, -1, null, newBoard, null, 0 );
        }

        public Move(int from, int to, Character carType, String newBoard, Move previousMove, int numberOfMoves ) {
            this.from = from;
            this.to = to;
            this.cartType = carType;
            this.newBoard = newBoard;
            this.previousMove = previousMove;
            this.numberOfMoves = numberOfMoves;
        }

        public String getNewBoard() { return newBoard; }

        public Move getPreviousMove() { return previousMove; }

        public int getNumberOfMoves() { return numberOfMoves; }

        @Override
        public boolean equals( Object other ) {
            return newBoard.equals( ((Move) other).getNewBoard() );
        }

        @Override
        public int hashCode() {
            return newBoard.hashCode();
        }

        public String toString() {
            return String.format( "From %d, to %d, car=%c, board=%s, numOfMoves =%d",
                    from, to, cartType, newBoard, numberOfMoves  );
        }
    }

    private static List<List<Integer>> generateIdxRanges() {
        List<List<Integer>> result = new ArrayList();
        IntStream.range(0, 6).forEach( x -> result.addAll( Stream.of(
                IntStream.iterate(x * 6, i -> i + 1),
                IntStream.iterate(x * 6 + 5, i -> i - 1),
                IntStream.iterate(x, i -> i + 6),
                IntStream.iterate(x + 30, i -> i - 6))
                .map(s -> s.limit(6).boxed().collect( Collectors.toList() ))
            .collect( Collectors.toList() )));
        return result;
    }


    public static void showBoard( String board ) {
        for( String line : board.split( "(?<=\\G.{6})" )) {
            System.out.println( line );
        }
    }


    public static List<Move> findMoves( Move move, List<Integer> indexes ) {
        List<Move> result = new ArrayList<>();
        String board = move.getNewBoard();
        int candidateIdx = -1;
        int lastIdx = -1;
        for( int currentIdx : indexes ) {
            Character current = board.charAt( currentIdx );
            if( current == SPACE && candidateIdx >= 0 ) {
                String newBoard = swapIdx( candidateIdx, currentIdx, board );
                char candidate = board.charAt(candidateIdx);
                result.add( new Move( candidateIdx, currentIdx, candidate, newBoard, move, move.getNumberOfMoves() + 1 ));
                candidateIdx = -1;
            }
            if( current != SPACE && lastIdx >= 0 && board.charAt( lastIdx ) == current ) {
                if( candidateIdx < 0 ) {
                    candidateIdx = lastIdx;
                }
            }
            else {
                candidateIdx = -1;
            }
            lastIdx = currentIdx;
        }
        return result;
    }


    public static List<Move> findAllMoves( Move move ) {
        List<Move> result = new ArrayList<>();
        IDX_RANGES.stream().forEach( indexes -> result.addAll( findMoves( move, indexes )));
        return result;
    }


    public static Move findSolution( Move initMove ) {
        Move bestSolution = null;
        int bestSolutionSize = Integer.MAX_VALUE;

        Map<String,Move> visited = new HashMap<>();
        Map<String,Move> openMoves = new LinkedHashMap<>();
        openMoves.put( initMove.getNewBoard(), initMove );

        while( ! openMoves.isEmpty() ) {
            Move current = openMoves.remove( openMoves.keySet().iterator().next() );
            if( visited.containsKey( current.getNewBoard() )
                    && visited.get( current.getNewBoard() ).getNumberOfMoves() <= current.getNumberOfMoves() ) {
                continue;
            }
            visited.put( current.getNewBoard(), current );
            for( Move nextMove : findAllMoves( current )) {
                if( isFinish( nextMove.getNewBoard() )) {
                    int nextSolutionSize = nextMove.getNumberOfMoves();
                    if( bestSolution == null || nextSolutionSize < bestSolutionSize ) {
                        bestSolution = nextMove;
                        bestSolutionSize = nextSolutionSize;
                    }
                }
                if( ! openMoves.containsKey( nextMove.getNewBoard() )
                        || openMoves.get( nextMove.getNewBoard() ).getNumberOfMoves() > nextMove.getNumberOfMoves() ) {
                    openMoves.put( nextMove.getNewBoard(), nextMove );
                }
            }
        }
        return bestSolution;
    }

    private static int getSolutionSize( Move solution ) {
        int result = 1;
        while( solution.previousMove != null ) {
            result++;
            solution = solution.previousMove;
        }
        return result;
    }


    public static boolean isFinish( String board ) {
        return board.charAt( 17 ) == 'X';
    }


    public static String swapIdx(int first, int second, String board ) {
        int firstIdx = Math.min( first, second );
        int secondIdx = Math.max( first, second );
        char firstChar = board.charAt( firstIdx );
        char secondChar = board.charAt( secondIdx );
        return board.substring( 0, firstIdx ) + secondChar
                + board.substring( firstIdx + 1, secondIdx ) + firstChar
                + board.substring( secondIdx + 1 );
    }
}
