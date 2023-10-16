package puzzlers.tasks;

public class IsDivisibleByNumbers {
    public static boolean isDivisibleByNumbers(int n) {
        int t = n;
        
        while (t > 0) {
            int k = t % 10;
            if (k != 0 && n % k != 0) return false;
            t /= 10;
        }
        return true;
    }
}