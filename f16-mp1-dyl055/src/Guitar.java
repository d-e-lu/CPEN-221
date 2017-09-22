// The Guitar interface specifies a set of methods that can be used to play a
// musical instrument that can play various notes.  You can specify exactly
// which note to play or you can specify a character that indicates which note
// to play.  Different guitar objects will have different mappings from
// characters to notes.

public interface Guitar {
    // plays the given note if possible by plucking an appropriate string;
    // the pitch uses a chromatic scale where Concert-A has a pitch of 12
    public void playNote(int pitch);

    // returns whether there is a string that corresponds to this character
    public boolean hasString(char key);

    // plucks the string for this character
    public void pluck(char key);

    // returns the current sound (sum of all strings)
    public double sample();

    // advances the simulation by having each string tic forward
    public void tic();

    // optional method that returns the number of times tic has been called;
    // returns -1 if not implemented
    public int time();
}
