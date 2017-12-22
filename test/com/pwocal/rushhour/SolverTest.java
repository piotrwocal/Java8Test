package com.pwocal.rushhour;

import org.junit.Test;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

import static org.junit.Assert.*;

public class SolverTest {

    @Test
    public void testShowBoard() {
        Solver.showBoard( Solver.firstBoard );
    }

    @Test
    public void testIdxRanges() {
        assertEquals( 24, Solver.IDX_RANGES.size() );

        assertEquals( "[0, 1, 2, 3, 4, 5]", Solver.IDX_RANGES.get( 0 ).toString() );
        assertEquals( "[30, 24, 18, 12, 6, 0]", Solver.IDX_RANGES.get( 3 ).toString() );
        assertEquals( "[35, 29, 23, 17, 11, 5]", Solver.IDX_RANGES.get( 23 ).toString() );

        Solver.IDX_RANGES.forEach( x -> System.out.println( x ));
    }

    @Test
    public void testSwapIdx() {
        assertEquals( "AB", Solver.swapIdx( 0, 1, "BA"));
        assertEquals( "A B", Solver.swapIdx( 0, 2, "B A"));
        assertEquals( " BA", Solver.swapIdx( 0, 1, "B A"));
        assertEquals( "B A", Solver.swapIdx( 1, 2, "BA "));
    }



    private String getBoardAtPos( String board, List<Integer> indexes, int pos ) {
        return Solver.findMoves( new Solver.Move( board ), indexes ).get( pos ).getNewBoard();
    }

    @Test
    public void testFindMovesSimple() {
        List<Integer> rightIndexes = Solver.IDX_RANGES.get( 0 );

        assertEquals( getBoardAtPos( "AA BCC", rightIndexes, 0),
                                     " AABCC");

        assertEquals( getBoardAtPos( " AA CC", rightIndexes, 0),
                                     "  AACC");

        assertEquals( getBoardAtPos( " AACC ", rightIndexes, 0),
                                     " AA CC");

        assertEquals( getBoardAtPos( "AA CC ", rightIndexes, 0),
                                     " AACC ");

        assertEquals( getBoardAtPos( "AA CC ", rightIndexes, 1),
                                     "AA  CC");

        assertEquals( getBoardAtPos( " AAAA ", rightIndexes, 0),
                                     "  AAAA");

        assertTrue( Solver.findMoves( new Solver.Move( "AB  CC"), rightIndexes ).isEmpty());
        assertTrue( Solver.findMoves( new Solver.Move( "  AACC"), rightIndexes ).isEmpty());
    }

    public static String finishBoard =
            "AA   O" +
            "P  Q O" +
            "P  QXX" +
            "P  Q  " +
            "B   CC" +
            "B RRR ";

    @Test
    public void testIsFinish() {
        assertFalse( Solver.isFinish( Solver.firstBoard ));
        assertTrue( Solver.isFinish( finishBoard ));
    }

    public static String easySolution =
            "      " +
            "      " +
            " XXA  " +
            "   A  " +
            "      " +
            "      ";

    public static String easySolution2 =
            "      " +
            "      " +
            " XX   " +
            "   A  " +
            "   A  " +
            "      ";

    @Test
    public void testFindAllMoves() {
        assertEquals( 4, Solver.findAllMoves( new Solver.Move( easySolution2 )).size() );
    }

    @Test
    public void testSolution() {
        long start = System.nanoTime();
        Solver.Move solutionMove = Solver.findSolution( new Solver.Move( Solver.fourtyBoard ));
        System.out.println( "Solution took " + (System.nanoTime() - start) / 1_000_000 );
        showSolution( solutionMove );
    }

    private void showSolution( Solver.Move solutionMove ) {
        Deque<Solver.Move> solutionList = new ArrayDeque<>();
        while( solutionMove != null ) {
            solutionList.push( solutionMove );
            System.out.println( "---------------------" );
            System.out.println( solutionMove );
            Solver.showBoard( solutionMove.getNewBoard() );
            solutionMove = solutionMove.getPreviousMove();
        }
        System.out.println( "Total number of moves = " + solutionList.size() );
    }


    @Test
    public void testFindAllMovesSimpleBoard() {
        Solver.showBoard( Solver.firstBoard );
        System.out.println( " ===>>> ");
        Solver.findAllMoves( new Solver.Move( Solver.firstBoard )).forEach( move ->
            { System.out.println( move );
                Solver.showBoard( move.getNewBoard() );
                System.out.println( "-----------------");} );
    }

}