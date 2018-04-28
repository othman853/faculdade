package s1801.lab02.m08;

public class EmptyListException extends RuntimeException {

   // no-argument constructor
   public EmptyListException() {
      this( "List" );   // call other EmptyListException constructor
   }

   // constructor
   public EmptyListException( String name ) {
      super( name + " is empty" );  // call superclass constructor
   }

} // end class EmptyListException

