package hu.oe.nik.szfmv.automatedcar.systemcomponents;

public class Shitfer {
    public enum ShiftPos {P, R, N, D}

    private int acttuallyValue = 0;

    public void Increment()
    {
        ShiftPos[] pos = ShiftPos.values();
        if (acttuallyValue < pos.length-1)
        {
            acttuallyValue++;
        }
    }

    public void Decrement()
    {
        if (acttuallyValue > 0)
        {
            acttuallyValue--;
        }
    }

    public ShiftPos GetCurrentState()
    {
        ShiftPos[] curr = ShiftPos.values();
        return curr[acttuallyValue];
    }


}
