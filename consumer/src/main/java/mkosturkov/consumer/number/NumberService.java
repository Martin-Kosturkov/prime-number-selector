package mkosturkov.consumer.number;

import jakarta.inject.Singleton;

@Singleton
public class NumberService {

    public boolean isPrime(long number) {
        for (var i = 2; i <= number / 2; i++) {

            if (number % i == 0) {
                return false;
            }
        }

        return true;
    }
}
